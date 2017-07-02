(function() {
    'use strict';

    angular
        .module('etatCivilApp')
        .controller('PersonneController', PersonneController);

    PersonneController.$inject = ['Personne'];

    function PersonneController(Personne) {

        var vm = this;

        vm.personnes = [];

        loadAll();

        function loadAll() {
            Personne.query(function(result) {
                vm.personnes = result;
                vm.searchQuery = null;
            });
        }
    }
})();
