package com.cssi.service;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

import com.cssi.util.PropUtils;

public class ProcessService
{
	private final static Logger logger = Logger.getLogger(ProcessService.class);
	public static void main(String[] args)
	{
		String exePath = PropUtils.getValue("exePath");
		//System.out.println(exePath.substring(exePath.lastIndexOf("/")+1));
		//killProcess(exePath.substring(exePath.lastIndexOf("/")+1));
		//startProcess( exePath);
		startProc(exePath);
		
	}
	public static void killProcess(String exeName)
	{
		try {
			String[] cmd = { "tasklist" };
			Process proc = Runtime.getRuntime().exec(cmd);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					proc.getInputStream()));
			String string_Temp = in.readLine();
			while (string_Temp != null) {
				if (string_Temp.indexOf(exeName) != -1){
					
					Runtime.getRuntime().exec("Taskkill /f /im "+ exeName);
					System.out.println("Taskkill /f /im "+ exeName);
				}
					
				string_Temp = in.readLine();
			}
		} catch (Exception e) {
			logger.info("关闭"+exeName+"进程出错，错误信息"+e.getMessage());
		}
	}
    public static String executeCmd(String command) throws IOException {  
        logger.info("Execute command : " + command);  
        Runtime runtime = Runtime.getRuntime();  
        Process process = runtime.exec("cmd /c " + command);  
        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));  
        String line = null;  
        StringBuilder build = new StringBuilder();  
        while ((line = br.readLine()) != null) {  
            logger.info(line);  
            build.append(line);  
        }  
        return build.toString();  
    }  
    public static void startProc(String processName) { 
	     logger.info("启动应用程序：" + processName);  
	         try {  
	             Desktop.getDesktop().open(new File(processName));  
	         } catch (Exception e) {  
	             e.printStackTrace();  
	             logger.error("应用程序：" + processName + "不存在！");  
	         }  
	         
	         
           
    }
    /**
     * 
     * @param exePath
     */
	public static void startProcess(String exePath)
	{
		ProcessBuilder builder = new ProcessBuilder();
		Runtime runtime = Runtime.getRuntime();
		Process process;
		try {
			process = runtime.exec("cmd.exe /c "+exePath);
			InputStream inputStream = process.getInputStream();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream,"gb2312"));
			String line = null;
			while((line = br.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
