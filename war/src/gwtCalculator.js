"use strict";

var Calculator = (function() {

    /**
     * @constructor
     */
    function Calculator() {
    }

    Calculator.prototype.calculateMortgage = function(price, down, interest, term) {
        return gwtCalculateMortgage(price, down, interest, term);
    }

    return Calculator;

})();
