package cn.hupig.www.code.cmservice.service.utils;

import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.teaopenapi.models.Config;

public class SendSms {

	private static final String SignName = "Yuoai"; 				// 短信签名
	private static final String TemplateCode = "201";				// 短信模板ID
	private static final String AccessKeyId = "accessKeyId";		// ak
	private static final String AccessKeySecret = "accessKeySecret";// sk
	
	
	
	/**
     * 使用AK&SK初始化账号Client
     * @param accessKeyId
     * @param accessKeySecret
     * @return Client
     * @throws Exception
     */
    public static com.aliyun.dysmsapi20170525.Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        Config config = new Config()
                // 您的AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 您的AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new com.aliyun.dysmsapi20170525.Client(config);
    }
    
    /**
     * 
     * @param phone 需要发送的手机号
     * @param code 需要发送的验证码
     * @throws Exception
     */
    public static void sendCode(String phone,String code) throws Exception {
        com.aliyun.dysmsapi20170525.Client client = SendSms.createClient(AccessKeyId, AccessKeySecret);
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setPhoneNumbers(phone)
                .setSignName(SignName)
                .setTemplateCode(TemplateCode)
                .setTemplateParam(code);
        client.sendSms(sendSmsRequest);
    }
}
