'use strict';

describe('Controller Tests', function() {

    describe('Extrait Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockExtrait, MockVille, MockPersonne, MockUser, MockPieceJointe;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockExtrait = jasmine.createSpy('MockExtrait');
            MockVille = jasmine.createSpy('MockVille');
            MockPersonne = jasmine.createSpy('MockPersonne');
            MockUser = jasmine.createSpy('MockUser');
            MockPieceJointe = jasmine.createSpy('MockPieceJointe');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Extrait': MockExtrait,
                'Ville': MockVille,
                'Personne': MockPersonne,
                'User': MockUser,
                'PieceJointe': MockPieceJointe
            };
            createController = function() {
                $injector.get('$controller')("ExtraitDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'etatCivilApp:extraitUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
