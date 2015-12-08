/**
 * Created by TITUS on 30.07.2015.
 */
define(
    [
        'angularAMD',

        'css!/app/directives/form-field/styles.css'
    ],
    function (angularAMD) {
        var ctrl =  [
            '$scope',
            '$attrs',
            '$element',
            function ($scope, $attrs, $element) {
                var validRegex = new RegExp($scope.config.pattern);
                var validate = function() {
                    var isPatternValid = true;
                    var isFunctionValid = true;

                    if ($scope.config.pattern) {
                        isPatternValid = validRegex.test($scope.config.value || '');
                    }

                    if (typeof $scope.config.validation === 'function') {
                        isFunctionValid = $scope.config.validation();
                    }

                    $scope.isValid = isFunctionValid && isPatternValid;
                    $scope.$emit('form-field-validated', {
                        result: $scope.isValid,
                        element: $element
                    });
                };

                angular.extend($scope, {
                    isValid: true,
                    isFocused: false
                });

                $element.find('input').on('focus', function() {
                    $scope.isFocused = true;
                    $scope.$apply();
                }).on('blur', function() {
                    $scope.isFocused = false;
                    validate();
                    $scope.$apply();
                }).on('change', function() {
                    $scope.isChanged = true;
                });

                $scope.$root.$on('validate-form', function(evt) {
                    $scope.isChanged = true;
                    validate();
                    evt.targetScope.validationResults.push($scope.isValid.toString());
                });
            }
        ];

        angularAMD.directive('dirFormField', [function () {
            return {
                restrict: 'E',
                templateUrl: 'app/directives/form-field/template.html',
                replace: true,
                scope: {
                    config: '=fieldConfig'
                },
                controller: ctrl
            };
        }]);
    }
);