package com.theladders.bankkata;

import java.io.IOException;
import java.io.Writer;
import java.util.LinkedList;

public class Transactions
{
  private final LinkedList<Transaction> transactions = new LinkedList<>();

  public static Transactions none()
  {
    return new Transactions();
  }

  public synchronized Amount balance()
  {
    Amount amount = Amount.ZERO;

    for (Transaction transaction : transactions)
      amount = transaction.on(amount);

    return amount;
  }

  public synchronized void add(Transaction transaction)
  {
    transaction.validWith(balance());
    transaction.happensAfter(transactions.peekLast());
    transactions.add(transaction);
  }

  private synchronized void printBalance(Writer writer) throws IOException
  {
    writer.append("Balance as of ");
    Time.now().printTo(writer);
    writer.append(": ");
    balance().printTo(writer);
  }

  public synchronized void print(Writer writer, StatementFilter filter) throws IOException
  {
    for (Transaction transaction : transactions)
    {
      boolean printed = transaction.print(writer, filter);
      newline(printed, writer);
    }

    writer.append("\n");
    printBalance(writer);
  }

  private static void newline(boolean shouldPrint, Writer writer) throws IOException
  {
    if (shouldPrint)
      writer.append('\n');
  }
}
