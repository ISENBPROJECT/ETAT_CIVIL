(function() {
    'use strict';

    angular
        .module('etatCivilApp')
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

        vm.villesNaissancesMere = [];
        vm.villesNaissancesEnfant = [];
        vm.villesNaissancesPere = [];
        vm.villesResidenceMere = [];
        vm.villesResidencePere = [];
        vm.villesResidenceEnfant = [];
        vm.updateVilleNaissanceMere = updateVilleNaissanceMere;
        vm.updateVilleNaissancePere = updateVilleNaissancePere;
        vm.updateVilleNaissanceEnfant = updateVilleNaissanceEnfant;
        vm.updateVilleResidenceMere = updateVilleResidenceMere;
        vm.updateVilleResidencePere = updateVilleResidencePere;
        vm.updateResidenceEnfant = updateResidenceEnfant;
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
            $scope.$emit('etatCivilApp:declarationExtraitUpdate', result);
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

        function updateVilleNaissanceMere(nomPays) {
            vm.villesNaissancesMere.length = 0;
            var selectedCountry;
            vm.pays.forEach(function (pays) {
                if (pays.nom == nomPays) {
                    selectedCountry = pays;
                }
            });
            vm.villes.forEach(function (ville) {
                if (ville.paysId == selectedCountry.id) {
                    vm.villesNaissancesMere.push(ville);
                }
            })
        }
        function updateVilleNaissanceEnfant(nomPays) {
            vm.villesNaissancesEnfant.length = 0;
            var selectedCountry;
            vm.pays.forEach(function (pays) {
                if (pays.nom == nomPays) {
                    selectedCountry = pays;
                }
            });
            vm.villes.forEach(function (ville) {
                if (ville.paysId == selectedCountry.id) {
                    vm.villesNaissancesEnfant.push(ville);
                }
            })
        }

        function updateVilleNaissancePere(nomPays) {
            vm.villesNaissancesPere.length = 0;
            var selectedCountry;
            vm.pays.forEach(function (pays) {
                if (pays.nom == nomPays) {
                    selectedCountry = pays;
                }
            });
            vm.villes.forEach(function (ville) {
                if (ville.paysId == selectedCountry.id) {
                    vm.villesNaissancesPere.push(ville);
                }
            })
        };

        function updateVilleResidenceMere(nomPays) {
            vm.villesResidenceMere.length = 0;
            vm.declarationExtrait.adresseEnfantId = null;
            var selectedCountry;
            vm.pays.forEach(function (pays) {
                if (pays.nom == nomPays) {
                    selectedCountry = pays;
                }
            });
            vm.villes.forEach(function (ville) {
                if (ville.paysId == selectedCountry.id) {
                    vm.declarationExtrait.adrPaysEnfantId = selectedCountry.nom;
                    vm.villesResidenceMere.push(ville);
                }
            })
        };
        function updateVilleResidencePere(nomPays) {
            vm.villesResidencePere.length = 0;
            var selectedCountry;
            vm.pays.forEach(function (pays) {
                if (pays.nom == nomPays) {
                    selectedCountry = pays;
                }
            });
            vm.villes.forEach(function (ville) {
                if (ville.paysId == selectedCountry.id) {
                    vm.villesResidencePere.push(ville);
                }
            })
        };

        function updateResidenceEnfant(selectedResidence) {

            vm.villes.forEach(function (ville) {
                if (ville.nom == selectedResidence) {
                    vm.declarationExtrait.adresseEnfantId = selectedResidence;
                }

            })

        }
    }
})();
