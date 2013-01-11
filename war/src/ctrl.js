"use strict";

function MortgageCtrl($scope , calculator) {

    $scope.calc = function(price, down, interest, term) {
        $scope.mortgage = calculator.calculateMortgage(price, down, interest, term);
    };
}

/*
REST Version
 */
/*
function MortgageCtrl($scope, mortgageResource) {

    $scope.calc = function(price, down, interest, term) {
        mortgageResource.get({price: price, down: down, interest: interest, term: term}, function(result) {
            $scope.mortgage = result;
        });
    };

}
*/