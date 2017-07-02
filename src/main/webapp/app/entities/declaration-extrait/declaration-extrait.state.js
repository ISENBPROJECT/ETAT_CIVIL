(function() {
    'use strict';

    angular
        .module('etatCivilApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('declaration-extrait', {
            parent: 'entity',
            url: '/declaration-extrait',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'etatCivilApp.declarationExtrait.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/declaration-extrait/declaration-extraits.html',
                    controller: 'DeclarationExtraitController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('declarationExtrait');
                    $translatePartialLoader.addPart('genre');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('declaration-extrait-detail', {
            parent: 'declaration-extrait',
            url: '/declaration-extrait/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'etatCivilApp.declarationExtrait.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/declaration-extrait/declaration-extrait-detail.html',
                    controller: 'DeclarationExtraitDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('declarationExtrait');
                    $translatePartialLoader.addPart('genre');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'DeclarationExtrait', function($stateParams, DeclarationExtrait) {
                    return DeclarationExtrait.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'declaration-extrait',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('declaration-extrait-detail.edit', {
            parent: 'declaration-extrait-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/declaration-extrait/declaration-extrait-dialog.html',
                    controller: 'DeclarationExtraitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['DeclarationExtrait', function(DeclarationExtrait) {
                            return DeclarationExtrait.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('declaration-extrait.new', {
            parent: 'declaration-extrait',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/declaration-extrait/declaration-extrait-dialog.html',
                    controller: 'DeclarationExtraitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                copieLiterale: null,
                                copieLiteraleContentType: null,
                                copieCarte: null,
                                copieCarteContentType: null,
                                mention: null,
                                nomEnfant: null,
                                prenomEnfant: null,
                                dateNaissanceEnfant: null,
                                genreEnfant: null,
                                nomMere: null,
                                prenomMere: null,
                                dateNaissanceMere: null,
                                fonctionMere: null,
                                adresseComplMere: null,
                                numeroIdentiteMere: null,
                                numeroPassportMere: null,
                                nomPere: null,
                                prenomPere: null,
                                dateNaissancePere: null,
                                adresseComplPere: null,
                                fonctionPere: null,
                                numeroIdentitePere: null,
                                numeroPassportPere: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('declaration-extrait', null, { reload: 'declaration-extrait' });
                }, function() {
                    $state.go('declaration-extrait');
                });
            }]
        })
        .state('declaration-extrait.edit', {
            parent: 'declaration-extrait',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/declaration-extrait/declaration-extrait-dialog.html',
                    controller: 'DeclarationExtraitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['DeclarationExtrait', function(DeclarationExtrait) {
                            return DeclarationExtrait.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('declaration-extrait', null, { reload: 'declaration-extrait' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('declaration-extrait.delete', {
            parent: 'declaration-extrait',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/declaration-extrait/declaration-extrait-delete-dialog.html',
                    controller: 'DeclarationExtraitDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['DeclarationExtrait', function(DeclarationExtrait) {
                            return DeclarationExtrait.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('declaration-extrait', null, { reload: 'declaration-extrait' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
