(function() {
    'use strict';

    angular
        .module('etatCivilApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('registre-naissance', {
            parent: 'entity',
            url: '/registre-naissance',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'etatCivilApp.registreNaissance.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/registre-naissance/registre-naissances.html',
                    controller: 'RegistreNaissanceController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('registreNaissance');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('registre-naissance-detail', {
            parent: 'registre-naissance',
            url: '/registre-naissance/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'etatCivilApp.registreNaissance.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/registre-naissance/registre-naissance-detail.html',
                    controller: 'RegistreNaissanceDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('registreNaissance');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'RegistreNaissance', function($stateParams, RegistreNaissance) {
                    return RegistreNaissance.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'registre-naissance',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('registre-naissance-detail.edit', {
            parent: 'registre-naissance-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/registre-naissance/registre-naissance-dialog.html',
                    controller: 'RegistreNaissanceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['RegistreNaissance', function(RegistreNaissance) {
                            return RegistreNaissance.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('registre-naissance.new', {
            parent: 'registre-naissance',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/registre-naissance/registre-naissance-dialog.html',
                    controller: 'RegistreNaissanceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                numero: null,
                                anneeRegistre: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('registre-naissance', null, { reload: 'registre-naissance' });
                }, function() {
                    $state.go('registre-naissance');
                });
            }]
        })
        .state('registre-naissance.edit', {
            parent: 'registre-naissance',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/registre-naissance/registre-naissance-dialog.html',
                    controller: 'RegistreNaissanceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['RegistreNaissance', function(RegistreNaissance) {
                            return RegistreNaissance.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('registre-naissance', null, { reload: 'registre-naissance' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('registre-naissance.delete', {
            parent: 'registre-naissance',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/registre-naissance/registre-naissance-delete-dialog.html',
                    controller: 'RegistreNaissanceDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['RegistreNaissance', function(RegistreNaissance) {
                            return RegistreNaissance.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('registre-naissance', null, { reload: 'registre-naissance' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
