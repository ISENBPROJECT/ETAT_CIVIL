"use strict";
(function () {
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
            .state('declaration-naissance-recherche', {
                parent: 'entity',
                url: '/declaration-naissance-recherche',
                data: {
                    authorities: ['ROLE_USER'],
                    //pageTitle: 'etatcivilApp.declarationNaissance.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/declaration-extrait/declaration-extrait-recherche.html',
                        controller: 'DeclarationNaissanceRechercheController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('declarationExtrait');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('declaration-extrait-detail-recherche', {
                parent: 'declaration-naissance-recherche',
                url: '/detail/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'etatCivilApp.declarationExtrait.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/declaration-extrait/declaration-extrait-detail-recherche.html',
                        controller: 'DeclarationNaissanceRechercheDetailController',
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
                            name: $state.current.name || 'declaration-extrai-detail-recherche',
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
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/declaration-extrait/declaration-extrait-dialog.html',
                        controller: 'DeclarationExtraitDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: ['DeclarationExtrait', function (DeclarationExtrait) {
                                return DeclarationExtrait.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('^', {}, {reload: false});
                    }, function () {
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
                views: {
                    'content@': {
                        templateUrl: 'app/entities/declaration-extrait/declaration-extrait-dialog.html',
                        controller: 'DeclarationExtraitDialogController',
                        controllerAs: 'vm'
                    }
                },
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
                            paysNaissanceEnfant: null,
                            villeNaissanceEnfant: null,
                            paysNaissanceMere: null,
                            villeNaissanceMere: null,
                            paysResidenceMere: null,
                            villeResidenceMere: null,
                            paysNaissancePere: null,
                            villeNaissancePere: null,
                            paysResidencePere: null,
                            villeResidencePere: null,
                            id: null
                        };
                    }
                }

            })
            .state('declaration-extrait.edit', {
                parent: 'declaration-extrait',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/declaration-extrait/declaration-extrait-dialog.html',
                        controller: 'DeclarationExtraitDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: ['DeclarationExtrait', function (DeclarationExtrait) {
                                return DeclarationExtrait.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('declaration-extrait', null, {reload: 'declaration-extrait'});
                    }, function () {
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
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/declaration-extrait/declaration-extrait-delete-dialog.html',
                        controller: 'DeclarationExtraitDeleteController',
                        controllerAs: 'vm',
                        size: 'md',
                        resolve: {
                            entity: ['DeclarationExtrait', function (DeclarationExtrait) {
                                return DeclarationExtrait.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('declaration-extrait', null, {reload: 'declaration-extrait'});
                    }, function () {
                        $state.go('^');
                    });
                }]
            })
            .state('declaration-extrait-affichagePdf', {
                parent: 'declaration-extrait.new',
                url: '/impressionPdf',
                data: {
                    authorities: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/declaration-extrait/affichagePdf.html',
                        controller: 'DeclarationExtraitDialogController',
                        controllerAs: 'vm'
                    }
                }

            })
            .state('declaration-extrait.recap', {
                parent: 'declaration-extrait.new',
                url: '/recap',
                data: {
                    authorities: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/declaration-extrait/declaration-extrait-recap.html',
                        controller: 'DeclarationExtraitDialogController',
                        controllerAs: 'vm'
                    }
                }

            });
    }

})();
