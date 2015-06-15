//remove tab ids used in sample comparison tool for smooth effect
$(function() {
  $('a[href*=#]:not([href=#])').not('#ui-id-1, #ui-id-2, #ui-id-3, #ui-id-4, #ui-id-5').click(function() {
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
