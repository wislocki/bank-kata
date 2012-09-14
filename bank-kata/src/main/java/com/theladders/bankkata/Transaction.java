package com.theladders.bankkata;

import java.io.IOException;
import java.io.Writer;

public abstract class Transaction
{
  protected final Time time;
  protected final Amount amount;

  public Transaction(Time time, Amount amount)
  {
    this.time = time;
    this.amount = amount;
  }

  public void happensAfter(Transaction transaction)
  {
    if (transaction != null && !time.after(transaction.time))
      throw new OutOfOrderException(time, transaction.time);
  }

  public boolean print(Writer writer, StatementFilter filter) throws IOException
  {
    boolean accepted = filter.accepts(this);

    if (accepted)
      printTo(writer);

    return accepted;
  }

  public void printTo(Writer writer) throws IOException
  {
    writer.append(toString());
  }

  public boolean isDeposit()    { return false; }
  public boolean isWithdrawal() { return false; }
  public boolean isTransfer()   { return false; }

  public abstract void validWith(Amount a);
  public abstract Amount on(Amount a);

  public static class OutOfOrderException extends RuntimeException
  {
    public OutOfOrderException(Time shouldBeBefore, Time shouldBeAfter)
    {
      super(shouldBeBefore + " should be equal to or before " + shouldBeAfter + ", but comes afterward.");
    }
  }
}
