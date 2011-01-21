//Login message dialog
//The message is trigger when someone tries to submit data when he is not logged in
$(document).ready(function() {
    $('#submittest').bind('click', function() {
        $('<div id="dialog-message" title="You are not logged in!"><p><span class="ui-icon ui-icon-circle-check" style="float:left; margin:0 7px 50px 0;"></span>Please log in before submitting data!</p><p>Thank you!</p></div>').appendTo('#submitdiv');

        $("#dialog:ui-dialog").dialog("destroy");

        $("#dialog-message").dialog({
            modal: true,
            buttons: {
                Ok: function() {
                    $(this).dialog("close");
                }
            }
        });
    });
});