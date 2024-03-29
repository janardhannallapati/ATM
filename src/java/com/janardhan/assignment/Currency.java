package com.janardhan.assignment;

public enum Currency {

    BILL_NEGATIVE(-1), BILL_ZERO(0), BILL_ONE(1), BILL_FIVE(5), BILL_TEN(10), BILL_TWENTY(20);
    private Integer denomination;


    private Currency(int value) {
        this.denomination = value;
    }

    public Integer getDenomination() {
        return denomination;
    }
}
