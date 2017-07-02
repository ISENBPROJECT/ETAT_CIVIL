(function() {
    'use strict';

    angular
        .module('etatCivilApp')
        .controller('PieceJointeController', PieceJointeController);

    PieceJointeController.$inject = ['DataUtils', 'PieceJointe'];

    function PieceJointeController(DataUtils, PieceJointe) {

        var vm = this;

        vm.pieceJointes = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            PieceJointe.query(function(result) {
                vm.pieceJointes = result;
                vm.searchQuery = null;
            });
        }
    }
})();
