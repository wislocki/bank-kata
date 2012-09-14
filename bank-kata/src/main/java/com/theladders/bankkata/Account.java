package com.theladders.bankkata;

import java.io.IOException;
import java.io.Writer;

public class Account
{
  private final Customer owner;
  private final Transactions transactions;

  public static Account forCustomer(Customer owner)
  {
    if (owner == null)
      throw new NullPointerException();

    return new Account(owner, Transactions.none());
  }

  private Account(Customer owner, Transactions transactions)
  {
    this.owner = owner;
    this.transactions = transactions;
  }

  public Amount balance()
  {
    return transactions.balance();
  }

  public void addTransaction(Transaction transaction)
  {
    transactions.add(transaction);
  }

  public void print(Writer writer, StatementFilter filter) throws IOException
  {
    writer.append("Account for: ");
    owner.printTo(writer);
    writer.append("\n\n");
    transactions.print(writer, filter);
  }
}
