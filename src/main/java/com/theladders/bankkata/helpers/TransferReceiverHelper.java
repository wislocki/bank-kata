package com.theladders.bankkata.helpers;

import com.theladders.bankkata.Amount;
import com.theladders.bankkata.Customer;
import com.theladders.bankkata.TransferPair;

public class TransferReceiverHelper
{
  private final Amount amount;
  private final Customer sender;

  public TransferReceiverHelper(Amount amount, Customer sender)
  {
    this.amount = amount;
    this.sender = sender;
  }

  public TransferTimeHelper to(Customer receiver)
  {
    return new TransferTimeHelper(amount, new TransferPair(sender, receiver));
  }
}
