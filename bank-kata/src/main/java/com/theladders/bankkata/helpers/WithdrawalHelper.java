package com.theladders.bankkata.helpers;

import com.theladders.bankkata.Amount;
import com.theladders.bankkata.Customer;
import com.theladders.bankkata.Time;
import com.theladders.bankkata.Withdrawal;

public class WithdrawalHelper
{
  private final Amount amount;
  private final Customer customer;

  public WithdrawalHelper(Amount amount, Customer customer)
  {
    this.amount = amount;
    this.customer = customer;
  }

  public TransactionHelper on(String time)
  {
    return new TransactionHelper(new Withdrawal(Time.at(time), amount), customer);
  }

  public TransactionHelper now()
  {
    return new TransactionHelper(new Withdrawal(Time.now(), amount), customer);
  }
}

