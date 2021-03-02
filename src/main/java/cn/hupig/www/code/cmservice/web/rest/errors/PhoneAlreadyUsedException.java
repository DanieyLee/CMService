package cn.hupig.www.code.cmservice.web.rest.errors;

public class PhoneAlreadyUsedException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public PhoneAlreadyUsedException() {
    	super(ErrorConstants.PHONE_ALREADY_USED_TYPE, "phone is already in use!", "userManagement", "phoneexists");
    }
    
    public PhoneAlreadyUsedException(String code) {
    	super(ErrorConstants.PHONE_ALREADY_USED_TYPE, "phone is already in use!", "userManagement", "phonecodeexists");
    }
    
}
