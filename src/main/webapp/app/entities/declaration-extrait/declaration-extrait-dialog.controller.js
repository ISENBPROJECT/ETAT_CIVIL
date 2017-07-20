(function() {
    'use strict';

    angular
        .module('etatCivilApp')
        .controller('DeclarationExtraitDialogController', DeclarationExtraitDialogController);

    DeclarationExtraitDialogController.$inject = ['$timeout', '$scope', '$state', 'DataUtils', 'entity', 'DeclarationExtrait', 'Ville', 'Pays'];

    function DeclarationExtraitDialogController($timeout, $scope, $state, DataUtils, entity, DeclarationExtrait, Ville, Pays) {
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
        vm.updateVilleDeclaration = updateVilleDeclaration;
        vm.F_PrintFile = F_PrintFile;
        vm.retourAccueil = retourAccueil;
        vm.ouvirActeNaissancePopup = ouvirActeNaissancePopup;
        vm.ouvirTranscriptionPopup = ouvirTranscriptionPopup;
        vm.valider = valider;
        vm.isNotWithFather = false;
        $scope.test = false;
        $scope.isrecap = false;
        $scope.isnew = true;
        vm.renseignerInfosPere = renseignerInfosPere;
        vm.verifierSiMajeur = verifierSiMajeur;
        vm.verifierSiPereMajeur = verifierSiPereMajeur;
        vm.dateNaissanceEnfantFutur = dateNaissanceEnfantFutur;
        vm.dateNaissanceMereError = false;
        vm.dateNaissanceMereEnfantInfDix = false;
        vm.dateNaissancePereError = false;
        vm.dateNaissancePlusCinquante = false;
        vm.dateNaissanceEnfantErrorFutur = false;
        vm.declarationExtrait.paysNaissanceEnfantId = "FRANCE";
        vm.declarationExtrait.paysDeclarationId = "FRANCE";
        vm.declarationExtrait.lieuDeclarationId = "Bordeaux";
        $timeout(function () {
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear() {
            $scope.isnew = true;
            $scope.isrecap = false;
            $scope.test = false;
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
            vm.declarationExtrait.id = result.id;
            $state.go('declaration-extrait-affichagePdf');
            vm.isSaving = false;
        }

        function onSaveError() {
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

            if (vm.isNotWithFather) {
                modifierVillePourSansPere();
            }
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

            if (vm.isNotWithFather) {
                modifierVillePourSansPere();
            }
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
            var fichier = 'app/documents/' + vm.declarationExtrait.id + vm.declarationExtrait.prenomEnfant + '_' + vm.declarationExtrait.nomEnfant + '_acte_naissance.pdf';
            window.open(fichier, "popup", "width=900,height=600")
        }

        function ouvirTranscriptionPopup() {
            var fichier = 'app/documents/' + vm.declarationExtrait.id + vm.declarationExtrait.prenomEnfant + '_' + vm.declarationExtrait.nomEnfant + '_transcription_naissance.pdf';
            window.open(fichier, "popup", "width=900,height=600")
        }

        function F_PrintFile() {
            fichierpdf.print();
        }

        function retourAccueil() {
            console.log(DeclarationExtrait)
            $state.go('home');
            if (null != vm.declarationExtrait.prenomEnfant && null != vm.declarationExtrait.nomEnfant) {
                DeclarationExtrait.update(vm.declarationExtrait, onDeleteSuccess, onDeleteError);
            }

        }

        function valider() {
            $scope.test = true;
            $scope.isrecap = true;
            $scope.isnew = false;
            vm.isSaving = true;
        }


        function modifier() {
            $scope.isnew = true;
            $scope.isrecap = false;
            $scope.test = false;
        }

        function renseignerInfosPere() {
            var paysInconnu;
            vm.pays.forEach(function (pays) {
                if (pays.nom == "Inconnu") {
                    paysInconnu = pays;
                }
            });

            var villeInconnu;

            vm.villes.forEach(function (ville) {
                if (ville.nom == "Inconnu") {
                    villeInconnu = ville;
                }
            });


            if (vm.isNotWithFather) {
                vm.villesNaissancesPere.push(villeInconnu);
                vm.villesResidencePere.push(villeInconnu);
                vm.declarationExtrait.nomPere = "xxxx",
                    vm.declarationExtrait.prenomPere = "xxxx",
                    vm.declarationExtrait.dateNaissancePere = new Date(null),
                    vm.declarationExtrait.adresseComplPere = "xxxx",
                    vm.declarationExtrait.fonctionPere = "xxxx",
                    vm.declarationExtrait.numeroIdentitePere = "xxxx",
                    vm.declarationExtrait.numeroPassportPere = "xxxx",
                    vm.declarationExtrait.adrPaysPereId = paysInconnu,
                    vm.declarationExtrait.paysNaissancePereId = paysInconnu,
                    vm.declarationExtrait.lieuNaissancePereId = villeInconnu,
                    vm.declarationExtrait.adressePereId = villeInconnu
            }
            if (!vm.isNotWithFather) {
                vm.villesNaissancesPere.length == 0;
                vm.villesResidencePere.length == 0;
                vm.declarationExtrait.nomPere = null,
                    vm.declarationExtrait.prenomPere = null,
                    vm.declarationExtrait.dateNaissancePere = null,
                    vm.declarationExtrait.adresseComplPere = null,
                    vm.declarationExtrait.fonctionPere = null,
                    vm.declarationExtrait.numeroIdentitePere = null,
                    vm.declarationExtrait.numeroPassportPere = null,
                    vm.declarationExtrait.adrPaysPereId = null,
                    vm.declarationExtrait.paysNaissancePereId = null,
                    vm.declarationExtrait.lieuNaissancePereId = null,
                    vm.declarationExtrait.adressePereId = null
            }
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

        function onDeleteSuccess(result) {
            console.log(result)
        };

        function onDeleteError(result) {
            console.log(result)
        };
    }
})();
