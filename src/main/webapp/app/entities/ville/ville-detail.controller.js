(function() {
    'use strict';

    angular
        .module('etatCivilApp')
        .controller('VilleDetailController', VilleDetailController);

    VilleDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Ville', 'Pays'];

    function VilleDetailController($scope, $rootScope, $stateParams, previousState, entity, Ville, Pays) {
        var vm = this;

        vm.ville = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('etatCivilApp:villeUpdate', function(event, result) {
            vm.ville = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
