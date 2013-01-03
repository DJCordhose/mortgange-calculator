angular.module('restclient', ['ngResource']).
    factory('RestCalculator', function($resource) {
        var RestCalculator = $resource('/mortgage_calculator/rs/:price/:down/:interest/:term');
        return RestCalculator;
    });