"use strict";
(function () {
    'use strict';

    angular
        .module('etatCivilApp')
        .controller('DeclarationNaissanceRechercheDetailController', DeclarationNaissanceRechercheDetailController);

    DeclarationNaissanceRechercheDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'DeclarationExtrait', 'Pays', 'Ville'];

    function DeclarationNaissanceRechercheDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, DeclarationExtrait, Pays, Ville) {

        var vm = this;
        vm.declarationExtrait = entity;
        vm.paysDeclaration = "FRANCE";
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.declarationExtrait.enfant.dateNaissance = new Date(vm.declarationExtrait.enfant.dateNaissance);
        vm.declarationExtrait.mere.dateNaissance = new Date(vm.declarationExtrait.mere.dateNaissance);
        vm.declarationExtrait.pere.dateNaissance = new Date(vm.declarationExtrait.pere.dateNaissance);
        vm.villes = Ville.query();
        vm.pays = Pays.query();
        vm.dateformat = "dd-MM-yyyy";
        vm.previousState = previousState.name;


        function openCalendar(date) {
            vm.datePickerOpenStatus[date] = true;
        }

        vm.setCopieLiterale = function ($file, declarationExtrait) {
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        declarationExtrait.piecesJointes[0].copieLiterale = base64Data;
                        declarationExtrait.piecesJointes[0].copieLiteraleContentType = $file.type;
                    });
                });
            }
        };

        vm.setCopieCarte = function ($file, declarationExtrait) {
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        declarationExtrait.piecesJointes[0].copieCarte = base64Data;
                        declarationExtrait.piecesJointes[0].copieCarteContentType = $file.type;
                    });
                });
            }
        };

        function save () {
            vm.isSaving = true;
            if (vm.declarationExtrait.id !== null) {
                DeclarationExtrait.update(vm.declarationExtrait, onSaveSuccess, onSaveError);
            } else {
                DeclarationExtrait.save(vm.declarationExtrait, onSaveSuccess, onSaveError);
            }
        }

    }
})();
