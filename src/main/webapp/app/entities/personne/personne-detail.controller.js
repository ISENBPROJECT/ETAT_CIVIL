(function() {
    'use strict';

    angular
        .module('etatCivilApp')
        .controller('PersonneDetailController', PersonneDetailController);

    PersonneDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Personne', 'Ville'];

    function PersonneDetailController($scope, $rootScope, $stateParams, previousState, entity, Personne, Ville) {
        var vm = this;

        vm.personne = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('etatCivilApp:personneUpdate', function(event, result) {
            vm.personne = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
