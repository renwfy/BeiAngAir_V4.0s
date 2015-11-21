package com.beiang.airdog;

import android.app.Application;

import com.beiang.airdog.net.business.entity.CurrentDevice;
import com.beiang.airdog.net.business.entity.CurrentUser;
import com.beiang.airdog.ui.ActivityManager;
import com.beiang.airdog.utils.FileUtils;
import com.beiang.airdog.utils.LogUtil;
import com.beiang.airdog.utils.Settings;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng_social_sdk_res_lib.UMConfig;

public class BeiAngAirApplaction extends Application {
	public static BeiAngAirApplaction applaction;
	public static IWXAPI api;
	
	@Override
	public void onCreate() {
		super.onCreate();
		applaction = this;

		init();
	}
	
	public static BeiAngAirApplaction getInstance() {
		return applaction;
	}
	public void init() {
		//微信注册
		String appId = "wx037f301a0ec34f84";
		api = WXAPIFactory.createWXAPI(this, appId,true);
		api.registerApp(appId);

		UMConfig.onCreate(getApplicationContext());
		
		//初始化屏幕大小
		Settings.P_HEIGHT = getResources().getDisplayMetrics().heightPixels;
		Settings.P_WIDTH = getResources().getDisplayMetrics().widthPixels;

		//初始化文件夹
		FileUtils.makedirs();
	}

	// 退出应用程序
	public void exit() {
		ActivityManager.getScreenManager().popAllActivity();
		onTerminate();
	}
	
	@Override
	public void onTerminate() {
		LogUtil.i("onTerminate");
		CurrentUser.instance().clean();
		CurrentDevice.instance().clean();
		super.onTerminate();
	}
	
}