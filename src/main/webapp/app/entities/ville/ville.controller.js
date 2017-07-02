(function() {
    'use strict';

    angular
        .module('etatCivilApp')
        .controller('VilleController', VilleController);

    VilleController.$inject = ['Ville'];

    function VilleController(Ville) {

        var vm = this;

        vm.villes = [];

        loadAll();

        function loadAll() {
            Ville.query(function(result) {
                vm.villes = result;
                vm.searchQuery = null;
            });
        }
    }
})();
