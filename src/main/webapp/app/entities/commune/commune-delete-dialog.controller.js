(function() {
    'use strict';

    angular
        .module('etatCivilApp')
        .controller('CommuneDeleteController',CommuneDeleteController);

    CommuneDeleteController.$inject = ['$uibModalInstance', 'entity', 'Commune'];

    function CommuneDeleteController($uibModalInstance, entity, Commune) {
        var vm = this;

        vm.commune = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Commune.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
