(function() {
    'use strict';
    angular
        .module('etatCivilApp')
        .factory('DeclarationPrint', DeclarationPrint);

    DeclarationPrint.$inject = ['$resource', 'DateUtils'];

    function DeclarationPrint ($resource, DateUtils) {
        var resourceUrl =  'api/declaration-extraits-print';

        return $resource(resourceUrl, {}, {
            'print': {
                method: 'POST',
                transformRequest: function (data) {
                    return angular.toJson(data);
                },
                transformResponse: function (data) {
                	 if (data) {
                         data = angular.fromJson(data);
                     }

                	 return data;
                },
                isArray: false
            }

        });


    }

})();
