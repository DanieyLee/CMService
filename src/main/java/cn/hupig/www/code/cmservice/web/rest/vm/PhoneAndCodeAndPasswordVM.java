package cn.hupig.www.code.cmservice.web.rest.vm;

import javax.validation.constraints.Size;

/**
 * View Model object for storing the user's key and password.
 */
public class PhoneAndCodeAndPasswordVM {
	
    private String phone;
    
    private String code;
    
    private String newPassword;
    
	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the newPassword
	 */
	public String getNewPassword() {
		return newPassword;
	}

	/**
	 * @param newPassword the newPassword to set
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	@Override
	public String toString() {
		return "PhoneAndCodeAndPasswordVM [phone=" + phone + ", code=" + code + ", newPassword=" + newPassword + "]";
	}

}
