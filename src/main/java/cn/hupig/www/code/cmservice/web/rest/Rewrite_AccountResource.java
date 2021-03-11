package cn.hupig.www.code.cmservice.web.rest;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cn.hupig.www.code.cmservice.service.Rewrite_UserService;
import cn.hupig.www.code.cmservice.web.rest.errors.EmailAlreadyUsedException;
import cn.hupig.www.code.cmservice.web.rest.errors.InvalidPasswordException;
import cn.hupig.www.code.cmservice.web.rest.errors.LoginAlreadyUsedException;
import cn.hupig.www.code.cmservice.web.rest.errors.PhoneAlreadyUsedException;
import cn.hupig.www.code.cmservice.web.rest.vm.ManagedPhoneUserVM;
import cn.hupig.www.code.cmservice.web.rest.vm.PhoneAndCodeAndPasswordVM;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api")
public class Rewrite_AccountResource {

    private final Logger log = LoggerFactory.getLogger(Rewrite_AccountResource.class);

    private final Rewrite_UserService rewrite_UserService;

    public Rewrite_AccountResource(Rewrite_UserService rewrite_UserService) {
        this.rewrite_UserService = rewrite_UserService;
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
