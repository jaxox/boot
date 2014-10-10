app.directive('fbSigninDirective', function(){
    return {
        restrict: 'E',
        templateUrl : 'partials/tpl/fb_signin.tpl.html',
        controller:function($scope, fbSigninService){
            $scope.fb_signin=function(scope){
                console.log('fb login function here');
                fbSigninService.fb_signin(scope);
            };
        },
        controllerAs:'fbSigninCtrl'
    };
});

