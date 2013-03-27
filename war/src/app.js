"use strict";

var MortgageApp = angular.module('mortgageApp', ['calculatorModule', 'mortgageResourceModule']);

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