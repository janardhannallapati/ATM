package com.janardhan.oportun;

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


    public String deposit(Map<Currency,Integer> notes) {
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
            final Long[] totalDepositedAmt = {0L};
            notes.entrySet().stream().forEach(entry-> totalDepositedAmt[0] += entry.getKey().getDenomination() * entry.getValue());
            balanceAmt+= totalDepositedAmt[0];
            atm.incrementBalance(balanceAmt);
            return "";
        }

    }

    public Long balance() {
        return balanceAmt;
    }
}
