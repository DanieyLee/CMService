package cn.hupig.www.code.cmservice.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.hupig.www.code.cmservice.domain.Authority;
import cn.hupig.www.code.cmservice.domain.Phone;
import cn.hupig.www.code.cmservice.domain.User;
import cn.hupig.www.code.cmservice.domain.UserLink;
import cn.hupig.www.code.cmservice.repository.AuthorityRepository;
import cn.hupig.www.code.cmservice.repository.PhoneRepository;
import cn.hupig.www.code.cmservice.repository.UserLinkRepository;
import cn.hupig.www.code.cmservice.repository.UserRepository;
import cn.hupig.www.code.cmservice.security.AuthoritiesConstants;
import cn.hupig.www.code.cmservice.security.SecurityUtils;
import cn.hupig.www.code.cmservice.service.utils.FileOperation;
import cn.hupig.www.code.cmservice.service.utils.Times;
import cn.hupig.www.code.cmservice.web.rest.errors.PhoneAlreadyUsedException;
import cn.hupig.www.code.cmservice.web.rest.vm.SettingsUserVM;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class Rewrite_UserService {

    private final Logger log = LoggerFactory.getLogger(Rewrite_UserService.class);

    private final UserRepository userRepository;

    private final PhoneRepository phoneRepository;
    
    private final PasswordEncoder passwordEncoder;
    
    private final UserLinkRepository userLinkRepository;

    private final AuthorityRepository authorityRepository;

    private final CacheManager cacheManager;

    public Rewrite_UserService(UserRepository userRepository, UserLinkRepository userLinkRepository, PhoneRepository phoneRepository, PasswordEncoder passwordEncoder, AuthorityRepository authorityRepository, CacheManager cacheManager) {
        this.userRepository = userRepository;
        this.phoneRepository = phoneRepository;
        this.passwordEncoder = passwordEncoder;
        this.userLinkRepository = userLinkRepository;
        this.authorityRepository = authorityRepository;
        this.cacheManager = cacheManager;
    }
    
    public void updateUser(SettingsUserVM settingsUserVM) {
        if (settingsUserVM.isImgSwitch()) { // ImgSwitch 开关打开就是上传文件
        	String oldFile = settingsUserVM.getImageUrl();
        	settingsUserVM.setImageUrl(FileOperation.save( // 上传文件
        				settingsUserVM.getImage(),
        				settingsUserVM.getImgName(), "user"));
        	FileOperation.deleteFile(oldFile, "user"); // 更新了头像之后，要删除原头像
        }
        userRepository.findOneByLogin(settingsUserVM.getLogin())
        .ifPresent(user -> {
        	userLinkRepository.findOneByUserId(user.getId()).ifPresent(userLink -> {
        		userLink.setFirstName(settingsUserVM.getFirstName());
        		userLink.setSex(settingsUserVM.isSex());
        		userLink.setAge(settingsUserVM.getAge());
        		userLink.setPasswordKey(settingsUserVM.getPasswordKey());
        	});
            user.setFirstName(settingsUserVM.getFirstName());
            user.setEmail(settingsUserVM.getEmail().toLowerCase());
            user.setImageUrl(settingsUserVM.isImgSwitch() ?
            		settingsUserVM.getImageUrl() : user.getImageUrl());
            this.clearUserCaches(user);
            log.debug("Changed Information for User: {}", user);
        });
    }

    public void registerUser(String phoneNumber, String code, String password, String langKey) {
        if (!judgeCode(phoneNumber, code)) {
        	throw new PhoneAlreadyUsedException();
        }
        if (userRepository.findOneByLogin(phoneNumber).isPresent()){
        	throw new UsernameAlreadyUsedException();
        }
        User newUser = new User();
        String encryptedPassword = passwordEncoder.encode(password);
        newUser.setLogin(phoneNumber);
        // new user gets initially a generated password
        newUser.setPassword(encryptedPassword);
        newUser.setFirstName(phoneNumber);
        newUser.setLastName("");
        newUser.setEmail(phoneNumber + "@local.com");
        newUser.setImageUrl("content/images/author.svg"); // 默认头像
        newUser.setLangKey(langKey);
        // new user is active
        newUser.setActivated(true);
        Set<Authority> authorities = new HashSet<>();
        authorityRepository.findById(AuthoritiesConstants.USER).ifPresent(authorities::add);
        newUser.setAuthorities(authorities);
        newUser = userRepository.save(newUser);
        this.clearUserCaches(newUser);
        log.debug("Created Information for User: {}", newUser);
        UserLink userLink = new UserLink();
        userLink.setUser(newUser);
        userLink.setFirstName(phoneNumber);
        userLink.setTheme("black");
        userLink.setAge(0L);
        userLink.setSex(true);
        userLink.setPasswordKey(666666L);
        userLink = userLinkRepository.save(userLink);
        log.debug("Created Information for UserLink: {}", userLink);
    }
    
    public void resetPassword(String phoneNumber, String code, String newPassword) {
        if (!judgeCode(phoneNumber, code)) {
        	throw new PhoneAlreadyUsedException();
        }
        Optional<User> user = userRepository.findOneByLogin(phoneNumber);
        if (!user.isPresent()) {
        	throw new PhoneAlreadyUsedException("phone");
        } else {
        	log.debug("Reset Information for Password: {}", user.get());
        	user.get().setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user.get());
            this.clearUserCaches(user.get());
        }
    }
    
    private boolean judgeCode(String phoneNumber, String code) {
    	Optional<Phone> Phone = phoneRepository.findOneByPhoneAndCode(phoneNumber, Integer.parseInt(code));
    	if (!Phone.isPresent()) {
    		return false; // 没有找到验证码，还没有发送，或者已经超时了
    	}
    	return Times.timeSlot(Phone.get().getSendTime(), Phone.get().getEffectiveTime());
    }

    private void clearUserCaches(User user) {
        Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_LOGIN_CACHE)).evict(user.getLogin());
        if (user.getEmail() != null) {
            Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_EMAIL_CACHE)).evict(user.getEmail());
        }
    }
}
