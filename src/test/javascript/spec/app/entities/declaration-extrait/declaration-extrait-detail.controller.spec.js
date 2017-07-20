'use strict';

describe('Controller Tests', function() {

    describe('DeclarationExtrait Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockDeclarationExtrait, MockPays, MockVille;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockDeclarationExtrait = jasmine.createSpy('MockDeclarationExtrait');
            MockPays = jasmine.createSpy('MockPays');
            MockVille = jasmine.createSpy('MockVille');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'DeclarationExtrait': MockDeclarationExtrait,
                'Pays': MockPays,
                'Ville': MockVille
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
