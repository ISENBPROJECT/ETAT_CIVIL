(function() {
    'use strict';

    angular
        .module('etatCivilApp')
        .controller('PersonneDialogController', PersonneDialogController);

    PersonneDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Personne', 'Ville'];

    function PersonneDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Personne, Ville) {
        var vm = this;

        vm.personne = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.villes = Ville.query();
        vm.personnes = Personne.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.personne.id !== null) {
                Personne.update(vm.personne, onSaveSuccess, onSaveError);
            } else {
                Personne.save(vm.personne, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('etatCivilApp:personneUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dateNaissance = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
