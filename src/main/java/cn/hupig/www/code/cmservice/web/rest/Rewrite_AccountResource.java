package cn.hupig.www.code.cmservice.web.rest;

import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cn.hupig.www.code.cmservice.domain.User;
import cn.hupig.www.code.cmservice.repository.UserRepository;
import cn.hupig.www.code.cmservice.security.AuthoritiesConstants;
import cn.hupig.www.code.cmservice.service.Rewrite_UserService;
import cn.hupig.www.code.cmservice.service.UserService;
import cn.hupig.www.code.cmservice.web.rest.errors.AccountResourceException;
import cn.hupig.www.code.cmservice.web.rest.errors.EmailAlreadyUsedException;
import cn.hupig.www.code.cmservice.web.rest.errors.InvalidPasswordException;
import cn.hupig.www.code.cmservice.web.rest.errors.LoginAlreadyUsedException;
import cn.hupig.www.code.cmservice.web.rest.errors.PhoneAlreadyUsedException;
import cn.hupig.www.code.cmservice.web.rest.vm.ManagedPhoneUserVM;
import cn.hupig.www.code.cmservice.web.rest.vm.PhoneAndCodeAndPasswordVM;
import cn.hupig.www.code.cmservice.web.rest.vm.SettingsUserVM;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api")
@Api(tags = "004-账号管理")
public class Rewrite_AccountResource {
    
    private final Logger log = LoggerFactory.getLogger(Rewrite_AccountResource.class);

    private final UserRepository userRepository;
    
    private final UserService userService;
    
    private final Rewrite_UserService rewrite_UserService;

    public Rewrite_AccountResource(UserRepository userRepository, UserService userService, Rewrite_UserService rewrite_UserService) {
    	this.userRepository = userRepository;
    	this.userService = userService;
    	this.rewrite_UserService = rewrite_UserService;
    }

    /**
     * {@code POST  /account} : update the current user information.
     *
     * @param userDTO the current user information.
     * @throws EmailAlreadyUsedException {@code 400 (Bad Request)} if the email is already used.
     * @throws RuntimeException {@code 500 (Internal Server Error)} if the user login wasn't found.
     */
    @PostMapping("/account/setting")
    @ApiOperation(value = "修改用户信息")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public void saveAccount(@Valid @RequestBody SettingsUserVM settingsUserVM) {
    	log.debug("REST request to revise User : {}", settingsUserVM);
    	Optional<User> user = userService.getUserWithAuthorities(); // 当前登录的用户
    	Optional<User> enterUser = userRepository.findOneByLogin(settingsUserVM.getLogin()); // 取出传入的用户信息
    	if (!enterUser.isPresent() || !user.get().getLogin().equals(enterUser.get().getLogin())) { // 传入的用户没有-或者-登录的不是传入的用户
    		throw new AccountResourceException("User could not be found");
    	}
        rewrite_UserService.updateUser(settingsUserVM);
    }
    
    /**
     * {@code POST  /register} : register the user.
     *
     * @param ManagedPhoneUserVM the managed user View Model.
     * @throws InvalidPasswordException {@code 400 (Bad Request)} if the password is incorrect.
     * @throws EmailAlreadyUsedException {@code 400 (Bad Request)} if the email is already used.
     * @throws LoginAlreadyUsedException {@code 400 (Bad Request)} if the login is already used.
     */
    @PostMapping("/public/register")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "注册账户-根据手机号-验证码")
    public void registerAccount(@Valid @RequestBody ManagedPhoneUserVM managedPhoneUserVM) {
    	log.debug("REST request to register Phone : {}", managedPhoneUserVM);
        if (!checkPasswordLength(managedPhoneUserVM.getPassword())) {
            throw new InvalidPasswordException();
        }
        if (!checkPhoneLength(managedPhoneUserVM.getPhone())) {
            throw new PhoneAlreadyUsedException("phone");
        }
        if (!checkCodeLength(managedPhoneUserVM.getCode())) {
            throw new PhoneAlreadyUsedException();
        }
        rewrite_UserService.registerUser(managedPhoneUserVM.getPhone(),
							        		managedPhoneUserVM.getCode(),
							        		managedPhoneUserVM.getPassword(),
							        		managedPhoneUserVM.getLangKey());
    }
    
    /**
     * {@code POST   /public/account/reset-password} : Send an phone to reset the password of the user.
     *
     * @param phone the phone of the user.
     */
    @PostMapping(path = "/public/account/reset-password")
    @ApiOperation(value = "重置密码-不需要登录-根据验证码")
    public void requestPasswordReset(@RequestBody PhoneAndCodeAndPasswordVM PhoneAndCodeAndPasswordVM) {
    	log.debug("REST request to reset password : {}", PhoneAndCodeAndPasswordVM);
        if (!checkPasswordLength(PhoneAndCodeAndPasswordVM.getNewPassword())) {
            throw new InvalidPasswordException();
        }
        if (!checkPhoneLength(PhoneAndCodeAndPasswordVM.getPhone())) {
            throw new PhoneAlreadyUsedException("phone");
        }
        if (!checkCodeLength(PhoneAndCodeAndPasswordVM.getCode())) {
            throw new PhoneAlreadyUsedException();
        }
        rewrite_UserService.resetPassword(PhoneAndCodeAndPasswordVM.getPhone(), 
        									PhoneAndCodeAndPasswordVM.getCode(),
        									PhoneAndCodeAndPasswordVM.getNewPassword());
    }

    private static boolean checkPasswordLength(String password) {
        return !StringUtils.isEmpty(password) &&
            password.length() >= ManagedPhoneUserVM.PASSWORD_MIN_LENGTH &&
            password.length() <= ManagedPhoneUserVM.PASSWORD_MAX_LENGTH;
    }
    
    private static boolean checkPhoneLength(String phone) {
        return !StringUtils.isEmpty(phone) &&
    		phone.length() >= ManagedPhoneUserVM.PHONE_MIN_LENGTH &&
			phone.length() <= ManagedPhoneUserVM.PHONE_MAX_LENGTH;
    }
    
    private static boolean checkCodeLength(String code) {
        return !StringUtils.isEmpty(code) &&
    		code.length() >= ManagedPhoneUserVM.CODE_MIN_LENGTH &&
			code.length() <= ManagedPhoneUserVM.CODE_MAX_LENGTH;
    }
}
