(function() {
    'use strict';

    angular
        .module('etatCivilApp')
        .controller('RegistreNaissanceDialogController', RegistreNaissanceDialogController);

    RegistreNaissanceDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'RegistreNaissance', 'Extrait'];

    function RegistreNaissanceDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, RegistreNaissance, Extrait) {
        var vm = this;

        vm.registreNaissance = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.extraits = Extrait.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.registreNaissance.id !== null) {
                RegistreNaissance.update(vm.registreNaissance, onSaveSuccess, onSaveError);
            } else {
                RegistreNaissance.save(vm.registreNaissance, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('etatCivilApp:registreNaissanceUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.anneeRegistre = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
