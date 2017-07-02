(function() {
    'use strict';

    angular
        .module('etatCivilApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('extrait', {
            parent: 'entity',
            url: '/extrait',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'etatCivilApp.extrait.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/extrait/extraits.html',
                    controller: 'ExtraitController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('extrait');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('extrait-detail', {
            parent: 'extrait',
            url: '/extrait/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'etatCivilApp.extrait.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/extrait/extrait-detail.html',
                    controller: 'ExtraitDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('extrait');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Extrait', function($stateParams, Extrait) {
                    return Extrait.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'extrait',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('extrait-detail.edit', {
            parent: 'extrait-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/extrait/extrait-dialog.html',
                    controller: 'ExtraitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Extrait', function(Extrait) {
                            return Extrait.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('extrait.new', {
            parent: 'extrait',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/extrait/extrait-dialog.html',
                    controller: 'ExtraitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                numeroRegistre: null,
                                mention: null,
                                dateDeclaration: null,
                                validated: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('extrait', null, { reload: 'extrait' });
                }, function() {
                    $state.go('extrait');
                });
            }]
        })
        .state('extrait.edit', {
            parent: 'extrait',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/extrait/extrait-dialog.html',
                    controller: 'ExtraitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Extrait', function(Extrait) {
                            return Extrait.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('extrait', null, { reload: 'extrait' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('extrait.delete', {
            parent: 'extrait',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/extrait/extrait-delete-dialog.html',
                    controller: 'ExtraitDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Extrait', function(Extrait) {
                            return Extrait.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('extrait', null, { reload: 'extrait' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
