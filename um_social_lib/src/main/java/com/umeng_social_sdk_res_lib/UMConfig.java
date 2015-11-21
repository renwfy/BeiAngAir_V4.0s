package com.umeng_social_sdk_res_lib;

import android.app.Activity;
import android.content.Context;

import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.sso.EmailHandler;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.SmsHandler;
import com.umeng.socialize.sso.TencentWBSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;

/**
 * Created by Administrator on 2015/8/4.
 */
public class UMConfig {
    private static Context applicationContext;
    private static UMConfig instance;
    private static UMSocialService mController;

    public static void onCreate(Context context) {
        applicationContext = context;
        instance = new UMConfig();
        mController = UMServiceFactory.getUMSocialService("com.umeng.login");
        addCustomPlatforms();
    }

    public static UMConfig getInstance() {
        return instance;
    }

    public UMSocialService getUMSocialService() {
        return mController;
    }

    /**
     * 添加所有的平台</br>
     */
    private static void addCustomPlatforms() {
        // 添加新浪SSO授权
        mController.getConfig().setSsoHandler(new SinaSsoHandler());
        // 添加腾讯微博SSO授权
        mController.getConfig().setSsoHandler(new TencentWBSsoHandler());

        // 添加QQ、QZone平台
        addQQQZonePlatform();

        // 添加微信平台
        addWXPlatform();
        // 添加短信平台
        addSMS();
        // 添加email平台
        addEmail();

        //关闭SDK中的toast
        mController.getConfig().closeToast();
    }

    /**
     * @功能描述 : 添加QQ平台支持
     * @return
     */
    private static void addQQQZonePlatform() {
        String appId = "1102154446";
        String appKey = "H5fwGHchvwGG92Ce";
        // 添加QQ支持, 并且设置QQ分享内容的target url
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler((Activity)applicationContext,
                appId, appKey);
        qqSsoHandler.addToSocialSDK();

        // 添加QZone平台
        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler((Activity)applicationContext, appId, appKey);
        qZoneSsoHandler.addToSocialSDK();
    }

    /**
     * @return
     * @功能描述 : 添加微信平台分享
     */
    private static void addWXPlatform() {
        String appId = "wxc8f3dbe24ba8e504";
        String appSecret = "dd231458131f99f19185880f3b86bb39";
        // 添加微信平台
        UMWXHandler wxHandler = new UMWXHandler(applicationContext, appId, appSecret);
        wxHandler.addToSocialSDK();

        // 支持微信朋友圈
        UMWXHandler wxCircleHandler = new UMWXHandler(applicationContext, appId,
                appSecret);
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();
    }

    /**
     * 添加短信平台</br>
     */
    private static void addSMS() {
        // 添加短信
        SmsHandler smsHandler = new SmsHandler();
        smsHandler.addToSocialSDK();
    }

    /**
     * 添加Email平台</br>
     */
    private static void addEmail() {
        // 添加email
        EmailHandler emailHandler = new EmailHandler();
        emailHandler.addToSocialSDK();
    }

}
