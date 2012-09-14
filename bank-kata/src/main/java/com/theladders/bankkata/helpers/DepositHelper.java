package com.theladders.bankkata.helpers;

import com.theladders.bankkata.Amount;
import com.theladders.bankkata.Customer;
import com.theladders.bankkata.Deposit;
import com.theladders.bankkata.Time;

public class DepositHelper
{
  private final Amount amount;
  private final Customer customer;

  public DepositHelper(Amount amount, Customer customer)
  {
    this.amount = amount;
    this.customer = customer;
  }

  public TransactionHelper on(String time)
  {
    return new TransactionHelper(new Deposit(Time.at(time), amount), customer);
  }

  public TransactionHelper now()
  {
    return new TransactionHelper(new Deposit(Time.now(), amount), customer);
  }
}
