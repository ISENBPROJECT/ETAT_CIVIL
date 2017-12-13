"use strict";
(function () {
    'use strict';

    angular
        .module('etatCivilApp')
        .controller('DeclarationNaissanceRechercheDetailController', DeclarationNaissanceRechercheDetailController);

    DeclarationNaissanceRechercheDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'DeclarationExtrait', 'Pays', 'Ville','$state'];

    function DeclarationNaissanceRechercheDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, DeclarationExtrait, Pays, Ville,$state) {

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
        vm.dateNaissanceMereError = false;
        vm.dateNaissanceMereEnfantInfDix = false;
        vm.dateNaissancePereError = false;
        vm.dateNaissancePlusCinquante = false;
        vm.dateNaissanceEnfantErrorFutur = false;
        vm.save = save;

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
        };
        function onSaveSuccess(result) {
            $state.go('declaration-naissance-recherche');
        }

        function onSaveError() {
        }

        function modifierVillePourSansPere() {

            var villeInconnu;

            vm.villes.forEach(function (ville) {
                if (ville.nom == "Inconnu") {
                    villeInconnu = ville;
                }
            });


            vm.declarationExtrait.lieuNaissancePereId = villeInconnu,
                vm.declarationExtrait.adressePereId = villeInconnu

        };


        //si l'année de naissance de la mere est inférieure à 10 ans,
        // je l'empeche et j'annule la date car l'attribut du name n'est qu'en getter
        function verifierSiMajeur() {

            var dateNaissance = vm.declarationExtrait.dateNaissanceMere;
            vm.declarationExtrait.dateNaissanceMereCache = vm.declarationExtrait.dateNaissanceMere;
            var dateNaissanceEnfant = vm.declarationExtrait.dateNaissanceEnfant;
            if (null != dateNaissance) {
                var today = new Date();
                var todayTime = today.getFullYear();
                var dateNaissanceMere = new Date(dateNaissance).getFullYear();

                if (todayTime - dateNaissanceMere < 10) {
                    vm.dateNaissanceMereError = true;
                    vm.declarationExtrait.dateNaissanceMereCache = null;
                } else if (null != dateNaissanceEnfant && (new Date(dateNaissanceEnfant).getFullYear() - dateNaissanceMere) > 50) {
                    vm.dateNaissancePlusCinquante = true;
                } else if (null != dateNaissanceEnfant && (new Date(dateNaissanceEnfant).getFullYear() - dateNaissanceMere) < 10) {
                    vm.dateNaissanceMereEnfantInfDix = true;
                }

                else {
                    vm.dateNaissanceMereError = false;
                    vm.dateNaissancePlusCinquante = false;
                    vm.dateNaissanceMereEnfantInfDix = false;
                }
            }
        }

        function verifierSiPereMajeur() {

            var dateNaissance = vm.declarationExtrait.dateNaissancePere;

            if (null != dateNaissance) {
                var today = new Date();
                var todayTime = today.getFullYear();
                var dateNaissanceTime = new Date(dateNaissance).getFullYear();

                if (todayTime - dateNaissanceTime < 10) {
                    vm.dateNaissancePereError = true;
                    vm.declarationExtrait.dateNaissancePere = null;
                } else {
                    vm.dateNaissancePereError = false;
                }

            }
        };

        function dateNaissanceEnfantFutur() {
            var dateNaissance = vm.declarationExtrait.dateNaissanceEnfant;

            if (null != dateNaissance) {
                var today = new Date();
                var todayTime = today.getFullYear();
                var dateNaissanceTime = new Date(dateNaissance).getFullYear();

                if (todayTime < dateNaissanceTime) {
                    vm.dateNaissanceEnfantErrorFutur = true;
                    vm.declarationExtrait.dateNaissanceEnfant = null;
                } else {
                    vm.dateNaissanceEnfantErrorFutur = false;
                }
                verifierSiMajeur();
            }
        };

    }
})();
