package cn.hupig.www.code.cmservice.service;

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
import cn.hupig.www.code.cmservice.service.utils.Times;
import cn.hupig.www.code.cmservice.web.rest.errors.PhoneAlreadyUsedException;

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

    public void registerUser(String phoneNumber, String code, String password, String langKey) {
        if (userRepository.findOneByLogin(phoneNumber).isPresent()){
        	throw new UsernameAlreadyUsedException();
        }
        if (!judgeCode(phoneNumber, code)) {
        	throw new PhoneAlreadyUsedException();
        }
        User newUser = new User();
        String encryptedPassword = passwordEncoder.encode(password);
        newUser.setLogin(phoneNumber);
        // new user gets initially a generated password
        newUser.setPassword(encryptedPassword);
        newUser.setFirstName(phoneNumber);
        newUser.setLastName("");
        newUser.setEmail(phoneNumber + "@local.com");
        newUser.setImageUrl("");
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
    	Phone Phone = phoneRepository.findOneByPhoneAndCode(phoneNumber, Integer.parseInt(code)).get();
    	if (Phone == null) {
    		return false;
    	}
    	return Times.timeSlot(Phone.getSendTime(), Phone.getEffectiveTime());
    }

    private void clearUserCaches(User user) {
        Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_LOGIN_CACHE)).evict(user.getLogin());
        if (user.getEmail() != null) {
            Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_EMAIL_CACHE)).evict(user.getEmail());
        }
    }
}
