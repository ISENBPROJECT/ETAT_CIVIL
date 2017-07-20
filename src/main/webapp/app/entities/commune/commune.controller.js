(function() {
    'use strict';

    angular
        .module('etatCivilApp')
        .controller('CommuneController', CommuneController);

    CommuneController.$inject = ['Commune'];

    function CommuneController(Commune) {

        var vm = this;

        vm.communes = [];

        loadAll();

        function loadAll() {
            Commune.query(function(result) {
                vm.communes = result;
                vm.searchQuery = null;
            });
        }
    }
})();
