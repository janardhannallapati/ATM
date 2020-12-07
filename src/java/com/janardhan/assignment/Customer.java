package com.janardhan.assignment;

import java.util.Map;

public class Customer {
    private Long balanceAmt=0L;
    private ATM atm;
    private Long accountNum;

    public Customer(Long accountNum, ATM atm) {
        this.atm = atm;
        this.accountNum = accountNum;
    }
    public Customer(Long accountNum, Long balanceAmt, ATM atm) {
        this.balanceAmt = balanceAmt;
        this.atm = atm;
        this.accountNum = accountNum;
    }

    public Long getAccountNum() {
        return accountNum;
    }


    public String deposit(Map<Currency,Long> notes) {
        long negativeAmtCnt = notes.keySet().stream().filter(note->note.getDenomination() < 0).count();
        if(negativeAmtCnt>0) {
            return "Incorrect deposit amount";
        }
        long zeroAmt = notes.keySet().stream().filter(note->note.getDenomination() == 0).count();
        int numberOfNotes = notes.size();
        if(numberOfNotes == zeroAmt){
            return "Deposit amount cannot be zero";
        }
        else {
            StringBuffer result = new StringBuffer("Deposit: ");
            final Long[] totalDepositedAmt = {0L};

            notes.entrySet().stream().forEach(entry-> {
                totalDepositedAmt[0] += entry.getKey().getDenomination() * entry.getValue();
                result.append(entry.getKey().getDenomination()+"s"+":"+" "+entry.getValue()+", ");
            }
            );
            result.append("\n---------------------------------");
            balanceAmt+= totalDepositedAmt[0];
            notes.entrySet().stream().forEach(entry-> atm.incrementCurrency(entry.getKey(),entry.getValue()));

            return result.toString();
        }

    }

    public Long balance() {
        return balanceAmt;
    }

    public String withdraw(long amount) {
        if(amount<=0 || amount > balanceAmt){
            return "Incorrect or insufficient funds";
        }
        atm.dispense(amount);
        balanceAmt-=amount;
        return "";
    }
}
