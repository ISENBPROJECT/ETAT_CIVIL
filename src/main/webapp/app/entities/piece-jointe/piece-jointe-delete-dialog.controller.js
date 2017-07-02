(function() {
    'use strict';

    angular
        .module('etatCivilApp')
        .controller('PieceJointeDeleteController',PieceJointeDeleteController);

    PieceJointeDeleteController.$inject = ['$uibModalInstance', 'entity', 'PieceJointe'];

    function PieceJointeDeleteController($uibModalInstance, entity, PieceJointe) {
        var vm = this;

        vm.pieceJointe = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PieceJointe.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
