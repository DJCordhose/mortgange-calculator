"use strict";

var CalculatorModule = angular.module('CalculatorModule', []).
    factory('Calculator', function() {
        return new Calculator();
    });

var MortgageApp = angular.module('MortgageApp', ['CalculatorModule']);

MortgageApp.directive('tooltip', function () {
    return function (scope, element, attrs) {
        element.tooltip({
            placement: attrs.tooltipPlacement || "top",
            animation: "true",
            title: attrs.tooltip,
            delay: { show: 500, hide: 100 }
        });
    };
});

function MortgageCtrl($scope, Calculator) {

    $scope.calc = function(price, down, interest, term) {
        $scope.mortgage = Calculator.calculateMortgage(price, down, interest, term);
    };

}