(function () {
    'use strict';

    angular
        .module('etatCivilApp')
        .controller('DeclarationExtraitDialogController', DeclarationExtraitDialogController);

    DeclarationExtraitDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'DeclarationExtrait', 'Ville', 'Pays'];

    function DeclarationExtraitDialogController($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, DeclarationExtrait, Ville, Pays) {
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

        vm.villesNaissancesMere = [];
        vm.villesNaissancesEnfant = [];
        vm.villesNaissancesPere = [];
        vm.villesResidenceMere = [];
        vm.villesResidencePere = [];
        vm.villesResidenceEnfant = [];
        vm.villesDeclaration = [];
        vm.updateVilleNaissanceMere = updateVilleNaissanceMere;
        vm.updateVilleNaissancePere = updateVilleNaissancePere;
        vm.updateVilleNaissanceEnfant = updateVilleNaissanceEnfant;
        vm.updateVilleResidenceMere = updateVilleResidenceMere;
        vm.updateVilleResidencePere = updateVilleResidencePere;
        vm.updateResidenceEnfant = updateResidenceEnfant;
        vm.updateVilleDeclaration= updateVilleDeclaration;
        vm.F_PrintFile = F_PrintFile;
        vm.retourAccueil = retourAccueil;
        vm.ouvirActeNaissancePopup = ouvirActeNaissancePopup;
        vm.ouvirTranscriptionPopup = ouvirTranscriptionPopup;
        $timeout(function () {
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }

        function save() {
            vm.isSaving = true;
            if (vm.declarationExtrait.id !== null) {
                DeclarationExtrait.update(vm.declarationExtrait, onSaveSuccess, onSaveError);
            } else {
                DeclarationExtrait.save(vm.declarationExtrait, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess(result) {
            $scope.$emit('etatCivilApp:declarationExtraitUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
            $state.go('declaration-naissance-affichagePdf');
        }

        function onSaveError() {
            vm.isSaving = false;
        }


        vm.setCopieLiterale = function ($file, declarationExtrait) {
            if ($file) {
                DataUtils.toBase64($file, function (base64Data) {
                    $scope.$apply(function () {
                        declarationExtrait.copieLiterale = base64Data;
                        declarationExtrait.copieLiteraleContentType = $file.type;
                    });
                });
            }
        };

        vm.setCopieCarte = function ($file, declarationExtrait) {
            if ($file) {
                DataUtils.toBase64($file, function (base64Data) {
                    $scope.$apply(function () {
                        declarationExtrait.copieCarte = base64Data;
                        declarationExtrait.copieCarteContentType = $file.type;
                    });
                });
            }
        };
        vm.datePickerOpenStatus.dateNaissanceEnfant = false;
        vm.datePickerOpenStatus.dateNaissanceMere = false;
        vm.datePickerOpenStatus.dateNaissancePere = false;

        function openCalendar(date) {
            vm.datePickerOpenStatus[date] = true;
        }

        function updateVilleNaissanceMere() {
           vm.villesNaissancesMere.length = 0;

            vm.villes.forEach(function (ville) {
                if (ville.paysId == vm.declarationExtrait.paysNaissanceMereId.id) {
                    vm.villesNaissancesMere.push(ville);
                }
            })
        }

        function updateVilleNaissancePere() {
            vm.villesNaissancesPere.length = 0;
            vm.villes.forEach(function (ville) {
                if (ville.paysId == vm.declarationExtrait.paysNaissancePereId.id) {
                    vm.villesNaissancesPere.push(ville);
                }
            })
        };

        function updateVilleResidenceMere() {

            vm.villesResidenceMere.length = 0;
            vm.villes.forEach(function (ville) {
                if (ville.paysId == vm.declarationExtrait.adrPaysMereId.id) {
                    vm.villesResidenceMere.push(ville);
                }
            })
        };
        function updateVilleResidencePere() {

            vm.villesResidencePere.length = 0;
            vm.villes.forEach(function (ville) {
                if (ville.paysId == vm.declarationExtrait.adrPaysPereId.id) {
                    vm.villesResidencePere.push(ville);
                }
            })
        };

        function updateResidenceEnfant() {

            vm.villes.forEach(function (ville) {
                if (ville.id == vm.declarationExtrait.adrPaysEnfantId.id) {
                    vm.villesResidenceEnfant.push(ville);
                }

            })
        }

        function updateVilleNaissanceEnfant() {

            vm.villes.forEach(function (ville) {
                if (ville.id == vm.declarationExtrait.paysNaissanceEnfantId.id) {
                    vm.villesNaissancesEnfant.push(ville);
                }

            })
        }

        function updateVilleDeclaration() {

            vm.villes.forEach(function (ville) {
                if (ville.paysId == vm.declarationExtrait.paysDeclarationId.id) {
                    vm.villesDeclaration.push(ville);
                }

            })
        }

        function ouvirActeNaissancePopup() {

            var fichier = 'app/document/' + vm.declarationNaissance.informationEnfant.prenom + '_' + vm.declarationNaissance.informationEnfant.nom + '_acte_naissance.pdf';
            window.open(fichier, "popup", "width=900,height=600")
        }

        function ouvirTranscriptionPopup() {

            var fichier = 'app/document/' + vm.declarationNaissance.informationEnfant.prenom + '_' + vm.declarationNaissance.informationEnfant.nom + '_transcription_naissance.pdf';
            window.open(fichier, "popup", "width=900,height=600")
        }

        function F_PrintFile() {
            fichierpdf.print();
        }

        function retourAccueil() {
            $state.go('home');

        }
    }
})();
