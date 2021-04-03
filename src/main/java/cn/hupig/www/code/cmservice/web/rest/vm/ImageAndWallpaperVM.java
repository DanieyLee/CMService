package cn.hupig.www.code.cmservice.web.rest.vm;

import java.util.Arrays;

import javax.persistence.Lob;

import cn.hupig.www.code.cmservice.service.dto.UserDTO;
import cn.hupig.www.code.cmservice.service.dto.WallpaperDTO;

/**
 * View Model extending the UserDTO, which is meant to be used in the user management UI.
 */
public class ImageAndWallpaperVM extends WallpaperDTO {

	private static final long serialVersionUID = 1L;
    
    @Lob
    private byte[] image;
    
    private String imgName;
    
    private boolean imgSwitch;
    
    public ImageAndWallpaperVM() {
        // Empty constructor needed for Jackson.
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

	/**
	 * @return the imgName
	 */
	public String getImgName() {
		return imgName;
	}
	
	/**
	 * @param imgName the imgName to set
	 */
	public void setImgName(String imgName) {
		this.imgName = imgName;
	}
	
	/**
	 * @return the imgSwitch
	 */
	public boolean isImgSwitch() {
		return imgSwitch;
	}

	/**
	 * @param imgSwitch the imgSwitch to set
	 */
	public void setImgSwitch(boolean imgSwitch) {
		this.imgSwitch = imgSwitch;
	}

	@Override
	public String toString() {
		return "ImageAndWallpaperVM [image=" + image.length +
				", imgName=" + imgName + ", imgSwitch =" + imgSwitch +
		", " + super.toString() + "} ]";
	}

}
