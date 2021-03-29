package cn.hupig.www.code.cmservice.web.rest.errors;

public class KeyBoxException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    /**
     * 创建报错
     */
    public KeyBoxException() {
    	super(ErrorConstants.KEY_BOX_USED_TYPE, "key box is already in use!", "userManagement", "fileexists");
    }
    
    /**
     * 找不到你要删除的
     * @param name
     */
    public KeyBoxException(String name) {
    	super(ErrorConstants.KEY_BOX_USED_TYPE, "key box is already in use!", "userManagement", "nofindexists");    		
    }
}
