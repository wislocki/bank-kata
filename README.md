The Bank Kata
=============

This is an implementation of the "bank kata" exercise in which you are tasked with implementing a simple banking program. The program should support the following operations:


* Deposit and withdrawal
* Transfer
* Account statement (date, amount, balance)
* Statement printing
* Statement filters (just deposits, withdrawal, date)

What makes it interesting are the following rules:

1. One level of indentation per method
2. Don’t use the ELSE keyword
3. Wrap all primitives and Strings
4. First class collections
5. One dot per line
6. Don’t abbreviate
7. Keep all entities small (50 lines)
8. No classes with more than two instance variables
9. No getters/setters/properties

You can find a more detailed explanation of the rules see http://www.bennadel.com/resources/uploads/2012/ObjectCalisthenics.pdf

What I Did
----------

My own implementation exceeds 50 lines for some classes, though not by much. 

When I first started workng, I most operations were happening through a `Bank` class, but this quickly began to feel awkward. As I was writing tests, I found myself writing code like 

    bank.deposit(customer, amount, date);

which felt clunky. First retrieving an account and then operating on it, though it read better, felt worse: 

    Account account = bank.accountFor(customer); 
    account.deposit(amount, date);
    
It didn't seem like the API should require the user to orchestrate interactions between objects. It felt like it should first of all be more structured, and second of all, proceed from the customer. I decided to experiment with a simple DSL instead, and I like how it came out. The result is 

    customer.deposit(amount).at(date).at(bank);
    
I also relied heavily on static factory methods to extend the DSL to the creation of objects. Thus instead of 

    customer = new Customer("Lou Costello");
    amount = new Amount("2.50");

I have

    customer = Customer.named("Lou Costello");
    amount = Amount.of("2.50");
    
The DSL is implemented using a string of helper objects that package their method arguments and pass them to the next helper. This actually seemed to work well with the "two field" constraint, with each helper composing a new object by adding a new field as they are passed along.

What I'd Like to Change
-----------------------

I'm not really satisfied with how printing / formatting is handled. The way printing is handled in `Transactions` feels extremely awkward to me, especially around handling new lines in `print()`. It seems that formatting should be a separate concern, not contained within the domain classes themselves. I also don't have anything to handle the printing of date ranges, something that I never got to.

Impressions of the Kata
-----------------------

Overall I thought this was a great exercise. I probably spent more time thinking and working on it than the average person would have, but I enjoyed the extra time spent. It forced me to think in ways that felt awkward and uncomfortable at first, but these ways ended up feeling more natural -- more right -- in the end than the ways I had thought about coding previously.
