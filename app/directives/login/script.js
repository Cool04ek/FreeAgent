/**
 * Created by TITUS on 30.07.2015.
 */
define(['angularAMD', 'uiTranslations', 'jquery'], function (angularAMD, uiTranslations, $) {
    angularAMD.directive('dirLogin', function() {
        return {
            restrict: 'E',
            templateUrl: 'app/directives/login/template.html',
            replace: true,
            scope: true,
            controller: function($scope) {
                $scope.uiTranslations = uiTranslations[$scope.language].login;

                $('#btn_login').on('click', function(event) {
                    $.ajax({
                        url: 'j_spring_security_check',
                        method: 'POST',
                        data: $('#form_login').serialize(),
                        success: function(res, status, xhr) {
                            var isRegFailed = xhr.responseText.indexOf('<b class="error">Invalid login or password.</b>');

                            if (isRegFailed) {
                                alert('Login failed');
                            }
                        }
                    });
                });
            }
        };
    });
});