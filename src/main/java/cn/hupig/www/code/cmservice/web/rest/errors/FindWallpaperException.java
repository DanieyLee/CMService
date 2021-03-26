package cn.hupig.www.code.cmservice.web.rest.errors;

public class FindWallpaperException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    /**
     * 翻页已经没有图片了!
     */
    public FindWallpaperException() {
    	super(ErrorConstants.IMAGE_ALREADY_USED_TYPE, "image is already in use!", "userManagement", "imageexists");
    }
    
}
