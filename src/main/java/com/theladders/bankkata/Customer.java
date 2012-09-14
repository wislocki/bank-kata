package com.theladders.bankkata;

import java.io.IOException;
import java.io.Writer;

import com.theladders.bankkata.helpers.DepositHelper;
import com.theladders.bankkata.helpers.StatementHelper;
import com.theladders.bankkata.helpers.TransferReceiverHelper;
import com.theladders.bankkata.helpers.WithdrawalHelper;

public class Customer
{
  private final Name name;

  public static Customer named(String name)
  {
    return new Customer(new Name(name));
  }

  private Customer(Name name)
  {
    if (name == null)
      throw new NullPointerException();

    this.name = name;
  }

  @Override
  public String toString()
  {
    return name.toString();
  }

  @Override
  public boolean equals(Object o)
  {
    return o instanceof Customer && name.equals(((Customer)o).name);
  }

  @Override
  public int hashCode()
  {
    return name.hashCode();
  }

  public Amount balanceAt(Bank bank)
  {
    return bank.balanceFor(this);
  }

  public void createAccountAt(Bank bank)
  {
    bank.createAccountFor(this);
  }

  public DepositHelper deposit(Amount amount)
  {
    return new DepositHelper(amount, this);
  }

  public WithdrawalHelper withdraw(Amount amount)
  {
    return new WithdrawalHelper(amount, this);
  }

  public TransferReceiverHelper transfer(Amount amount)
  {
    return new TransferReceiverHelper(amount, this);
  }

  public void printTo(Writer writer) throws IOException
  {
    writer.append(toString());
  }

  public StatementHelper printStatementFrom(Bank bank)
  {
    return new StatementHelper(accountAt(bank));
  }

  public Account accountAt(Bank bank)
  {
    return bank.accountFor(this);
  }
}

