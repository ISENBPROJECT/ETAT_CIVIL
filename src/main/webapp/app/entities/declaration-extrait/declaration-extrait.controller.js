(function() {
    'use strict';

    angular
        .module('etatCivilApp')
        .controller('DeclarationExtraitController', DeclarationExtraitController);

    DeclarationExtraitController.$inject = ['DataUtils', 'DeclarationExtrait'];

    function DeclarationExtraitController(DataUtils, DeclarationExtrait) {

        var vm = this;

        vm.declarationExtraits = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            DeclarationExtrait.query(function(result) {
                vm.declarationExtraits = result;
                vm.searchQuery = null;
            });
        }
    }
})();
