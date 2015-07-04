angular.module('app', [ 'ngRoute' ])
    .config(function($routeProvider, $httpProvider) {

        $routeProvider.when('/', {
            templateUrl : 'html/home.html',
            controller : 'home'
        }).when('/login', {
            templateUrl : 'html/login.html',
            controller : 'navigation'
        }).when('/register', {
            templateUrl : 'html/register.html',
            controller : 'navigation'
        }).otherwise('/');

        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

    })
    .controller('home', function($scope, $http) {
        $http.get('/resource/').success(function(data) {
            $scope.greeting = data;
        })
    });

