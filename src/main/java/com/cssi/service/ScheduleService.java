package com.cssi.service;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduleService
{
	private static ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;
	
	private static int corePoolSize = 5;
	private static long initialDelay =5;
	private static long period = 5;
	
	public static void main(String[] args)
	{
		Runnable runnable = new Runnable()
		{
			
			public void run()
			{
				System.out.println("........");
			}
		};
		schedule(runnable, initialDelay, period);
	}
	
	public static void schedule( Runnable runnable,long initialDelay,long period )
	{
		scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(corePoolSize);
		scheduledThreadPoolExecutor.scheduleAtFixedRate(runnable, initialDelay, period, TimeUnit.SECONDS);
	}
}
