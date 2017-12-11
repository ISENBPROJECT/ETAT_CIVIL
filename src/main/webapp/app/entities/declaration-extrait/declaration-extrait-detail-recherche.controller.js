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
        vm.paysDeclaration = "FRANCE"

    }
})();
