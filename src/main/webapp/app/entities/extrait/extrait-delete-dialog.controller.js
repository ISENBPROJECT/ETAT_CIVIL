(function() {
    'use strict';

    angular
        .module('etatCivilApp')
        .controller('ExtraitDeleteController',ExtraitDeleteController);

    ExtraitDeleteController.$inject = ['$uibModalInstance', 'entity', 'Extrait'];

    function ExtraitDeleteController($uibModalInstance, entity, Extrait) {
        var vm = this;

        vm.extrait = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Extrait.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
