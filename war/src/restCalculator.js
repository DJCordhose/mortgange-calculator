angular.module('mortgageResourceModule', ['ngResource']).
    factory('mortgageResource', function($resource) {
        var mortgageResource = $resource('/mortgage_calculator/rs/:price/:down/:interest/:term');
        return mortgageResource;
    });