angular.module('app').controller('navigation',
    function($rootScope, $scope, $route, $http, $location) {
        authenticate($http, $rootScope, $scope.credentials, function() {});

        $scope.credentials = {};
        $scope.teamDetails = {};
        $scope.$route = $route;

        $scope.login = function() {
            authenticate($http, $rootScope, $scope.credentials, function() {
                if ($rootScope.authenticated) {
                    $location.path("/");
                    $scope.error = false;
                } else {
                    $location.path("/login");
                    $scope.error = true;
                }
            });
        };

        $scope.register = function() {
            var errorCallback = function(clientErrorMessage) {
                $location.path("/register");
                $scope.error = true;
                $scope.errorMessage = clientErrorMessage;
            };
            checkRegistration($http, $scope.teamDetails,
                function() {
                    doRegister($http, $rootScope, $location, $scope.teamDetails, errorCallback)
                },
                errorCallback
            );
        };

        $scope.logout = function() {
            $http.post('logout', {}).success(function() {
                $rootScope.authenticated = false;
                $location.path("/");
            }).error(function(data) {
                $rootScope.authenticated = false;
            });
        };
    }
);

/**
 * This function is used to log a user into the website
 * @param $http {angular.$http}
 * @param $rootScope {{authenticated: boolean}}
 * @param credentials {{username: String, password: String}}
 * @param callback {function()}
 */
var authenticate = function($http, $rootScope, credentials, callback) {
    var headers = credentials ? {authorization : "Basic "
    + btoa(credentials.username + ":" + credentials.password)
    } : {};

    $http.get('user/getUser', {headers : headers}).success(function(/** User */ data) {
        $rootScope.authenticated = !!data;
        if ($rootScope.authenticated) {
            $rootScope.user = data;
            $rootScope.isAdmin = isAdmin(data.authorities);
        }
        callback && callback();
    }).error(function() {
        $rootScope.authenticated = false;
        callback && callback();
    });
};

/**
 * Determines if a user is an admin from their authorities
 * @param {Array.<authority>} authorities
 */
var isAdmin = function(authorities) {
    for (var i = 0; i < authorities.length; i++) {
        if (authorities[i].authority === "ROLE_ADMIN") {
            return true;
        }
    }
    return false;
};

/**
 * This function checks that the team details entered are valid and can be sent to the server
 * @param teamDetails {NewUserSubmission}
 * @param callback {function(String)} error callback
 * @returns {boolean} is the form data valid
 */
var formDataValid = function(teamDetails, callback) {
    if (!teamDetails.teamName || teamDetails.teamName.length == 0) {
        callback("Team name cannnot be empty.");
    }
    else if (!teamDetails.schoolName || teamDetails.schoolName.length == 0) {
        callback("School name cannot be empty.")
    }
    else if (!teamDetails.email || teamDetails.email.length == 0) {
        callback("Contact email cannot be empty.")
    }
    else if (!teamDetails.email || teamDetails.email.length == 0) {
        callback("Contact email cannot be empty.")
    }
    else if (!teamDetails.division || teamDetails.division === null) {
        callback("Division cannot be blank.");
    }
    else if (!teamDetails.password || teamDetails.password.length < 8) {
        callback("Password must be at least 8 characters.");
    }
    else if (teamDetails.password != teamDetails.repeat_password) {
        callback("Passwords do not match.");
    }
    else if (!teamDetails.member1Name || teamDetails.member1Name.length == 0) {
        callback("There must be at least one team member on the team.");
    }
    else {
        return true;
    }
    return false;
};

/**
 *
 * @param $http
 * @param teamDetails {NewUserSubmission}
 * @param success {function()} Called if this team can register
 * @param error {function(errorMessage: String)} Called if this team cannot register,
 */
var checkRegistration = function($http, teamDetails, success, error) {
    if (!formDataValid(teamDetails, error)) {
        return;
    }

    if (!teamDetails.member2Name || teamDetails.member2Name.length == 0) {
        teamDetails.member2Name = "";
    }
    if (!teamDetails.member3Name || teamDetails.member3Name.length == 0) {
        teamDetails.member3Name = "";
    }

    $http.get('user/userExists', {params: {teamName: teamDetails.teamName}})
        .success(function(/** boolean */ data) {
            if (data) {
                error("Team name already taken. Please try another.");
            }
            else {
                success();
            }
        })
        .error(function(data, status, headers, config) {
            error("Error attempting to register. Please try again later.")
        });
};

/**
 * Registers the team specified
 * @param $http
 * @param $rootScope {$rootScope}
 * @param $location {{path: function(String)}}
 * @param teamDetails {NewUserSubmission}
 * @param error {function(String)}
 */
var doRegister = function($http, $rootScope, $location, teamDetails, error) {
    $http.post('user/addUser', teamDetails)
        .success(function(data) {
            var loginCreds = {
                username: teamDetails.teamName,
                password: teamDetails.password
            };
            authenticate($http, $rootScope, loginCreds, function() {
                if ($rootScope.authenticated) {
                    $location.path("/");
                } else {
                    error("Error attempting to register. Please try again later.");
                }
            });
        })
        .error(function(data, status, headers, config) {
            error("Error attempting to register. Please try again later.")
        });
};