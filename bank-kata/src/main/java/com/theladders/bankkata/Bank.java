package com.theladders.bankkata;


public class Bank
{
  private final Name name;
  private final Accounts accounts;

  public static Bank named(String name)
  {
    return new Bank(new Name(name));
  }

  private Bank(Name name)
  {
    if (name == null)
      throw new NullPointerException();

    this.name = name;
    accounts = Accounts.none();
  }

  @Override
  public String toString()
  {
    return name.toString();
  }

  @Override
  public boolean equals(Object o)
  {
    return o instanceof Bank && name.equals(((Bank)o).name);
  }

  @Override
  public int hashCode()
  {
    return name.hashCode();
  }

  public void createAccountFor(Customer customer)
  {
    Account account = Account.forCustomer(customer);

    accounts.add(customer, account);
  }

  public Amount balanceFor(Customer customer)
  {
    Account account = accounts.findFor(customer);

    return account.balance();
  }

  public void addTransaction(Customer customer, Transaction transaction)
  {
    accounts.addTransaction(customer, transaction);
  }

  public void addTransfer(TransferPair pair, Transfer transfer)
  {
    accounts.addTransfer(pair, transfer);
  }

  public Account accountFor(Customer customer)
  {
    return accounts.findFor(customer);
  }
}
