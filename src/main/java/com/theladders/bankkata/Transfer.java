package com.theladders.bankkata;

public class Transfer
{
  protected final Time time;
  protected final Amount amount;

  public Transfer(Time time, Amount amount)
  {
    this.time = time;
    this.amount = amount;
  }

  public Transaction asWithdrawal()
  {
    return new Withdrawal.Transfer(time, amount);
  }

  public Transaction asDeposit()
  {
    return new Deposit.Transfer(time, amount);
  }
}
