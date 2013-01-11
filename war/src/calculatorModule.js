"use strict";

var CalculatorModule = angular.module('calculatorModule', []).
    factory('calculator', function() {
        return new Calculator();
    });
