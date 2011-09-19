$(document).ready(function() {
    var $dialog = $('<div></div>')
            .html
            ('<div class="sub">' +
                    '<div class="sub_log">' +
                    '<form>' +
                    '<fieldset>' +
                    '<div class="form_row"><h3>Login</h3></div>' +
                    '<div class="form_row">' +
                    '<label for="email">E-Mail:</label>' +
                    '<br/>' +
                    '<input type="text" name="emailAddress" id="emailAddress" style="width:313px;" />' +
                    '</div>' +
                    '<div class="form_row">' +
                    '<label for="password">Password:</label>' +
                    '<br/>' +
                    '<input type="password" name="password" id="password" style="width:313px;" />' +
                    '</div>' +
                    '<div><a href=""  title="Request a new password">Forgot your password?</a></div>' +
                    '<div class="form_row_log">' +
                    '<input type="submit" name="login" value="Login" class="main_button"/>&nbsp;' +
                    '<span class="clear_but" style="float:none;">| <a href="/metagenomics" title="cancel">Cancel</a></span>' +
                    '</div>' +
                    '</fieldset>' +
                    '</form>' +
                    '</div>' +
                    '</div>' +
                    '</div>')
            .dialog({
                        autoOpen: false, // Avoids auto open after a new dialog instance is being created
                        title: 'JQuery Login Dialog', //Dialog title
                        modal: true, //Dialog has modal behavior; other items on the page are disabled
                        closeOnEscape: true, //Dialog closes when it has focus and the user presses the esacpe (ESC) key
                        height: 380,
                        width: 410
                    });

    $('#jqueryLogin').click(function() {
        $dialog.dialog('open');
        // prevent the default action, e.g., following a link
        return false;
    });
    $('.ui-widget-overlay').live("click", function() {
        //Close the dialog
        $dialog.dialog("close");
    });
});