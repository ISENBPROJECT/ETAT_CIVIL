(function () {
    'use strict';

    angular
        .module('etatCivilApp')
        .controller('DeclarationNaissanceRechercheController', DeclarationNaissanceRechercheController);

    DeclarationNaissanceRechercheController.$inject = ['$scope', '$state', 'DeclarationExtrait'];

    function DeclarationNaissanceRechercheController($scope, $state, DeclarationExtrait) {
        var vm = this;

        vm.declarationNaissances = [];
        vm.search = search;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        function search() {

            var dataSearch = {
                numeroRegistre: vm.declarationNaissance.numeroRegistre,
                nomEnfant: vm.declarationNaissance.nomEnfant,
                prenomEnfant: vm.declarationNaissance.prenomEnfant,
                dateNaissanceEnfant: vm.declarationNaissance.dateNaissance
            };
            console.log(vm.declarationNaissance.dateNaissance)
            DeclarationExtrait.search(dataSearch,
                onSuccess, onError);


        }

        function onSuccess(data, headers) {
            vm.declarationNaissances = data;
            console.log(data)
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
