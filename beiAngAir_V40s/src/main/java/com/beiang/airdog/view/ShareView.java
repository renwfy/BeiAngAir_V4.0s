package com.beiang.airdog.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.MailShareContent;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.SinaShareContent;
import com.umeng.socialize.media.SmsShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;
import com.umeng_social_sdk_res_lib.UMConfig;

import java.io.File;

public class ShareView {
	Context context;
	String title = "贝昂空气净化器";
	String content = "欢迎使贝昂空气净化器，实时监控你的空气质量！";
	String url = " ";

	public ShareView(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}


	public void show(Bitmap bitmap) {
		UMImage localImage = new UMImage(context, bitmap);

		//微信
		WeiXinShareContent weixinContent = new WeiXinShareContent();
		//weixinContent.setShareContent(content);
		weixinContent.setTitle(title);
		//weixinContent.setTargetUrl(" ");
		weixinContent.setShareMedia(localImage);
		UMConfig.getInstance().getUMSocialService().setShareMedia(weixinContent);

		// 设置朋友圈分享的内容
		CircleShareContent circleMedia = new CircleShareContent();
		//circleMedia.setShareContent(content);
		circleMedia.setTitle(title);
		circleMedia.setShareMedia(localImage);
		//circleMedia.setTargetUrl(url);
		UMConfig.getInstance().getUMSocialService().setShareMedia(circleMedia);

		//QQ
		QQShareContent qqShareContent = new QQShareContent();
		//qqShareContent.setShareContent(content);
		qqShareContent.setTitle(title);
		qqShareContent.setShareMedia(localImage);
		//qqShareContent.setTargetUrl(url);
		UMConfig.getInstance().getUMSocialService().setShareMedia(qqShareContent);

		//QQ空间
		QZoneShareContent qzone = new QZoneShareContent();
		//qzone.setShareContent(content);
		//qzone.setTargetUrl(url);
		qzone.setTitle(title);
		qzone.setShareMedia(localImage);
		UMConfig.getInstance().getUMSocialService().setShareMedia(qzone);

		//sina
		SinaShareContent sinaContent = new SinaShareContent();
		//sinaContent.setShareContent(content);
		sinaContent.setTitle(title);
		sinaContent.setShareImage(localImage);
		UMConfig.getInstance().getUMSocialService().setShareMedia(sinaContent);

		// 设置短信分享内容
		SmsShareContent sms = new SmsShareContent();
		sms.setShareContent(content);
		sms.setShareImage(localImage);
		UMConfig.getInstance().getUMSocialService().setShareMedia(sms);

		// 设置邮件分享内容， 如果需要分享图片则只支持本地图片
		MailShareContent mail = new MailShareContent(localImage);
		mail.setTitle(title);
		//mail.setShareContent(content);
		// 设置tencent分享内容
		mail.setShareImage(localImage);
		UMConfig.getInstance().getUMSocialService().setShareMedia(mail);

		UMConfig.getInstance().getUMSocialService().getConfig().setPlatforms(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,
				SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.SINA, SHARE_MEDIA.SMS,SHARE_MEDIA.EMAIL);
		UMConfig.getInstance().getUMSocialService().openShare((Activity) context, false);
	}
}
