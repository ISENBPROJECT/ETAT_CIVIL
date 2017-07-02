(function() {
    'use strict';

    angular
        .module('etatCivilApp')
        .controller('RegistreNaissanceController', RegistreNaissanceController);

    RegistreNaissanceController.$inject = ['RegistreNaissance'];

    function RegistreNaissanceController(RegistreNaissance) {

        var vm = this;

        vm.registreNaissances = [];

        loadAll();

        function loadAll() {
            RegistreNaissance.query(function(result) {
                vm.registreNaissances = result;
                vm.searchQuery = null;
            });
        }
    }
})();
