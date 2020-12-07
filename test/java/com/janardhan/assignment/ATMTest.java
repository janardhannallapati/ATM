package com.janardhan.assignment;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

class ATMTest {

    @Test
    void depositOneNegativeAmtGivesErrorMessage() {
        Customer customer = new Customer( 1L,ATM.getInstance());
        Map<Currency,Long> notes = new HashMap<>();
        notes.put(Currency.BILL_NEGATIVE,10L);
        assertThat(customer.deposit(notes),is("Incorrect deposit amount"));
    }
    @Test
    void depositMultipleNegativeAmtsGivesErrorMessage() {
        Customer customer = new Customer( 1L, ATM.getInstance());
        Map<Currency,Long> notes = new HashMap<>();
        notes.put(Currency.BILL_TEN,10L);
        notes.put(Currency.BILL_NEGATIVE,10L);
        notes.put(Currency.BILL_NEGATIVE,100L);
        assertThat(customer.deposit(notes),is("Incorrect deposit amount"));
    }
    @Test
    void depositAllZeroAmtsGivesErrorMessage(){
        Customer customer = new Customer( 1L, ATM.getInstance());
        Map<Currency,Long> notes = new HashMap<>();
        notes.put(Currency.BILL_ZERO,10L);
        notes.put(Currency.BILL_ZERO,10L);
        assertThat(customer.deposit(notes),is("Deposit amount cannot be zero"));
    }

    @Test
    void depositOneZeroAmtAndOneNonZeroGivesNoErrorMessage(){
        Customer customer = new Customer( 1L, ATM.getInstance());
        Map<Currency,Long> notes = new HashMap<>();
        notes.put(Currency.BILL_ZERO,10L);
        notes.put(Currency.BILL_TEN,10L);
        assertThat(customer.deposit(notes),not("Deposit amount cannot be zero"));
    }
    @Test
    void depositOnlyOneZeroAmtGivesErrorMessage(){
        Customer customer = new Customer( 1L, ATM.getInstance());
        Map<Currency,Long> notes = new HashMap<>();
        notes.put(Currency.BILL_ZERO,10L);
        assertThat(customer.deposit(notes),is("Deposit amount cannot be zero"));
    }

    @Test
    void depositValidNotesIncrementsCustomerBalance(){
        Customer customer = new Customer( 1L, ATM.getInstance());
        Map<Currency,Long> notes = new HashMap<>();
        notes.put(Currency.BILL_TEN,10L);
        notes.put(Currency.BILL_ONE,10L);
        customer.deposit(notes);
        assertThat(customer.balance(),is(110L));

    }
    @Test
    void depositValidNotesIncrementsATMBalance(){
        ATM atm = ATM.getInstance();
        atm.resetBalance();
        Customer customer = new Customer( 1L,atm);
        Map<Currency,Long> notes = new HashMap<>();
        notes.put(Currency.BILL_TEN,10L);
        notes.put(Currency.BILL_ONE,10L);
        System.out.println(customer.deposit(notes));
        System.out.println(atm);
        assertThat(atm.getBalance(),is(110L));
    }
    @Test
     void depositValidNotesIncrementBillNotes(){
        ATM atm = ATM.getInstance();
        atm.resetBalance();
        Customer customer = new Customer( 1L,atm);
        Map<Currency,Long> notes = new HashMap<>();
        notes.put(Currency.BILL_TEN,8L);
        notes.put(Currency.BILL_FIVE,20L);
        System.out.println(customer.deposit(notes));
        System.out.println(atm);
        assertThat(atm.getBalance(),is(180L));
        assertThat(atm.getCurrencyQty(Currency.BILL_TEN),is(8L));
        assertThat(atm.getCurrencyQty(Currency.BILL_FIVE),is(20L));
        notes = new HashMap<>();
        notes.put(Currency.BILL_ONE,4L);
        notes.put(Currency.BILL_FIVE,18L);
        notes.put(Currency.BILL_TWENTY,3L);
        System.out.println(customer.deposit(notes));
        System.out.println(atm);
        assertThat(atm.getCurrencyQty(Currency.BILL_TEN),is(8L));
        assertThat(atm.getCurrencyQty(Currency.BILL_FIVE),is(38L));
        assertThat(atm.getCurrencyQty(Currency.BILL_TWENTY),is(3L));
        assertThat(atm.getCurrencyQty(Currency.BILL_ONE),is(4L));
        assertThat(atm.getBalance(),is(334L));

    }

