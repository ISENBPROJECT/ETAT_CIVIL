(function() {
    'use strict';

    angular
        .module('etatCivilApp')
        .controller('PieceJointeDialogController', PieceJointeDialogController);

    PieceJointeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'PieceJointe', 'Extrait'];

    function PieceJointeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, PieceJointe, Extrait) {
        var vm = this;

        vm.pieceJointe = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
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
            if (vm.pieceJointe.id !== null) {
                PieceJointe.update(vm.pieceJointe, onSaveSuccess, onSaveError);
            } else {
                PieceJointe.save(vm.pieceJointe, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('etatCivilApp:pieceJointeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setCopieLiterale = function ($file, pieceJointe) {
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        pieceJointe.copieLiterale = base64Data;
                        pieceJointe.copieLiteraleContentType = $file.type;
                    });
                });
            }
        };

        vm.setCopieCarte = function ($file, pieceJointe) {
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        pieceJointe.copieCarte = base64Data;
                        pieceJointe.copieCarteContentType = $file.type;
                    });
                });
            }
        };

    }
})();
