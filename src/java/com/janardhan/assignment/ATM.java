package com.janardhan.assignment;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ATM {
    private Long balance = 0L;
    private static volatile ATM instance;
    private Map<Currency, Long> currencyDetails;

    private ATM(){
        initializeCurrency();

    }

    private void initializeCurrency() {
        currencyDetails = new ConcurrentHashMap<>();
        currencyDetails.put(Currency.BILL_ZERO, 0L);
        currencyDetails.put(Currency.BILL_ONE, 0L);
        currencyDetails.put(Currency.BILL_FIVE, 0L);
        currencyDetails.put(Currency.BILL_TEN, 0L);
        currencyDetails.put(Currency.BILL_TWENTY, 0L);
    }

    public static ATM getInstance(){
       if(instance == null)
       {
           synchronized(ATM.class)  {
               if(instance == null){
                   instance = new ATM();
               }
           }
       }

       return instance;
    }

    public Long getBalance(){
        return balance;
    }
    public void resetBalance(){
        this.balance = 0L;
        initializeCurrency();
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
       Map.Entry<Currency, Long> result = currencyDetails.entrySet().stream().filter(entry -> entry.getKey() == currency).findFirst().get();
        return result.getValue();
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

    @Override
    public String toString() {
        StringBuffer stringBuffer=new StringBuffer("");
        currencyDetails.entrySet().stream().forEach(entry-> stringBuffer.append(entry.getKey().getDenomination()+"s"+"="+entry.getValue()+", "));
        return "ATM {" +

                "Balance : " +  stringBuffer.toString()+
                "Total =" + balance +'}';
    }

    public boolean isAmtDispensable(long amount){
        //get list of currencies with quantities greater than zero and in descending order.
        List<Map.Entry<Currency, Long>> list= currencyDetails.entrySet().stream().filter(entry-> entry.getValue()>0).sorted((entry1, entry2)->entry2.getKey().getDenomination().compareTo(entry1.getKey().getDenomination())).collect(Collectors.toList());

        for(Map.Entry<Currency, Long> entry:list){
            Integer denomination = entry.getKey().getDenomination();
            Long quantity = entry.getValue();
            Long reqdQty = amount/denomination ;
            if(quantity<=reqdQty){
                amount = amount - quantity * denomination;
            }
            else{
               amount = quantity*denomination - amount;
            }

        }
        return true;
    }
    public void dispense(long amount) {
        //Dispensed: 10s=7, 5s=10, 1s=2
       if(isAmtDispensable(amount)){
          return;
       }

       return;
    }
}
