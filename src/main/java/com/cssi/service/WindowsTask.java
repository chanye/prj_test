package com.cssi.service;

public class WindowsTask
{
  private String name;
  private Integer pid;
  private String sessionName;
  private Integer sessionId;
  private long mem;

  public String toString()
  {
    return "映像名称:\t" + this.name + ", PID:\t" + this.pid + ", 会话名:\t" + this.sessionName + 
      ", 会话编号:\t" + this.sessionId + ", 内存使用:\t" + this.mem + " K\n";
  }

  public String getName()
  {
    return this.name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public Integer getPid()
  {
    return this.pid;
  }

  public void setPid(Integer pid)
  {
    this.pid = pid;
  }

  public String getSessionName()
  {
    return this.sessionName;
  }

  public void setSessionName(String sessionName)
  {
    this.sessionName = sessionName;
  }

  public Integer getSessionId()
  {
    return this.sessionId;
  }

  public void setSessionId(Integer sessionId)
  {
    this.sessionId = sessionId;
  }

  public long getMem()
  {
    return this.mem;
  }

  public void setMem(long mem)
  {
    this.mem = mem;
  }
}