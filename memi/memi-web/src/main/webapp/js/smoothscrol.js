
$(function() {
  $('a[href*=#]:not([href=#])').click(function() {
    if (location.pathname.replace(/^\//,'') == this.pathname.replace(/^\//,'') && location.hostname == this.hostname) {
      var target = $(this.hash);
      target = target.length ? target : $('[name=' + this.hash.slice(1) +']');
      if (target.length) {
        $('html,body').animate({
          scrollTop: target.offset().top
        }, 1000);
        return false;
      }
    }
  });
});
// previous script - temp - to delete
//window.$j = window.$ = jQuery;
//  $.browser.old_ie = $.browser.msie && $.browser.version < 8;
//  $j(document).ready(function() {
//  // jQuery SmoothScroll | Version 10-04-30
//  $('a[href*=#top], a[href*=#what_about], a[href*=#resources], a[href*=#resources_1], a[href*=#resources_2], a[href*=#features], a[href*=#features_1], a[href*=#features_2], a[href*=#features_3], a[href*=#p_features], a[href*=#funding], a[href*=#mail], a[href*=#credits], a[href*=#registration], a[href*=#format], a[href*=#prepublication], a[href*=#analysis], a[href*=#h_cite], a[href*=#submit]').click(function() {
//
//   // duration in ms
//   var duration=1000;
//
//   // easing values: swing | linear
//   var easing='swing';
//
//   // get / set parameters
//   var newHash=this.hash;
//   var target=$(this.hash).offset().top;
//   var oldLocation=window.location.href.replace(window.location.hash, '');
//   var newLocation=this;
//
//   // make sure it's the same location
//   if(oldLocation+newHash==newLocation)
//   {
//      // set selector
//      if($.browser.safari) var animationSelector='body:not(:animated)';
//      else var animationSelector='html:not(:animated)';
//
//      // animate to target and set the hash to the window.location after the animation
//      $(animationSelector).animate({ scrollTop: target }, duration, easing, function() {
//
//         // add new hash to the browser location
//         window.location.href=newLocation;
//      });
//
//      // cancel default click action
//      return false;
//   }
//});
//     });