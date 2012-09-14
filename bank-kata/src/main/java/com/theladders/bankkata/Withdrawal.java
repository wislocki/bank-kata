package com.theladders.bankkata;

public class Withdrawal extends Transaction
{
  public Withdrawal(Time time, Amount amount)
  {
    super(time, amount);
  }

  @Override
  public void validWith(Amount availableAmount)
  {
    if (availableAmount.lessThan(amount))
      throw new NotEnoughFundsException(availableAmount, amount);
  }

  @Override
  public Amount on(Amount a)
  {
    return a.minus(amount);
  }

  @Override
  public boolean isWithdrawal() { return true; }

  @Override
  public String toString()
  {
    return time + " -"  + amount;
  }

  public static class Transfer extends Withdrawal
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
