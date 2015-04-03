'use strict';

egdApp.directive('egdQtip', function ($translate, $log, $templateCache, $compile, $http) {
    return {
        restrict: 'E',
        scope: {
            content: '@content',
            url: '@url'
        },
        replace: true,

        template: '<a href="javascript:void(0)"><img src="/assets/images/info.gif" alt="Info"/></a>',
        link: function (scope, element, attrs) {
            var createQtip = function() {
                var my = attrs.my || 'bottom left' // Position the tooltip above the link
                    , at = attrs.at || 'top right'
                    , qtipClass = attrs.class || 'qtip-dark ui-tooltip-tipsy';

                var qTipGlobalOptions = {
                    content:{
                        text:null,
                        title:{
                            text:' ',
                            button:true
                        }
                    },
                    position:{
                        at:at,
                        my:my,
                        viewport:$(window), // Keep the tooltip on-screen at all times
                        effect:false, // Disable positioning animation,
                        target: element
                    },
                    style:{
                        classes:qtipClass
                    },

                    show:{
                        ready:true,
                        event:false,
                        solo:true // Only show one tooltip at a time
                    },
                    hide:false
                };

                var target = $(element);

                if (scope.url) {

                    $http.get(scope.url).success(function(template) {
                        qTipGlobalOptions.content.text = $compile(template)(scope);
                        target.qtip(qTipGlobalOptions);
                        showQtip(target);

                    });
                } else {
                    qTipGlobalOptions.content.text = scope.content;
                    target.qtip(qTipGlobalOptions);
                    showQtip(target);
                }
            };

            var showQtip = function(element) {
                $log.debug("egdQtip.showQtip");
                element.qtip('show');
                element.data('visible', true);
            };

            var hideQtip = function(element) {
                $log.debug("egdQtip.hideQtip");
                element.qtip('hide');
                element.data('visible', false);
            };

            var toggleQtip = function(element) {
                if (element.data('visible')) {
                    hideQtip(element);
                } else {
                    showQtip(element);
                }
            };

            element.bind("click",function() {
                var target = $(element);
                if('object' === typeof target.data('qtip')) { //check if it has qtip class on them
                    toggleQtip(target);
                } else {
                    createQtip();
                }
            });
        }
    }
}).directive('qtip', function ($translate) {
    return {
        restrict: 'A',
        //scope: {content: '@'},
        //replace: true,
        //template: '<img src="images/info.gif" data-content="{{content}}"/>',
        link: function (scope, element, attrs) {
            var my = attrs.my || 'bottom left' // Position the tooltip above the link
                , at = attrs.at || 'top right'
                , qtipClass = attrs.class || 'qtip-dark ui-tooltip-tipsy';

            var qTipGlobalOptions = {
                content:{
                    text:attrs.content,
                    title:{
                        text:' ',
                        button:true
                    }
                },
                position:{
                    at:at,
                    my:my,
                    viewport:$(window), // Keep the tooltip on-screen at all times
                    effect:false, // Disable positioning animation,
                    target: element
                },
                style:{
                    classes:qtipClass
                },

                show:{
                    ready:true,
                    event:false,
                    solo:true // Only show one tooltip at a time
                },
                hide:false
            };

            $(element).qtip(qTipGlobalOptions);

        }
    }
});
