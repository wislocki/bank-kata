package com.theladders.bankkata;

public class Deposit extends Transaction
{
  public Deposit(Time time, Amount amount)
  {
    super(time, amount);
  }

  @Override
  public void validWith(Amount availableAmount) {}

  @Override
  public Amount on(Amount a)
  {
    return amount.plus(a);
  }

  @Override
  public boolean isDeposit() { return true; }

  @Override
  public String toString()
  {
    return time + " "  + amount;
  }

  public static class Transfer extends Deposit
  {
    public Transfer(Time time, Amount amount)
    {
      super(time, amount);
    }

    @Override
    public boolean isTransfer() { return true; }

    @Override
    public String toString()
    {
      return super.toString() + " (T)";
    }
  }
}
