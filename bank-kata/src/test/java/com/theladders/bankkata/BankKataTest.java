package com.theladders.bankkata;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BankKataTest
{
  private static Random       random;

  private Customer            abbott, costello;
  private Bank                bank;
  private Amount              startingBalance;


  @BeforeClass
  public static void setUpRandom()
  {
    random = new Random();
  }


  @Before
  public void setUpAccounts()
  {
    bank = Bank.named("First National Savings");

    abbott = Customer.named("Bud Abbott");
    costello = Customer.named("Lou Costello");

    abbott.createAccountAt(bank);
    costello.createAccountAt(bank);

    startingBalance = Amount.of("1000.00");
  }


  @Test
  public void initialAmountInAccountShouldBeZero()
  {
    assertEquals(Amount.ZERO, abbott.balanceAt(bank));
  }


  @Test
  public void bankShouldAcceptDeposits()
  {
    Amount amount = randomAmount();

    abbott.deposit(amount).now().at(bank);

    assertEquals(amount, abbott.balanceAt(bank));
  }


  @Test
  public void bankShouldAcceptDepositsForDifferentCustomers()
  {
    Amount amount1 = randomAmount();
    Amount amount2 = randomAmount();

    abbott.deposit(amount1).now().at(bank);
    costello.deposit(amount2).now().at(bank);

    assertEquals(amount1, abbott.balanceAt(bank));
    assertEquals(amount2, costello.balanceAt(bank));
  }


  @Test
  public void bankShouldAllowMultipleDeposits()
  {
    Amount total = Amount.ZERO;

    for (Amount amount : randomAmounts())
    {
      abbott.deposit(amount).now().at(bank);
      total = total.plus(amount);
    }

    assertEquals(total, abbott.balanceAt(bank));
  }


  @Test
  public void bankShouldAcceptWithdrawals()
  {
    Amount amount = randomAmount();

    costello.deposit(startingBalance).now().at(bank);
    costello.withdraw(amount).now().from(bank);

    assertEquals(startingBalance.minus(amount), costello.balanceAt(bank));
  }


  @Test
  public void bankShouldAcceptWithdrawalsForDifferentCustomers()
  {
    Amount amount1 = randomAmount();
    Amount amount2 = randomAmount();

    abbott.deposit(startingBalance).now().at(bank);
    costello.deposit(startingBalance).now().at(bank);

    abbott.withdraw(amount1).now().at(bank);
    costello.withdraw(amount2).now().at(bank);

    assertEquals(startingBalance.minus(amount1), abbott.balanceAt(bank));
    assertEquals(startingBalance.minus(amount2), costello.balanceAt(bank));
  }


  @Test(expected = NotEnoughFundsException.class)
  public void bankShouldRejectWithdrawalsWithoutEnoughFunds()
  {
    Amount amount = startingBalance.plus(randomAmount());

    costello.deposit(startingBalance).now().at(bank);

    costello.withdraw(amount).now().from(bank);
  }


  @Test
  public void balanaceShouldBeZeroForEqualsAmountsOfDepositsAndWidthdrawals()
  {
    List<Amount> amounts = randomAmounts();

    for (Amount amount : amounts)
      costello.deposit(amount).now().at(bank);

    Collections.shuffle(amounts);

    for (Amount amount : amounts)
      costello.withdraw(amount).now().from(bank);

    assertEquals(Amount.ZERO, costello.balanceAt(bank));
  }


  @Test
  public void bankShouldAllowTransfersBetweenCustomers()
  {
    abbott.deposit(startingBalance).now().at(bank);
    costello.deposit(startingBalance).now().at(bank);

    Amount amount = randomAmountUnder(1000);

    abbott.transfer(amount).to(costello).now().at(bank);

    assertEquals(startingBalance.minus(amount), abbott.balanceAt(bank));
  }


  @Test(expected = NotEnoughFundsException.class)
  public void bankShouldRejectTransfersWithoutEnoughFunds()
  {
    abbott.deposit(startingBalance).now().at(bank);
    costello.deposit(startingBalance).now().at(bank);

    Amount amount = startingBalance.plus(randomAmount());

    abbott.transfer(amount).to(costello).now().at(bank);
  }


  @Test
  public void bankShouldPrintTransactions() throws IOException
  {
    String statement = randomStatementWithFilter(StatementFilter.NONE);

    assertTrue(containsDeposits(statement));
    assertTrue(containsWithdrawals(statement));
    assertTrue(containsTransfers(statement));
  }


  @Test
  public void bankShouldBeAbleToPrintOnlyDeposits() throws IOException
  {
    String statement = randomStatementWithFilter(StatementFilter.DEPOSITS_ONLY);

    assertTrue(containsDeposits(statement));
    assertFalse(containsWithdrawals(statement));
  }


  @Test
  public void bankShouldBeAbleToPrintOnlyWithdrawals() throws IOException
  {
    String statement = randomStatementWithFilter(StatementFilter.WITHDRAWALS_ONLY);

    assertFalse(containsDeposits(statement));
    assertTrue(containsWithdrawals(statement));
  }


  @Test
  public void bankShouldBeAbleToPrintOnlyTransfers() throws IOException
  {
    String statement = randomStatementWithFilter(StatementFilter.TRANSFERS_ONLY);

    assertFalse(containsDeposits(statement));
    assertFalse(containsWithdrawals(statement));
    assertTrue(containsTransfers(statement));
  }


  private String randomStatementWithFilter(StatementFilter filter) throws IOException
  {
    StringWriter writer = new StringWriter();

    writer.append("***************************************************************\n" +
                  filter.toString() + "\n\n");

    Amount amount1 = randomAmount();

    costello.deposit(startingBalance).now().at(bank);
    abbott.deposit(startingBalance).now().at(bank);

    costello.withdraw(amount1).now().from(bank);
    costello.deposit(startingBalance).now().at(bank);

    Amount amount2 = randomAmountUnder(1000);

    abbott.transfer(amount2).to(costello).now().at(bank);

    costello.transfer(amount2).to(abbott).now().at(bank);

    costello.printStatementFrom(bank).withFilter(filter).to(writer);

    String statement = writer.getBuffer().toString();

    System.out.println(statement);

    return statement;
  }


  private static boolean containsDeposits(String statement)
  {
    return matches(statement, "[^:] [$][0-9]+\\.[0-9][0-9][^ ]");
  }


  private static boolean containsWithdrawals(String statement)
  {
    return matches(statement, "[^:] -[$][0-9]+\\.[0-9][0-9][^ ]");
  }


  private static boolean containsTransfers(String statement)
  {
    return matches(statement, "[$][0-9]+\\.[0-9][0-9] \\(T\\)");
  }


  private static boolean matches(String string, String pattern)
  {
    Matcher matcher = Pattern.compile(pattern).matcher(string);

    boolean found = matcher.find();

    return found;
  }


  private static Amount randomAmountUnder(int dollars)
  {
    return Amount.of(random.nextInt(dollars) + "." + random.nextInt(100));
  }


  private static Amount randomAmount()
  {
    return randomAmountUnder(1000);
  }


  private static List<Amount> randomAmounts()
  {
    int number = random.nextInt(25) + 10;

    List<Amount> amounts = new ArrayList<>(number);

    for (int i=0; i<number; i++)
      amounts.add(randomAmount());

    return amounts;
  }
}
