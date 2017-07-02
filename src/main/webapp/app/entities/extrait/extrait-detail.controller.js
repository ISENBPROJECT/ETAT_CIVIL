(function() {
    'use strict';

    angular
        .module('etatCivilApp')
        .controller('ExtraitDetailController', ExtraitDetailController);

    ExtraitDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Extrait', 'Ville', 'Personne', 'User', 'PieceJointe'];

    function ExtraitDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Extrait, Ville, Personne, User, PieceJointe) {
        var vm = this;

        vm.extrait = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('etatCivilApp:extraitUpdate', function(event, result) {
            vm.extrait = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
