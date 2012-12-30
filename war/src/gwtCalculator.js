"use strict";

var Calculator = (function() {

    /**
     * @constructor
     */
    function Calculator() {
    }

    Calculator.prototype.calculateMortgage = function(price, down, interest, term) {
        var result = gwtCalculateMortgage(price, down, interest, term);
        return JSON.parse(result);
    }

    return Calculator;

})();
