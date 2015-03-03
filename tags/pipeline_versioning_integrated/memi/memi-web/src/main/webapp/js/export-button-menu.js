$(".ui-button").click (function(){
        // auto-find the position of the menu when window resize
        $(".export_list").css({
                'position': 'absolute',
                'left': $(this).position().left,
                'top': $(this).position().top + $(this).height() + 2
            }).show();
    });
    $(document).mouseup(function (e)
    {
        var container = $(".export_list");

        if (!container.is(e.target) // if the target of the click isn't the container...  hide the container
            && container.has(e.target).length === 0) // ... nor a descendant of the container
        {
            container.hide();
        }
    });