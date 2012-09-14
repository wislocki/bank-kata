package com.theladders.bankkata.helpers;

import com.theladders.bankkata.Bank;
import com.theladders.bankkata.Customer;
import com.theladders.bankkata.Transaction;

public class TransactionHelper
{
  private final Transaction transaction;
  private final Customer customer;

  public TransactionHelper(Transaction transaction, Customer customer)
  {
    this.transaction = transaction;
    this.customer = customer;
  }

  public void at(Bank bank)
  {
    bank.addTransaction(customer, transaction);
  }

  public void from(Bank bank)
  {
    bank.addTransaction(customer, transaction);
  }
}
