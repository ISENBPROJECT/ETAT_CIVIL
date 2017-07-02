(function() {
    'use strict';

    angular
        .module('etatCivilApp')
        .controller('DeclarationExtraitDeleteController',DeclarationExtraitDeleteController);

    DeclarationExtraitDeleteController.$inject = ['$uibModalInstance', 'entity', 'DeclarationExtrait'];

    function DeclarationExtraitDeleteController($uibModalInstance, entity, DeclarationExtrait) {
        var vm = this;

        vm.declarationExtrait = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            DeclarationExtrait.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
