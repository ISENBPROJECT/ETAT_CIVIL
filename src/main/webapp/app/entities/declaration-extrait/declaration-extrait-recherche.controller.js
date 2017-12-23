"use strict";
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

        vm.numeroRegistre = null;
        vm.nomEnfant = null;
        vm.prenomEnfant = null;
        vm.dateNaissanceEnfant = null;

        function search() {
            if (null != vm.numeroRegistre && vm.numeroRegistre === "") {
                vm.numeroRegistre = null;
            }
            if (null != vm.nomEnfant && vm.nomEnfant === "") {
                vm.nomEnfant = null;
            }
            if (null != vm.prenomEnfant && vm.prenomEnfant === "") {
                vm.prenomEnfant = null;
            }
            if (null != vm.dateNaissance && vm.dateNaissance === "") {
                vm.dateNaissance = null;
            }
            var dataSearch = {
                numeroRegistre: vm.numeroRegistre,
                nomEnfant: vm.nomEnfant,
                prenomEnfant: vm.prenomEnfant,
                dateNaissanceEnfant: vm.dateNaissance
            };
            DeclarationRecherche.search(dataSearch,
                onSuccess, onError);


        }

        function onSuccess(data, headers) {
            vm.declarationNaissances = data;
            if (data.length != 0) {
                $scope.noData = false;
            } else {
                $scope.noData = true;
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
