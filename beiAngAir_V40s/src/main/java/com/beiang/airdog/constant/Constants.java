package com.beiang.airdog.constant;

public class Constants {
	// 本地文件
	public static final String FILE_NAME = "BeiAngAir";
	/** 文件夹名 **/
	public static final String FILE_DEVICE_ICON = "DeviceIcon";
	/** 文件夹名 **/
	public static final String FILE_ALL_DEVICE_ICON = "AllDeviceIcon";
	/**录音*/
	public static final String RECORD = "record";

	/** 设备类型 */
	public static class Device {
		public static final int DT_280B = 1;
		public static final int DT_280E = 2;
		public static final int DT_CAR = 3;
		public static final int DT_AURA100 = 4;
		public static final int DT_JY300 = 5;
		public static final int DT_JY500 = 6;
		public static final int DT_JY300S = 8;
		public static final int DT_Airdog = 160;
		public static final int DT_TAir = 80;
		public static final int DT_Outlet = 33;
		public static final int DT_Light = 34;
		public static final int DT_FA20 = 257;
		public static final int DT_FC1 = 258;
	}


	public static String getDeviceName(int deviceType) {
		String name = "";
		switch (deviceType) {
		case Device.DT_280B:
			name = "280B";
			break;
		case Device.DT_280E:
			name = "280E";
			break;
		case Device.DT_CAR:
			name = "车载";
			break;
		case Device.DT_AURA100:
			name = "AURA100";
			break;
		case Device.DT_Airdog:
			name = "Airdog";
			break;
		case Device.DT_JY300:
			name = "JY300";
			break;
		case Device.DT_JY500:
			//name = "JY500";
			name = "JY500/JY550";
			break;
		case Device.DT_JY300S:
			name = "JY 300";
			break;
		case Device.DT_TAir:
			name = "TAir";
			break;
		case Device.DT_FC1:
			name = "FC1";
			break;
		case Device.DT_FA20:
			name = "FA20";
			break;
		default:
			name = "280E";
			break;
		}
		return name;
	}

}
