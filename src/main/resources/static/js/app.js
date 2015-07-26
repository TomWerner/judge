angular.module('app', [ 'ngRoute' ])
    .config(['$routeProvider', '$httpProvider', function($routeProvider, $httpProvider) {

        $routeProvider.when('/', {
            templateUrl: 'html/home.html',
            controller: 'home',
            activeTab: 'home'
        }).when('/login', {
            templateUrl: 'html/login.html',
            controller: 'navigation',
            activeTab: 'login'
        }).when('/register', {
            templateUrl: 'html/register.html',
            controller: 'navigation',
            activeTab: 'register'
        }).when('/confirm/:userId/:uuid', {
            templateUrl: 'html/confirmation.html',
            controller: 'confirmation'
        }).when('/admin', {
            templateUrl: 'html/adminTeams.html',
            controller: 'adminTeams',
            activeTab: 'admin',
            activeSubTab: 'adminTeams'
        }).when('/admin/problems', {
            templateUrl: 'html/adminProblems.html',
            controller: 'adminProblems',
            activeTab: 'admin',
            activeSubTab: 'adminProblems'
        }).when('/admin/problem/:problemId', {
            templateUrl: 'html/adminProblem.html',
            controller: 'adminProblem',
            activeTab: 'admin',
            activeSubTab: 'adminProblems',
            activeSubSubTab: 'problem'
        }).when('/admin/problem/:problemId/testCases', {
            templateUrl: 'html/adminProblemTestCases.html',
            controller: 'adminProblemTestCases',
            activeTab: 'admin',
            activeSubTab: 'adminProblems',
            activeSubSubTab: 'testCases'
        }).otherwise('/');

        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
    }])
    .controller('home', function($scope, $http) {
        $http.get('/resource/').success(function(data) {
            $scope.greeting = data;
        })
    });

/**
 * @typedef {{
        authenticated: (Boolean|boolean),
        user: (User),
        isAdmin: (Boolean|boolean)
    }} $rootScope
 */
var $rootScope;

/**
 * @typedef {Object} User
 * @property {Array.<authority>} authorities
 * @property {Boolean|boolean} authenticated
 * @property {String|string} name
 */
var User;

/**
 * @typedef {Object} authority
 * @property {String|string} authority
 */
var authority;

/**
 * @typedef NewUserSubmission
 * @property teamName {String}
 * @property schoolName {String}
 * @property email {String}
 * @property division {String}
 * @property member1Name {String}
 * @property member2Name {?String}
 * @property member3Name {?String}
 * @property password {String}
 * @property repeat_password {String}
 */
var NewUserSubmission;

/**
 * @typedef Team
 * @property {String|string} teamName
 * @property {String|string} school
 * @property {String|string} email
 * @property {TeamLevel} teamLevel
 * @property {Number|number} correctAnswers
 * @property {Number|number} incorrectSubmissions
 */
var Team;

/**
 * @enum {String} TeamLevel
 */
var TeamLevel = {
    DIVISION_1: "Division 1 (AP)",
    DIVISION_2: "Division 2 (No AP)"
};

/**
 * @typedef {{
        id: (Number|number),
        division: (TeamLevel),
        problemTitle: (String|string),
        problemBody: (String|string)
 * }} Problem
 */
var Problem;