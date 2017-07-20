(function() {
    'use strict';

    angular
        .module('etatCivilApp')
        .controller('CommuneDetailController', CommuneDetailController);

    CommuneDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Commune', 'Ville'];

    function CommuneDetailController($scope, $rootScope, $stateParams, previousState, entity, Commune, Ville) {
        var vm = this;

        vm.commune = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('etatCivilApp:communeUpdate', function(event, result) {
            vm.commune = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
