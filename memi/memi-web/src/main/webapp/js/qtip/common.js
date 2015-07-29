$(document).ready(function() {
    $('.show_tooltip').qtip({
        position: {
            my: 'top center',  // Position my top left...
            at: 'bottom center' // at the bottom right of...

        }});
    // Qtip2 popups (used on all pages of I5 web)
    $('a[title]').qtip({
        position: {
            viewport: $(window), // Keep the tooltip on-screen at all times
            my: 'top center',  // Position my top left...
            at: 'bottom center' // at the bottom right of...
        }
    });
    $('img[title]').qtip({
        position: {
            viewport: $(window) // Keep the tooltip on-screen at all times
        }
    });
    $('abbr[title]').qtip({
        position: {
            viewport: $(window) // Keep the tooltip on-screen at all times
        }
    });
});
