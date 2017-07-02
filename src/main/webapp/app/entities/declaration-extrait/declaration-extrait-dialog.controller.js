(function() {
    'use strict';

    angular
        .module('etatcivilApp')
        .controller('DeclarationExtraitDialogController', DeclarationExtraitDialogController);

    DeclarationExtraitDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'DeclarationExtrait', 'Ville', 'Pays'];

    function DeclarationExtraitDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, DeclarationExtrait, Ville, Pays) {
        var vm = this;

        vm.declarationExtrait = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.villes = Ville.query();
        vm.pays = Pays.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.declarationExtrait.id !== null) {
                DeclarationExtrait.update(vm.declarationExtrait, onSaveSuccess, onSaveError);
            } else {
                DeclarationExtrait.save(vm.declarationExtrait, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('etatcivilApp:declarationExtraitUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
            $state.go('declaration-naissance-affichagePdf');
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setCopieLiterale = function ($file, declarationExtrait) {
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        declarationExtrait.copieLiterale = base64Data;
                        declarationExtrait.copieLiteraleContentType = $file.type;
                    });
                });
            }
        };

        vm.setCopieCarte = function ($file, declarationExtrait) {
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        declarationExtrait.copieCarte = base64Data;
                        declarationExtrait.copieCarteContentType = $file.type;
                    });
                });
            }
        };
        vm.datePickerOpenStatus.dateNaissanceEnfant = false;
        vm.datePickerOpenStatus.dateNaissanceMere = false;
        vm.datePickerOpenStatus.dateNaissancePere = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
