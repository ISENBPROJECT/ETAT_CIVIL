(function() {
    'use strict';

    angular
        .module('etatCivilApp')
        .controller('CommuneDialogController', CommuneDialogController);

    CommuneDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Commune', 'Ville'];

    function CommuneDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Commune, Ville) {
        var vm = this;

        vm.commune = entity;
        vm.clear = clear;
        vm.save = save;
        vm.villes = Ville.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.commune.id !== null) {
                Commune.update(vm.commune, onSaveSuccess, onSaveError);
            } else {
                Commune.save(vm.commune, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('etatCivilApp:communeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
