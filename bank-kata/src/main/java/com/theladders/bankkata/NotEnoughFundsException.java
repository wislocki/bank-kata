package com.theladders.bankkata;

public class NotEnoughFundsException extends RuntimeException
{
  public NotEnoughFundsException(Amount availableAmount, Amount withdrawalAmount)
  {
    super("Available amount of " + availableAmount + " is less than " + withdrawalAmount);
  }
}
