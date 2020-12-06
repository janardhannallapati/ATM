package com.janardhan.oportun;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ATM {
    private Long balance = 0L;
    private static ATM instance;
    private Map<Currency, Long> currencyDetails;

    private ATM(){
        currencyDetails = new HashMap<>();
        currencyDetails.put(Currency.BILL_ZERO, 0L);
        currencyDetails.put(Currency.BILL_ONE, 0L);
        currencyDetails.put(Currency.BILL_FIVE, 0L);
        currencyDetails.put(Currency.BILL_TEN, 0L);
        currencyDetails.put(Currency.BILL_TWENTY, 0L);

    }
    public static ATM getInstance(){
       if(instance == null)
       {
           instance = new ATM();
       }

       return instance;
    }

    public Long getBalance(){
        return balance;
    }
    public void setBalance(Long amount){
        this.balance = amount;
    }

    public void incrementBalance(Long amount){
        this.balance += amount;
    }

    public void decrementBalance(Long amount){
        this.balance -= amount;
    }

    public Map<Currency, Long> getCurrencyDetails() {
        return currencyDetails;
    }

    public void setCurrencyDetails(Map<Currency, Long> currencyDetails) {
        this.currencyDetails = currencyDetails;
    }

    public void incrementCurrency(Currency currency, Long qty){
        long existingQty = currencyDetails.get(currency);
        incrementBalance(currency.getDenomination()*qty);
        currencyDetails.put(currency, existingQty+qty);
    }

    public void decrementCurrency(Currency currency, Long qty){

        long existingQty = currencyDetails.get(currency);
        decrementBalance(currency.getDenomination()*qty);
        currencyDetails.put(currency, qty-existingQty);
    }

    public Long getCurrencyQty(Currency currency){
//       currencyDetails.entrySet().stream().filter(entry -> entry.getKey() == currency);
        return 0L;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ATM atm = (ATM) o;
        return balance.equals(atm.balance) &&
                currencyDetails.equals(atm.currencyDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(balance, currencyDetails);
    }
}
