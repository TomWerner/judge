angular.module('app').controller('adminTeams',
    function($rootScope, $scope, $route, $http, $location) {
        $scope.$route = $route;

        if (!$rootScope.authenticated) {
            $location.path("/login");
        }

        $http.get('team/allTeams')
            .success(function(/** Array.<Team> */ data) {
                $scope.teams = data;
            })
            .error(function(data, status, headers, config) {
                console.log("Error trying to get all teams");
            });
    }
);