(function() {
    'use strict';

    angular
        .module('etatCivilApp')
        .controller('RegistreNaissanceDeleteController',RegistreNaissanceDeleteController);

    RegistreNaissanceDeleteController.$inject = ['$uibModalInstance', 'entity', 'RegistreNaissance'];

    function RegistreNaissanceDeleteController($uibModalInstance, entity, RegistreNaissance) {
        var vm = this;

        vm.registreNaissance = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            RegistreNaissance.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
