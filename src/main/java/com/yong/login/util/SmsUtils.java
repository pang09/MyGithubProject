package com.yong.login.util;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teaopenapi.models.Config;


import java.util.Map;

/**
 * 发短信的工具类2.0
 * @author NewBoy
 * @since 2021-7-14
 */
public class SmsUtils {

    /**
     * 换成你的accessKeyId和accessKeySecret
     */
    private static String accessKeyId = "LTAI5tHNkpeCSEWTQCePEvWU";
    private static String accessKeySecret = "0GlJaZOPeyq2ulrUEUCGPDhdktxa1P";

    /**
     * 使用AK&SK初始化账号Client
     * @param accessKeyId
     * @param accessKeySecret
     * @return Client
     * @throws Exception
     */
    public static Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        Config config = new Config()
                // 您的AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 您的AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new Client(config);
    }

    /**
     * 发送短信
     * @param telephone 电话号码
     * @param signName 签名
     * @param templateCode 模板编号
     * @param code 验证码
     * @return 返回的状态码为OK，则发送成功
     */
    public static String send(String telephone, String signName, String templateCode, String code) {
        try {
            Client client = SmsUtils.createClient(accessKeyId, accessKeySecret);
            SendSmsRequest sendSmsRequest = new SendSmsRequest()
                    .setPhoneNumbers(telephone)
                    .setSignName(signName)  //短信签名
                    .setTemplateCode(templateCode)  //模板编号
                    .setTemplateParam("{\"code\":\"" + code + "\"}");
            //发送短信，获取发送结果
            SendSmsResponse sendSmsResponse = client.sendSms(sendSmsRequest);
            //将结果转成Map对象
            Map<String, Object> map = sendSmsResponse.toMap();
            //获取主体部分
            Map<String, String> body = (Map<String, String>) map.get("body");
            System.out.println(body);
            return body.get("Code");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //失败返回空
        return null;
    }

    /**
     * 调用方式
     * @param args
     */
    public static void main(String[] args) {
       // String code = SmsUtils.send("18929281046", "旅游", "SMS_195723031", "520520");
       // System.out.println(code);
    }
}
