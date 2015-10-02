/**
 * Created by TITUS on 28.09.2015.
 */
define([
        'angularAMD',
        'resources/ui-translations',

        'services/edit-user-info',
        'services/user-delete'
    ],
    function (angularAMD, uiTranslations) {
        angularAMD.directive('dirEditProfile', function () {
            return {
                restrict: 'E',
                templateUrl: 'app/directives/edit-profile/template.html',
                replace: true,
                scope: true,
                controller: controller
            };
        });

        var controller = ['$scope', 'editUserInfo', 'deleteUser', function ($scope, editUserInfo, deleteUser) {
            if (!$scope.$root.isLoggedIn) {
                location.assign('/');
                return;
            }

            $scope.uiTranslations = uiTranslations[$scope.language].registration;

            $scope.formData = {};

            $scope.error = '';

            $scope.submitHandler = function() {
                var data = $scope.$root.toolkit.serialize($scope.formData);
                $scope.error = '';

                editUserInfo(data, $scope.currUserData.id)
                    .success(function(data) {
                        if (typeof data !== 'object') {
                            $scope.error = 'Something wrong with response';
                            return;
                        }

                        if (data.error === true) {
                            $scope.error = 'User data edit error. Code: ' + data.status;
                            return;
                        }

                        location.assign('/profile')
                    })
                    .error(function(err) {
                        $scope.error = 'Request failed';
                    })
            };

            $scope.deleteProfile = function() {
                deleteUser($scope.currUserData.id)
                    .success(function(data) {
                        if (typeof data !== 'object') {
                            $scope.error = 'Something wrong with response';
                            return;
                        }

                        if (data.error === true) {
                            $scope.error = 'User delete error. Code: ' + data.status;
                            return;
                        }

                        location.assign('/profile')
                    })
                    .error(function(err) {
                        $scope.error = 'Request failed';
                    })
            }
        }];
    }
);

