(function() {
    'use strict';

    angular
        .module('etatCivilApp')
        .controller('ExtraitController', ExtraitController);

    ExtraitController.$inject = ['DataUtils', 'Extrait'];

    function ExtraitController(DataUtils, Extrait) {

        var vm = this;

        vm.extraits = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            Extrait.query(function(result) {
                vm.extraits = result;
                vm.searchQuery = null;
            });
        }
    }
})();
