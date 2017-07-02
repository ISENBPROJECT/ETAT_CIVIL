(function() {
    'use strict';

    angular
        .module('etatCivilApp')
        .controller('PieceJointeDetailController', PieceJointeDetailController);

    PieceJointeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'PieceJointe', 'Extrait'];

    function PieceJointeDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, PieceJointe, Extrait) {
        var vm = this;

        vm.pieceJointe = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('etatCivilApp:pieceJointeUpdate', function(event, result) {
            vm.pieceJointe = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
