(function() {
    'use strict';

    angular
        .module('etatCivilApp')
        .controller('PaysController', PaysController);

    PaysController.$inject = ['Pays'];

    function PaysController(Pays) {

        var vm = this;

        vm.pays = [];

        loadAll();

        function loadAll() {
            Pays.query(function(result) {
                vm.pays = result;
                vm.searchQuery = null;
            });
        }
    }
})();
