package com.theladders.bankkata;

public class Name
{
  private final String name;

  public Name(String name)
  {
    if (name == null)
      throw new NullPointerException();

    this.name = name;
  }

  @Override
  public String toString()
  {
    return name;
  }

  @Override
  public int hashCode()
  {
    return name.hashCode();
  }

  @Override
  public boolean equals(Object o)
  {
    return o instanceof Name && name.equals(((Name)o).name);
  }
}
