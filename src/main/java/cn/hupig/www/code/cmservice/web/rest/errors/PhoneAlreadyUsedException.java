package cn.hupig.www.code.cmservice.web.rest.errors;

public class PhoneAlreadyUsedException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    /**
     * 验证码错误!
     */
    public PhoneAlreadyUsedException() {
    	super(ErrorConstants.PHONE_ALREADY_USED_TYPE, "phone is already in use!", "userManagement", "phoneexists");
    }
    
    /**
     * 验证码24小时内有效，请勿重复发送!
     * @param code
     */
    public PhoneAlreadyUsedException(Integer code) {
    	super(ErrorConstants.PHONE_ALREADY_USED_TYPE, "phone is already in use!", "userManagement", "phonecodeexists");
    }
    
    /**
     * 手机号码错误!
     * @param phone
     */
    public PhoneAlreadyUsedException(String phone) {
    	super(ErrorConstants.PHONE_ALREADY_USED_TYPE, "phone is already in use!", "userManagement", "userphoneexists");
    }
    
}