    @Test
    public void withdrawZeroAmtGivesErrorMessage(){
        ATM atm = ATM.getInstance();
        Customer customer = new Customer( 1L,atm);
        assertThat(customer.withdraw(0L),is("Incorrect or insufficient funds"));
    }

    @Test
    public void withdrawNegativeAmtGivesErrorMessage(){
        ATM atm = ATM.getInstance();
        Customer customer = new Customer( 1L,atm);
        assertThat(customer.withdraw(-1L),is("Incorrect or insufficient funds"));
    }


    @Test
    public void withdrawAboveCurrentBalanceGivesErrorMessage(){
        ATM atm = ATM.getInstance();
        Customer customer = new Customer( 1L,atm);
        Map<Currency,Long> notes = new HashMap<>();
        notes.put(Currency.BILL_TEN,8L);
        notes.put(Currency.BILL_FIVE,20L);
        customer.deposit(notes);
        assertThat(customer.withdraw(181L),is("Incorrect or insufficient funds"));
        customer.withdraw(180L);
        assertThat(customer.balance(),is(0L));
    }

    @Test
    public void withdrawAmountMatchCustomerBalance(){
        ATM atm = ATM.getInstance();
        Customer customer = new Customer( 1L,atm);
        Map<Currency,Long> notes = new HashMap<>();
        notes.put(Currency.BILL_TEN,8L);
        notes.put(Currency.BILL_FIVE,20L);
        customer.deposit(notes);
        customer.withdraw(180L);
        assertThat(customer.balance(),is(0L));
    }

    @Test
    public void withdrawAmountMatchATMCurrencyBalance(){
        ATM atm = ATM.getInstance();
        atm.resetBalance();
        Customer customer = new Customer( 1L,atm);
        Map<Currency,Long> notes = new HashMap<>();
        notes.put(Currency.BILL_TEN,8L);
        notes.put(Currency.BILL_FIVE,20L);
        customer.deposit(notes);
        customer.withdraw(120L);
        assertThat(atm.getCurrencyQty(Currency.BILL_TEN),is(0L));
        assertThat(atm.getCurrencyQty(Currency.BILL_FIVE),is(12L));
    }

    /**

     Withdraw: Customer input the amount to withdraw. ATM dispenses the 20, 10, 5, and 1 dollar bills as needed.

     W.1) If the input amount is zero, negative, or over the current balance, print "Incorrect or insufficient funds".
     W.2) If the input amount is in valid range, print the number of current notes dispensed in each denomination.
     Use the available higher denomination first. Also, print the available new balances in each denomination and the total balance.
     W.3) If any denomination is not available to cover the withdrawal amount, do not reduce the balances.
     Instead, print "Requested withdraw amount is not dispensable". See Withdraw 3 scenario below.

     *
     * Withdraw 1: 75
     * ---------------------
     *
     * Dispensed: 20s=3, 10s=1, 5s=1
     * Balance: 20s=0, 10s=7, 5s=37, 1s=4, Total=259
     *
     * Withdraw 2: 122
     * ----------------------
     *
     * Dispensed: 10s=7, 5s=10, 1s=2
     * Balance: 20s=0, 10s=0, 5s=27, 1s=2, Total=137
     *
     * Withdraw 3: 63
     * ----------------------
     *
     * Output: "Requested withdraw amount is not dispensable"
     *
     * Note: At this stage, the ATM has only two 1 dollar bills. So, the withdrawal amount cannot be dispensed.
     *
     * Withdraw 3: 253
     * ----------------------
     *
     * Output: "Incorrect or insufficient funds"
     *
     * Withdraw 4: 0
     * -------------------
     *
     * Output: "Incorrect or insufficient funds"
     *
     * Withdraw 5: -25
     * ----------------------
     *
     * Output: "Incorrect or insufficient funds"
     */



}
