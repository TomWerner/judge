angular.module('app').controller('adminTeams',
    function($rootScope, $scope, $route, $http, $location) {
        $scope.$route = $route;

        if (!$rootScope.authenticated) {
            $location.path("/login");
        }

        $http.get('team/allTeams')
            .success(function(/** Array.<(Team)> */ data) {
                $scope.teams = data;
            })
            .error(function(data, status, headers, config) {
                console.log("Error trying to get all teams");
            });
    }
).controller('adminProblems',
    function($rootScope, $scope, $route, $http, $location) {
        $scope.$route = $route;

        $scope.problemDivision = "Division 1";
        if (!$rootScope.authenticated) {
            $location.path("/login");
        }

        $scope.getProblems = function() {
            $http.get('problem/allProblems')
                .success(function (/** Array.<(Problem)> */ data) {
                    $scope.problems = data;
                })
                .error(function (data, status, headers, config) {
                    console.log("Error trying to get all problems");
                });
        };
        $scope.getProblems();

        /**
         * @param {Problem} newProblem
         */
        $scope.addProblem = function(newProblem) {
            console.log(newProblem);
            $http.post('admin/problem/addProblem', newProblem).then(
                function() { // Success
                    $scope.getProblems();
                },
                function(data, status, headers, config) { // Error
                    console.log("Error saving problems");
                }
            );
        }
    }
).controller('adminProblem',
    function($rootScope, $scope, $route, $http, $location, $routeParams) {
        $scope.$route = $route;

        if (!$rootScope.authenticated) {
            $location.path("/login");
        }

        $http.get('problem/getProblem/' + $routeParams['problemId'])
            .success(function (/** Problem */ data) {
                $scope.problem = data;
                $scope.editor.setValue(data.problemBody);
                $scope.editor.clearSelection();

            })
            .error(function (data, status, headers, config) {
                console.log("Error trying to get the problem");
            }
        );

        /**
         * @param {Problem} problem
         */
        $scope.saveProblem = function(problem) {
            problem.problemBody = $scope.editor.getValue();
            $http.post('admin/problem/updateProblem/' + $routeParams['problemId'], problem).then(
                function() { /* Success */ },
                function(data, status, headers, config) { // Error
                    console.log("Error saving problem");
                }
            );
        };

        $scope.editor = ace.edit("editor");
        $scope.editor.setTheme("ace/theme/github");
        $scope.editor.getSession().setMode("ace/mode/text");
        $scope.editor.$blockScrolling = Infinity

    }
).controller('adminProblemTestCases',
    function($rootScope, $scope, $route, $http, $location, $routeParams) {
        $scope.$route = $route;

        if (!$rootScope.authenticated) {
            $location.path("/login");
        }

        $http.get('problem/getProblem/' + $routeParams['problemId'])
            .success(function (/** Problem */ data) {
                $scope.problem = data;
            })
            .error(function (data, status, headers, config) {
                console.log("Error trying to get the problem");
            }
        );
    }
);