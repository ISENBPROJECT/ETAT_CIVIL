'use strict';

describe('Controller Tests', function() {

    describe('DeclarationExtrait Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockDeclarationExtrait, MockVille, MockPays;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockDeclarationExtrait = jasmine.createSpy('MockDeclarationExtrait');
            MockVille = jasmine.createSpy('MockVille');
            MockPays = jasmine.createSpy('MockPays');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'DeclarationExtrait': MockDeclarationExtrait,
                'Ville': MockVille,
                'Pays': MockPays
            };
            createController = function() {
                $injector.get('$controller')("DeclarationExtraitDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'etatCivilApp:declarationExtraitUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
