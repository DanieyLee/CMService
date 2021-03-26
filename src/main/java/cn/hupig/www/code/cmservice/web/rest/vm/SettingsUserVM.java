package cn.hupig.www.code.cmservice.web.rest.vm;

import cn.hupig.www.code.cmservice.service.dto.UserDTO;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.validation.constraints.Size;

/**
 * View Model extending the UserDTO, which is meant to be used in the user management UI.
 */
public class SettingsUserVM extends UserDTO {

	private static final long serialVersionUID = 1L;
	
    private Long passwordKey;

    private Long age;
    
    private boolean sex;
    
    @Lob
    private byte[] image;
    
    public SettingsUserVM() {
        // Empty constructor needed for Jackson.
    }

	/**
	 * @return the passwordKey
	 */
	public Long getPasswordKey() {
		return passwordKey;
	}

	/**
	 * @param passwordKey the passwordKey to set
	 */
	public void setPasswordKey(Long passwordKey) {
		this.passwordKey = passwordKey;
	}

	/**
	 * @return the age
	 */
	public Long getAge() {
		return age;
	}

	/**
	 * @param age the age to set
	 */
	public void setAge(Long age) {
		this.age = age;
	}

	/**
	 * @return the sex
	 */
	public boolean isSex() {
		return sex;
	}

	/**
	 * @param sex the sex to set
	 */
	public void setSex(boolean sex) {
		this.sex = sex;
	}

	/**
	 * @return the image
	 */
	public byte[] getImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(byte[] image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "SettingsUserVM [passwordKey=" + passwordKey + ", age=" + age + ", sex=" + sex + ", image="
				+ Arrays.toString(image) +
				", UserDTO {" + super.toString() + "} ]";
	}

}
