(function() {
    'use strict';

    angular
        .module('etatCivilApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('personne', {
            parent: 'entity',
            url: '/personne',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'etatCivilApp.personne.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/personne/personnes.html',
                    controller: 'PersonneController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('personne');
                    $translatePartialLoader.addPart('genre');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('personne-detail', {
            parent: 'personne',
            url: '/personne/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'etatCivilApp.personne.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/personne/personne-detail.html',
                    controller: 'PersonneDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('personne');
                    $translatePartialLoader.addPart('genre');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Personne', function($stateParams, Personne) {
                    return Personne.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'personne',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('personne-detail.edit', {
            parent: 'personne-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/personne/personne-dialog.html',
                    controller: 'PersonneDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Personne', function(Personne) {
                            return Personne.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('personne.new', {
            parent: 'personne',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/personne/personne-dialog.html',
                    controller: 'PersonneDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nom: null,
                                prenom: null,
                                dateNaissance: null,
                                fonction: null,
                                genre: null,
                                numeroCarteIdentite: null,
                                numeroPassport: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('personne', null, { reload: 'personne' });
                }, function() {
                    $state.go('personne');
                });
            }]
        })
        .state('personne.edit', {
            parent: 'personne',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/personne/personne-dialog.html',
                    controller: 'PersonneDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Personne', function(Personne) {
                            return Personne.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('personne', null, { reload: 'personne' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('personne.delete', {
            parent: 'personne',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/personne/personne-delete-dialog.html',
                    controller: 'PersonneDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Personne', function(Personne) {
                            return Personne.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('personne', null, { reload: 'personne' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
