(function() {
    'use strict';

    angular
        .module('etatCivilApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('piece-jointe', {
            parent: 'entity',
            url: '/piece-jointe',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'etatCivilApp.pieceJointe.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/piece-jointe/piece-jointes.html',
                    controller: 'PieceJointeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pieceJointe');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('piece-jointe-detail', {
            parent: 'piece-jointe',
            url: '/piece-jointe/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'etatCivilApp.pieceJointe.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/piece-jointe/piece-jointe-detail.html',
                    controller: 'PieceJointeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pieceJointe');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PieceJointe', function($stateParams, PieceJointe) {
                    return PieceJointe.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'piece-jointe',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('piece-jointe-detail.edit', {
            parent: 'piece-jointe-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/piece-jointe/piece-jointe-dialog.html',
                    controller: 'PieceJointeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PieceJointe', function(PieceJointe) {
                            return PieceJointe.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('piece-jointe.new', {
            parent: 'piece-jointe',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/piece-jointe/piece-jointe-dialog.html',
                    controller: 'PieceJointeDialogController',
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
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('piece-jointe', null, { reload: 'piece-jointe' });
                }, function() {
                    $state.go('piece-jointe');
                });
            }]
        })
        .state('piece-jointe.edit', {
            parent: 'piece-jointe',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/piece-jointe/piece-jointe-dialog.html',
                    controller: 'PieceJointeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PieceJointe', function(PieceJointe) {
                            return PieceJointe.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('piece-jointe', null, { reload: 'piece-jointe' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('piece-jointe.delete', {
            parent: 'piece-jointe',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/piece-jointe/piece-jointe-delete-dialog.html',
                    controller: 'PieceJointeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PieceJointe', function(PieceJointe) {
                            return PieceJointe.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('piece-jointe', null, { reload: 'piece-jointe' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
