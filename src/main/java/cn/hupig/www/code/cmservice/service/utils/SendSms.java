package cn.hupig.www.code.cmservice.service.utils;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

public class SendSms {

	private static final String SignName = "Yuoai"; 				// 短信签名
	private static final String TemplateCode = "SMS_212486262";				// 短信模板ID
	private static final String AccessKeyId = "LTAI5tAj5fTdSLwTd86AygNE";		// ak
	private static final String AccessKeySecret = "UN3RyeUp0MRG86OCQGbeZFsd5I5CGU";// sk
	
    /**
     * 
     * @param phone 需要发送的手机号
     * @param code 需要发送的验证码
     * @throws Exception
     */
    public static void sendCode(String phone,String code) throws Exception{
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", AccessKeyId, AccessKeySecret);//自己账号的AccessKey信息
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");//短信服务的服务接入地址
        request.setSysVersion("2017-05-25");//API的版本号
        request.setSysAction("SendSms");//API的名称
        request.putQueryParameter("PhoneNumbers", phone);//接收短信的手机号码
        request.putQueryParameter("SignName", SignName);//短信签名名称
        request.putQueryParameter("TemplateCode", TemplateCode);//短信模板ID
        request.putQueryParameter("TemplateParam", "{\"code\":\""+code+"\"}");//短信模板变量对应的实际值
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
