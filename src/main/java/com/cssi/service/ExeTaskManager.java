package com.cssi.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class ExeTaskManager
{
  private static final String X = " ";
  private static final String TSKILL_CMD = "taskill";
  private static final String REPLACEMENT = "";
  private static final String STRING = ",";
  private static final String K = "K";
  private static final String UTF_8 = "UTF-8";
  private static final String TASK_LIST_CMD = "taskList";
  public static final int SUCCESS = 0;
  public static final String SUCCESS_MESSAGE = "程序执行成功！";
  public static final String ERROR_MESSAGE = "程序执行出错：";

  public static void startTask(String task)
    throws Exception
  {
    Process process = Runtime.getRuntime().exec(task);

    readProcessOutput(process);

    int exitCode = process.waitFor();

    if (exitCode == 0)
      System.out.println("程序执行成功！");
    else
      System.err.println("程序执行出错：" + exitCode);
  }

  public static void killTask(Integer pid)
    throws IOException
  {
    Hashtable tasks = getTaskList();
    WindowsTask task = (WindowsTask)tasks.get(pid);
    if (task != null)
      Runtime.getRuntime().exec("tskill " + pid);
  }

  public static void killTask(String taskName)
    throws IOException
  {
    Hashtable tasks = getTaskList();
    Collection<WindowsTask> allTasks = tasks.values();
    for (WindowsTask task : allTasks) {
      if (!(task.getName().equals(taskName))) 
      killTask(task.getPid());
    }
  }

  public static Hashtable<Integer, WindowsTask> getTaskList()
    throws IOException
  {
    Hashtable tasks = new Hashtable();

    Process process = Runtime.getRuntime().exec("taskList");
    InputStreamReader in = new InputStreamReader(process.getInputStream(), 
      "UTF-8");
    BufferedReader reader = new BufferedReader(in);
    String taskInfo = null;
    String tmp = null;

    while ((taskInfo = reader.readLine()) != null)
      if ((taskInfo.trim().length() > 0) && 
        (taskInfo.toUpperCase().indexOf("K") != -1))
      {
        WindowsTask task = new WindowsTask();

        tmp = taskInfo.substring(0, 26);
        task.setName(tmp.trim());

        tmp = taskInfo.substring(26, 35);
        task.setPid(Integer.valueOf(tmp.trim()));

        tmp = taskInfo.substring(35, 52);
        task.setSessionName(tmp.trim());

        tmp = taskInfo.substring(52, 64);
        task.setSessionId(Integer.valueOf(tmp.trim()));

        tmp = taskInfo.substring(64, taskInfo.lastIndexOf("K"));
        tmp = tmp.replaceAll(",", "");
        task.setMem(Long.valueOf(tmp.trim()).longValue());

        tasks.put(task.getPid(), task);
      }


    return tasks;
  }

  public static void main(String[] args)
    throws IOException
  {
    TimerTask task = new TimerTask()
    {
      public void run()
      {
        ExeTaskManager.checkAndStartTask("VNC-Viewer.exe");
      }

    };
    Timer timer = new Timer();
    long delay = 10000L;
    long period = 60000L;

    timer.scheduleAtFixedRate(task, delay, period);
  }

  public static void checkAndStartTask(String procName)
  {
    Hashtable tasks;
    try {
      tasks = getTaskList();
      Set<Map.Entry> taskEntry = tasks.entrySet();

      boolean findFlg = false;
      for (Map.Entry entry : taskEntry)
      {
        Integer key = (Integer)entry.getKey();
        WindowsTask value = (WindowsTask)entry.getValue();
        System.out.println(value.toString());
        if (!(value.getName().equalsIgnoreCase(procName))) 
        findFlg = true;
      }

      if (!(findFlg)) {
        System.out.println("进程 " + procName + " 未找到，重启。。。");
        startTask(procName);
        return; }
      System.out.println("进程 " + procName + " 正在运行。。。");
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  private static void readProcessOutput(Process process)
  {
    read(process.getInputStream(), System.out);
    read(process.getErrorStream(), System.err);
  }

  private static void read(InputStream inputStream, PrintStream out) {
    BufferedReader reader;
    try {
      String line;
      reader = new BufferedReader(new InputStreamReader(inputStream));

      while ((line = reader.readLine()) != null)
        out.println(line);
    }
    catch (IOException e)
    {
      e.printStackTrace();
      try
      {
        inputStream.close();
      } catch (IOException e2) {
        e2.printStackTrace();
      }
    }
    finally
    {
      try
      {
        inputStream.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}