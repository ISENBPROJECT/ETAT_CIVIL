(function () {
    'use strict';

    angular
        .module('etatCivilApp')
        .controller('DeclarationNaissanceRechercheController', DeclarationNaissanceRechercheController);

    DeclarationNaissanceRechercheController.$inject = ['$scope', '$state', 'DeclarationRecherche'];

    function DeclarationNaissanceRechercheController($scope, $state, DeclarationRecherche) {
        var vm = this;

        vm.declarationNaissances = [];
        vm.search = search;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        $scope.noData = true;

        function search() {

            var dataSearch = {
                numeroRegistre: vm.declarationNaissance.numeroRegistre,
                nomEnfant: vm.declarationNaissance.nomEnfant,
                prenomEnfant: vm.declarationNaissance.prenomEnfant,
                dateNaissanceEnfant: vm.declarationNaissance.dateNaissance
            };
            console.log(vm.declarationNaissance.dateNaissance)
            DeclarationRecherche.search(dataSearch,
                onSuccess, onError);


        }

        function onSuccess(data, headers) {
            vm.declarationNaissances = data;

            if(data.length != 0){
                $scope.noData =false;
            }else {
                $scope.noData =true;
            }
        }

        function onError() {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dateNaissance = false;

        function openCalendar(date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
