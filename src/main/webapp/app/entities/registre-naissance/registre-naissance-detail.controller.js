(function() {
    'use strict';

    angular
        .module('etatCivilApp')
        .controller('RegistreNaissanceDetailController', RegistreNaissanceDetailController);

    RegistreNaissanceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'RegistreNaissance', 'Extrait'];

    function RegistreNaissanceDetailController($scope, $rootScope, $stateParams, previousState, entity, RegistreNaissance, Extrait) {
        var vm = this;

        vm.registreNaissance = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('etatCivilApp:registreNaissanceUpdate', function(event, result) {
            vm.registreNaissance = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
