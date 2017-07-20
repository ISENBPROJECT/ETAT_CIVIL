(function() {
    'use strict';

    angular
        .module('etatCivilApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('commune', {
            parent: 'entity',
            url: '/commune',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'etatCivilApp.commune.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/commune/communes.html',
                    controller: 'CommuneController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('commune');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('commune-detail', {
            parent: 'commune',
            url: '/commune/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'etatCivilApp.commune.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/commune/commune-detail.html',
                    controller: 'CommuneDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('commune');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Commune', function($stateParams, Commune) {
                    return Commune.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'commune',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('commune-detail.edit', {
            parent: 'commune-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/commune/commune-dialog.html',
                    controller: 'CommuneDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Commune', function(Commune) {
                            return Commune.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('commune.new', {
            parent: 'commune',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/commune/commune-dialog.html',
                    controller: 'CommuneDialogController',
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
                    $state.go('commune', null, { reload: 'commune' });
                }, function() {
                    $state.go('commune');
                });
            }]
        })
        .state('commune.edit', {
            parent: 'commune',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/commune/commune-dialog.html',
                    controller: 'CommuneDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Commune', function(Commune) {
                            return Commune.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('commune', null, { reload: 'commune' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('commune.delete', {
            parent: 'commune',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/commune/commune-delete-dialog.html',
                    controller: 'CommuneDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Commune', function(Commune) {
                            return Commune.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('commune', null, { reload: 'commune' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
