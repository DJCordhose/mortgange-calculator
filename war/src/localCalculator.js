"use strict";

var Calculator = (function() {

    /**
     * @constructor
     */
    function Calculator() {
    }

    // http://de.wikipedia.org/wiki/Annuit%C3%A4tendarlehen
    Calculator.prototype.calculateMortgage = function(price, down, interest, term) {
        var monthlyInterestRate = (interest/100) / 12;
        var principle = price - down;
        var payments = term * 12;
//        var monthly = principle * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, payments)
//            / (Math.pow(1 + monthlyInterestRate, payments) - 1);
        // simplified version
        var monthly = principle * monthlyInterestRate / (1 - Math.pow(1 + monthlyInterestRate,(-1 * payments)));
        var roundedMonthly = Math.round(monthly*100)/100;
        var total = roundedMonthly * payments;
        return {
            principle: principle.toFixed(2),
            total: total.toFixed(2),
            payments: payments.toFixed(0),
            monthly: roundedMonthly.toFixed(2)
        };
    };

    return Calculator;

})();
