package com.theladders.bankkata;

import java.util.HashMap;
import java.util.Map;

public class Accounts
{
  private final Map<Customer, Account> accounts;

  public static Accounts none()
  {
    return new Accounts();
  }

  private Accounts()
  {
    accounts = new HashMap<>();
  }

  public void add(Customer owner, Account account)
  {
    accounts.put(owner, account);
  }

  public Account findFor(Customer customer)
  {
    assertCustomerExists(customer);

    return accounts.get(customer);
  }

  public synchronized void addTransaction(Customer customer, Transaction transaction)
  {
    Account account = findFor(customer);

    account.addTransaction(transaction);
  }

  public synchronized void addTransfer(TransferPair pair, Transfer transfer)
  {
    Account senderAccount = pair.senderAccount(this);
    Account receiverAccount = pair.receiverAccount(this);

    senderAccount.addTransaction(transfer.asWithdrawal());
    receiverAccount.addTransaction(transfer.asDeposit());
  }

  private void assertCustomerExists(Customer customer)
  {
    if (!accounts.containsKey(customer))
      throw new CustomerNotFoundException(customer);
  }
}
