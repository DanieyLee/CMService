package cn.hupig.www.code.cmservice.web.rest.errors;

public class AccountResourceException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public AccountResourceException(String message) {
        super(message);
    }
}
