package com.beiang.airdog.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import com.beiang.airdog.constant.Constants;

/**
 * 文件处理工具
 */
public class FileUtils {

	public static void makedirs() {
		// 文件路径设置
		String parentPath = null;
		String fileName = Constants.FILE_NAME;

		// 存在SDCARD的时候，路径设置到SDCARD
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			parentPath = Environment.getExternalStorageDirectory().getPath() + File.separator + fileName;
			// 不存在SDCARD的时候，路径设置到ROM
		} else {
			parentPath = Environment.getDataDirectory().getPath() + "/data/" + fileName;
		}

		Settings.BASE_PATH = parentPath;

		Settings.TEMP_PATH = parentPath + "/temp";
		Settings.CONFIG_PATH = parentPath + "/config";
		Settings.CACHE_PATH = parentPath + "/cache";
		Settings.DEVICE_ICON_PATH = parentPath + File.separator + Constants.FILE_DEVICE_ICON;
		Settings.DEVICE_ALL_ICON_PATH = parentPath + File.separator + Constants.FILE_ALL_DEVICE_ICON;
		Settings.RECORD_PATH = parentPath + File.separator + Constants.RECORD;

		File fileBase = new File(Settings.BASE_PATH);
		fileBase.mkdirs();

		File fileTemp = new File(Settings.TEMP_PATH);
		fileTemp.mkdirs();

		File configTemp = new File(Settings.CONFIG_PATH);
		configTemp.mkdirs();

		File fileCahe = new File(Settings.CACHE_PATH);
		fileCahe.mkdirs();

		File file_DEVICE_ICON = new File(Settings.DEVICE_ICON_PATH);
		file_DEVICE_ICON.mkdirs();

		File file_DEVICE_ALL_ICON = new File(Settings.DEVICE_ALL_ICON_PATH);
		file_DEVICE_ALL_ICON.mkdirs();

		File file_DEVICE_ICON_PATH = new File(Settings.DEVICE_ICON_PATH, ".nomedia");
		file_DEVICE_ICON_PATH.mkdirs();

		File file_DEVICE_ALL_ICON_PATH = new File(Settings.DEVICE_ALL_ICON_PATH, ".nomedia");
		file_DEVICE_ALL_ICON_PATH.mkdirs();
		
		File file_RECORD_PATH = new File(Settings.RECORD_PATH);
		file_RECORD_PATH.mkdirs();
	}
}
