(function() {
    'use strict';

    angular
        .module('etatCivilApp')
        .controller('ExtraitDialogController', ExtraitDialogController);

    ExtraitDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Extrait', 'Ville', 'Personne', 'User', 'PieceJointe'];

    function ExtraitDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Extrait, Ville, Personne, User, PieceJointe) {
        var vm = this;

        vm.extrait = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.villes = Ville.query();
        vm.personnes = Personne.query();
        vm.users = User.query();
        vm.piecejointes = PieceJointe.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.extrait.id !== null) {
                Extrait.update(vm.extrait, onSaveSuccess, onSaveError);
            } else {
                Extrait.save(vm.extrait, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('etatCivilApp:extraitUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dateDeclaration = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
