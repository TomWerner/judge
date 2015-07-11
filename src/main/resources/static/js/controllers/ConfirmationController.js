angular.module('app').controller('confirmation',
    function($rootScope, $scope, $routeParams, $http, $location) {
        $scope.$route = $route;
        $scope.userId = $routeParams.userId;
        $scope.uuid = $routeParams.uuid;
        $scope.confirmationError = true;
        $scope.errorMessage = "There was a problem confirming your account.";
        $scope.successMessage = "Congratulations! You're all ready for the Hawkeye Programming Challenge!";

        $http.get("/user/confirm/" + $scope.userId + "/" + $scope.uuid)
            .success(function(data) {
                $scope.confirmationError = !data;
            })
            .error(function(data) {
                $scope.confirmationError = true;
            });

        $scope.resend = function() {
            $http.post("/user/resendConfirmation/" + $scope.userId, {})
                .success(function(data) {
                    $scope.confirmationError = !data;
                    if ($scope.confirmationError) {
                        $scope.errorMessage = "There was a problem resending your confirmation email. Please try again later";
                    }
                })
                .error(function(data) {
                    $scope.confirmationError = true;
                    if ($scope.confirmationError) {
                        $scope.errorMessage = "There was a problem resending your confirmation email. Please try again later";
                    }
                });
        };
    }
);