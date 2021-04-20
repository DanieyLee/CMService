package cn.hupig.www.code.cmservice.web.rest.vm;

import javax.validation.constraints.Size;

/**
 * View Model extending the UserDTO, which is meant to be used in the user management UI.
 */
public class ManagedPhoneUserVM {

    public static final int PHONE_MIN_LENGTH = 8;

    public static final int PHONE_MAX_LENGTH = 11;
    
    public static final int CODE_MIN_LENGTH = 6;

    public static final int CODE_MAX_LENGTH = 6;
    
    public static final int PASSWORD_MIN_LENGTH = 4;

    public static final int PASSWORD_MAX_LENGTH = 100;

    @Size(min = PHONE_MIN_LENGTH, max = PHONE_MAX_LENGTH)
    private String phone;
    
    @Size(min = CODE_MIN_LENGTH, max = CODE_MAX_LENGTH)
    private String code;
    
    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;

    @Size(min = 2, max = 10)
    private String langKey;

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
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the langKey
	 */
	public String getLangKey() {
		return langKey;
	}

	/**
	 * @param langKey the langKey to set
	 */
	public void setLangKey(String langKey) {
		this.langKey = langKey;
	}

	@Override
	public String toString() {
		return "ManagedPhoneUserVM [phone=" + phone + ", code=" + code + ", password=" + password + ", langKey="
				+ langKey + "]";
	}
    
}
