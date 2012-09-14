package com.theladders.bankkata.helpers;

import java.io.IOException;
import java.io.Writer;

import com.theladders.bankkata.Account;
import com.theladders.bankkata.StatementFilter;
import com.theladders.bankkata.StatementPrinter;

public class StatementHelper
{
  private final Account account;

  public StatementHelper(Account account)
  {
    this.account = account;
  }

  public StatementPrinter withFilter(StatementFilter filter)
  {
    return new StatementPrinter(account, filter);
  }

  public void to(Writer writer) throws IOException
  {
    new StatementPrinter(account, StatementFilter.NONE).to(writer);
  }
}
