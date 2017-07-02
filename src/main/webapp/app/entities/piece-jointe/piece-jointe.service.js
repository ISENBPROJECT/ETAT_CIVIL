(function() {
    'use strict';
    angular
        .module('etatCivilApp')
        .factory('PieceJointe', PieceJointe);

    PieceJointe.$inject = ['$resource'];

    function PieceJointe ($resource) {
        var resourceUrl =  'api/piece-jointes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
