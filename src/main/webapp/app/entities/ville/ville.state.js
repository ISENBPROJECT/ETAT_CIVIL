(function() {
    'use strict';

    angular
        .module('etatCivilApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('ville', {
            parent: 'entity',
            url: '/ville',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'etatCivilApp.ville.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/ville/villes.html',
                    controller: 'VilleController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('ville');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('ville-detail', {
            parent: 'ville',
            url: '/ville/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'etatCivilApp.ville.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/ville/ville-detail.html',
                    controller: 'VilleDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('ville');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Ville', function($stateParams, Ville) {
                    return Ville.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'ville',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('ville-detail.edit', {
            parent: 'ville-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ville/ville-dialog.html',
                    controller: 'VilleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Ville', function(Ville) {
                            return Ville.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('ville.new', {
            parent: 'ville',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ville/ville-dialog.html',
                    controller: 'VilleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nom: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('ville', null, { reload: 'ville' });
                }, function() {
                    $state.go('ville');
                });
            }]
        })
        .state('ville.edit', {
            parent: 'ville',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ville/ville-dialog.html',
                    controller: 'VilleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Ville', function(Ville) {
                            return Ville.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('ville', null, { reload: 'ville' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('ville.delete', {
            parent: 'ville',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ville/ville-delete-dialog.html',
                    controller: 'VilleDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Ville', function(Ville) {
                            return Ville.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('ville', null, { reload: 'ville' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
