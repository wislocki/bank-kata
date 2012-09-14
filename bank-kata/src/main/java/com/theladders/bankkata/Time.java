package com.theladders.bankkata;

import java.io.IOException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

public class Time implements Comparable<Time>
{
  private static final DateFormat FORMAT = DateFormat.getDateTimeInstance();

  private final Date time;

  public static Time now()
  {
    return new Time(new Date());
  }

  public static Time at(String timeString)
  {
    try
    {
      return new Time(FORMAT.parse(timeString));
    }
    catch (ParseException e)
    {
      throw new InvalidException(e);
    }
  }

  private Time(Date time)
  {
    this.time = time;
  }

  @Override
  public String toString()
  {
    return time.toString();
  }

  @Override
  public int compareTo(Time t)
  {
    return time.compareTo(t.time);
  }

  @Override
  public int hashCode()
  {
    return time.hashCode();
  }

  @Override
  public boolean equals(Object o)
  {
    return (o instanceof Time) && time.equals(((Time) o).time);
  }

  public static class InvalidException extends RuntimeException
  {
    public InvalidException(Throwable cause)
    {
      super(cause);
    }
  }

  public void printTo(Writer writer) throws IOException
  {
    writer.append(toString());
  }

  public boolean after(Time otherTime)
  {
    return time.after(otherTime.time) || time.equals(otherTime.time);
  }
}
