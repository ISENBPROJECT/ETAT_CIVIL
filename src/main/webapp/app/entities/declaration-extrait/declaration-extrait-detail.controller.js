(function() {
    'use strict';

    angular
        .module('etatCivilApp')
        .controller('DeclarationExtraitDetailController', DeclarationExtraitDetailController);

    DeclarationExtraitDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'DeclarationExtrait', 'Ville', 'Pays'];

    function DeclarationExtraitDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, DeclarationExtrait, Ville, Pays) {
        var vm = this;

        vm.declarationExtrait = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('etatCivilApp:declarationExtraitUpdate', function(event, result) {
            vm.declarationExtrait = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();