package com.theladders.bankkata;

public class TransferPair
{
  private final Customer sender;
  private final Customer receiver;

  public TransferPair(Customer sender,
                      Customer receiver)
  {
    this.sender = sender;
    this.receiver = receiver;
  }

  public Account senderAccount(Accounts accounts)
  {
    return accounts.findFor(sender);
  }

  public Account receiverAccount(Accounts accounts)
  {
    return accounts.findFor(receiver);
  }
}
