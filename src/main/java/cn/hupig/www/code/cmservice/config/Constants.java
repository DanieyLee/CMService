package cn.hupig.www.code.cmservice.config;

/**
 * Application constants.
 */
public final class Constants {

    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$";

    public static final String SYSTEM_ACCOUNT = "system";
    public static final String DEFAULT_LANGUAGE = "zh-cn";
    public static final String ANONYMOUS_USER = "anonymoususer";
    
	/**
	 * fileAddress开发机文件存放位置
	 * saveAddress服务器的文件访问路径
	 */
    public static final String fileAddress = "/home/apache-tomcat-10.0.0-M10/webapps/ROOT/";
    public static final String saveAddress = "/webfile/";
//    public static final String fileAddress = "C:/Users/lixin/Desktop/CMService/target/classes/static/content/images/";
//    public static final String saveAddress = "content/images/";
    

    private Constants() {
    }
}
