package com.theladders.bankkata.helpers;

import com.theladders.bankkata.Bank;
import com.theladders.bankkata.Transfer;
import com.theladders.bankkata.TransferPair;

public class TransferHelper
{
  private final Transfer transfer;
  private final TransferPair transferPair;

  public TransferHelper(Transfer transfer, TransferPair transferPair)
  {
    this.transfer = transfer;
    this.transferPair = transferPair;
  }

  public void at(Bank bank)
  {
    bank.addTransfer(transferPair, transfer);
  }
}
