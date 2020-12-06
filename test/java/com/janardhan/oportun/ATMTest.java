package com.janardhan.oportun;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

class ATMTest {

    @Test
    void depositOneNegativeAmtGivesErrorMessage() {
        Customer customer = new Customer( 1L,ATM.getInstance());
        Map<Currency,Integer> notes = new HashMap<>();
        notes.put(Currency.BILL_NEGATIVE,10);
        assertThat(customer.deposit(notes),is("Incorrect deposit amount"));
    }
    @Test
    void depositMultipleNegativeAmtsGivesErrorMessage() {
        Customer customer = new Customer( 1L, ATM.getInstance());
        Map<Currency,Integer> notes = new HashMap<>();
        notes.put(Currency.BILL_TEN,10);
        notes.put(Currency.BILL_NEGATIVE,10);
        notes.put(Currency.BILL_NEGATIVE,100);
        assertThat(customer.deposit(notes),is("Incorrect deposit amount"));
    }
    @Test
    void depositAllZeroAmtsGivesErrorMessage(){
        Customer customer = new Customer( 1L, ATM.getInstance());
        Map<Currency,Integer> notes = new HashMap<>();
        notes.put(Currency.BILL_ZERO,10);
        notes.put(Currency.BILL_ZERO,10);
        assertThat(customer.deposit(notes),is("Deposit amount cannot be zero"));
    }

    @Test
    void depositOneZeroAmtAndOneNonZeroGivesNoErrorMessage(){
        Customer customer = new Customer( 1L, ATM.getInstance());
        Map<Currency,Integer> notes = new HashMap<>();
        notes.put(Currency.BILL_ZERO,10);
        notes.put(Currency.BILL_TEN,10);
        assertThat(customer.deposit(notes),not("Deposit amount cannot be zero"));
    }
    @Test
    void depositOnlyOneZeroAmtGivesErrorMessage(){
        Customer customer = new Customer( 1L, ATM.getInstance());
        Map<Currency,Integer> notes = new HashMap<>();
        notes.put(Currency.BILL_ZERO,10);
        assertThat(customer.deposit(notes),is("Deposit amount cannot be zero"));
    }

    @Test
    void depositValidNotesIncrementsCustomerBalance(){
        Customer customer = new Customer( 1L, ATM.getInstance());
        Map<Currency,Integer> notes = new HashMap<>();
        notes.put(Currency.BILL_TEN,10);
        notes.put(Currency.BILL_ONE,10);
        customer.deposit(notes);
        assertThat(customer.balance(),is(110L));

    }
    @Test
    void depositValidNotesIncrementsATMBalance(){
        ATM atm = ATM.getInstance();
        atm.setBalance(0L);
        Customer customer = new Customer( 1L,atm);
        Map<Currency,Integer> notes = new HashMap<>();
        notes.put(Currency.BILL_TEN,10);
        notes.put(Currency.BILL_ONE,10);
        customer.deposit(notes);
        assertThat(atm.getBalance(),is(110L));
    }
    @Test
     void depositValidNotesIncrementBillNotes(){
        ATM atm = ATM.getInstance();
        atm.setBalance(0L);
        Customer customer = new Customer( 1L,atm);
        Map<Currency,Integer> notes = new HashMap<>();
        notes.put(Currency.BILL_TEN,10);
        notes.put(Currency.BILL_ONE,10);
        customer.deposit(notes);
        assertThat(atm.getCurrencyQty(Currency.BILL_TEN),is(10L));
        assertThat(atm.getCurrencyQty(Currency.BILL_ONE),is(10L));
    }
    /**
     Deposit: Customer inputs the number of currency notes in each denomination

     D.1) If any input values are negative, print "Incorrect deposit amount".
     D.2) If all the input values are zero, print "Deposit amount cannot be zero".
     D.3) If the input values are valid, increment the balances of corresponding dollar
     bills and print the available new balances in each denomination and the total balance.

     Withdraw: Customer input the amount to withdraw. ATM dispenses the 20, 10, 5, and 1 dollar bills as needed.

     W.1) If the input amount is zero, negative, or over the current balance, print "Incorrect or insufficient funds".
     W.2) If the input amount is in valid range, print the number of current notes dispensed in each denomination.
     Use the available higher denomination first. Also, print the available new balances in each denomination and the total balance.
     W.3) If any denomination is not available to cover the withdrawal amount, do not reduce the balances.
     Instead, print "Requested withdraw amount is not dispensable". See Withdraw 3 scenario below.

     * Deposit 1: 10s: 8, 5s: 20
     * ---------------------------------
     *
     * Balance: 20s=0, 10s=8, 5s=20, 1s=0, Total=180
     *
     * Deposit 2: 20s: 3, 5s: 18, 1s: 4
     * -----------------------------------------
     *
     * Balance: 20s=3, 10s=8, 5s=38, 1s=4, Total=334
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
