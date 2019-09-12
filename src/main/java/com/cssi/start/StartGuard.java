package com.cssi.start;

import com.cssi.service.ProcessService;
import com.cssi.service.ScheduleService;
import com.cssi.util.PropUtils;

public class StartGuard
{
	private static int corePoolSize = 5;
	private static long initialDelay =5;
	private static long period = Integer.parseInt(PropUtils.getValue("schedulePeriod"));
	public static void main(String[] args)
	{
		Runnable runnable = new Runnable()
		{
			
			public void run()
			{
				System.out.println("........");
				String exePath = PropUtils.getValue("exePath");
				ProcessService.killProcess(exePath.substring(exePath.lastIndexOf("/")+1));
				ProcessService.startProc(exePath);
			}
		};
		ScheduleService.schedule(runnable, initialDelay, period);
	}
}
