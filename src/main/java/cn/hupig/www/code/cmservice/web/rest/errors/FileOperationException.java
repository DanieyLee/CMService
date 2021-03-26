package cn.hupig.www.code.cmservice.web.rest.errors;

public class FileOperationException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    /**
     * 文件上传错误!
     */
    public FileOperationException() {
    	super(ErrorConstants.FILE_ALREADY_USED_TYPE, "file is already in use!", "userManagement", "fileexists");
    }
    
}
