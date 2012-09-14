package com.theladders.bankkata;

public enum StatementFilter
{
  NONE {
    @Override
    public boolean accepts(Transaction transaction)
    {
      return true;
    }
  },
  WITHDRAWALS_ONLY {
    @Override
    public boolean accepts(Transaction transaction)
    {
      return transaction.isWithdrawal();
    }
  },
  DEPOSITS_ONLY {
    @Override
    public boolean accepts(Transaction transaction)
    {
      return transaction.isDeposit();
    }
  },
  TRANSFERS_ONLY {
    @Override
    public boolean accepts(Transaction transaction)
    {
      return transaction.isTransfer();
    }
  };

  public abstract boolean accepts(Transaction transaction);
}
