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
        function search() {

            var dataSearch = {
                numeroRegistre: vm.declarationNaissance.id,
                nomEnfant:vm.declarationNaissance.informationEnfant.nom,
                prenomEnfant: vm.declarationNaissance.informationEnfant.prenom,
                dateNaissanceEnfant: vm.declarationNaissance.dateNaissance
            };

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


    }
})();
