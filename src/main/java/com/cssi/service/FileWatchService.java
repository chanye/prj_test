package com.cssi.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class FileWatchService
{
	public static void main(String[] args)
	{
		FileWatchService s = new FileWatchService();
	}
	private static Logger log = Logger.getLogger(FileWatchService.class);

	private static Properties SysLocalPropObject = null;

	// 配置文件路径
	private static String defaultPropFileName =System.getProperty("user.dir")+ "/conf/config.properties";
	// 文件更新标识
	protected long lastModifiedData = -1;

	private static FileWatchService instance;

	public static FileWatchService getInstance()
	{
		if (instance == null) {
			instance = new FileWatchService();
		}
		return instance;
	}

	/**
	 * @description 私有构造器启动一个线程实时监控配置文件
	 */
	private FileWatchService()
	{
		SysLocalPropObject = new Properties();
		String tempPath = defaultPropFileName;
		File tempFile = new File(tempPath);
		final String filePath;
		if (tempFile.exists()) {
			filePath = tempPath;
		} else {
			filePath = "system.properties";
		}

		final FileWatchService self = this;
		File propertyFile = new File(filePath);
		if (propertyFile.exists())
			reloadFile(propertyFile);

		// 循环监控配置文件的变化，一旦发现文件发生变化了则重读配置文件
		Thread t = new Thread()
		{
			public void run()
			{
				while (true) {
					// 间隔1秒
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}

					try {
						File propertyFile = new File(filePath);
						if (self.lastModifiedData != propertyFile
								.lastModified()) {
							self.reloadFile(propertyFile);
							self.lastModifiedData = propertyFile.lastModified();
						}
					} catch (Exception e) {

					}
				}
			}
		};
		t.start();
	}

	protected void reloadFile(File propertyFile)
	{
		try {
			FileInputStream fis = new FileInputStream(propertyFile);
			SysLocalPropObject.load(fis);
			System.out.println(SysLocalPropObject.get("exeName"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
