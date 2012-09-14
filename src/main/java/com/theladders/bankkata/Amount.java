package com.theladders.bankkata;

import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;

public class Amount implements Comparable<Amount>
{
  public static final Amount ZERO = new Amount(0);

  private final int totalCents;

  public static Amount of(String amount)
  {
    return new Amount(stringToCents(amount));
  }

  private static int stringToCents(String amount)
  {
    BigDecimal dollarsAndCents = new BigDecimal(amount).setScale(2);
    BigInteger totalCents = dollarsAndCents.unscaledValue();

    return totalCents.intValue();
  }

  private Amount(int cents)
  {
    this.totalCents = cents;
  }

  public Amount plus(Amount amount)
  {
    return new Amount(totalCents + amount.totalCents);
  }

  public Amount minus(Amount amount)
  {
    return  new Amount(totalCents - amount.totalCents);
  }

  private int dollars()
  {
    return totalCents / 100;
  }

  private int centsPart()
  {
    return Math.abs(totalCents % 100);
  }

  @Override
  public String toString()
  {
    return String.format("$%d.%02d", dollars(), centsPart());
  }

  @Override
  public boolean equals(Object o)
  {
    return (o instanceof Amount) && totalCents == ((Amount)o).totalCents;
  }

  @Override
  public int hashCode()
  {
    return 31 * totalCents;
  }

  @Override
  public int compareTo(Amount amount)
  {
    return totalCents - amount.totalCents;
  }

  public boolean lessThan(Amount amount)
  {
    return compareTo(amount) < 0;
  }

  public void printTo(Writer writer) throws IOException
  {
    writer.append(toString());
  }
}
