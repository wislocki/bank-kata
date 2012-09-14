package com.theladders.bankkata.helpers;

import com.theladders.bankkata.*;

public class TransferTimeHelper
{
  private final Amount amount;
  private final TransferPair pair;

  public TransferTimeHelper(Amount amount, TransferPair pair)
  {
    this.amount = amount;
    this.pair = pair;
  }

  public TransferHelper on(String time)
  {
    return new TransferHelper(new Transfer(Time.at(time), amount), pair);
  }

  public TransferHelper now()
  {
    return new TransferHelper(new Transfer(Time.now(), amount), pair);
  }
}
