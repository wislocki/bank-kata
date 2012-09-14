package com.theladders.bankkata;

public class CustomerNotFoundException extends RuntimeException
{
  public CustomerNotFoundException(Customer customer)
  {
    super("Customer " + customer + " not found.");
  }
}
