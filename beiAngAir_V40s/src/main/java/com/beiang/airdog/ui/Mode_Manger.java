package com.beiang.airdog.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Handler;
import android.view.View;

import com.beiang.airdog.ui.activity.AboutActivity;
import com.beiang.airdog.ui.activity.BindUserActivity;
import com.beiang.airdog.ui.activity.ConfigDeviceActivity;
import com.beiang.airdog.ui.activity.EditInfoActivity;
import com.beiang.airdog.ui.activity.HelpActivity;
import com.beiang.airdog.ui.activity.LoginActivity;
import com.beiang.airdog.ui.activity.RegisterActivity;
import com.beiang.airdog.ui.model.MenuEntity;
import com.beiang.airdog.view.ShareView;

public class Mode_Manger {
	public static void startModesActivity(final Context context, MenuEntity ety) {
		if ("acout".equals(ety.getMenu_key())) {
			context.startActivity(new Intent(context, LoginActivity.class));
		}
		if ("plus".equals(ety.getMenu_key())) {
			((Activity) context).startActivityForResult(new Intent(context, ConfigDeviceActivity.class), 100);
		}
		if ("minus".equals(ety.getMenu_key())) {
		}
		if ("edit".equals(ety.getMenu_key())) {
			((Activity) context).startActivityForResult(new Intent(context, EditInfoActivity.class), 200);
		}
		if ("help".equals(ety.getMenu_key())) {
			context.startActivity(new Intent(context, HelpActivity.class));
		}
		if ("share".equals(ety.getMenu_key())) {
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					final Bitmap bitmap = takeScreenShot((Activity) context);
					new ShareView(context).show(bitmap);
				}
			}, 500);
		}
		if ("Auth".equals(ety.getMenu_key())) {
		}
		if ("regist".equals(ety.getMenu_key())) {
			context.startActivity(new Intent(context, RegisterActivity.class));
		}
		if ("about".equals(ety.getMenu_key())) {
			context.startActivity(new Intent(context, AboutActivity.class));
		}
		if ("versions".equals(ety.getMenu_key())) {
			context.startActivity(new Intent(context, HelpActivity.class));
		}
		if ("guide".equals(ety.getMenu_key())) {
		}
		if ("logout".equals(ety.getMenu_key())) {
		}
		if ("bind".equals(ety.getMenu_key())) {
			context.startActivity(new Intent(context, BindUserActivity.class));
		}

	}

	/**
	 * 屏幕截图
	 * 
	 * @param activity
	 * @return
	 */
	private static Bitmap takeScreenShot(Activity activity) {
		// View是你需要截图的View
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap b1 = view.getDrawingCache();

		// 获取状态栏高度
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;

		// 获取屏幕长和高
		int width = activity.getWindowManager().getDefaultDisplay().getWidth();
		int height = activity.getWindowManager().getDefaultDisplay().getHeight();
		// 去掉标题栏
		Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height - statusBarHeight);
		view.destroyDrawingCache();
		return b;
	}

}
