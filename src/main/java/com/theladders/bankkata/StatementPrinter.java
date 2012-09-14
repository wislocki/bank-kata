package com.theladders.bankkata;

import java.io.IOException;
import java.io.Writer;

public class StatementPrinter
{
  private final Account account;
  private final StatementFilter filter;

  public StatementPrinter(Account account, StatementFilter filter)
  {
    this.account = account;
    this.filter = filter;
  }

  public void to(Writer writer) throws IOException
  {
    account.print(writer, filter);
    writer.flush();
  }
}
