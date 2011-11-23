var dialog;
var loginDialogValidator;
$(function() {
    dialog =
            $("#login_dialog_div").dialog({
                autoOpen: false, // Avoids auto open after a new dialog instance is being created
                title: 'Login for EBI Metagenomics', //Dialog title
                modal: true, //Dialog has modal behavior; other items on the page are disabled
                closeOnEscape: true, //Dialog closes when it has focus and the user presses the esacpe (ESC) key
                height: 400,
                width: 730,
                beforeClose: function(event, ui) {
                    $("#errorMessage").html("");
                }
            });

    //Close dialog by mouse click in the overlay area
    $('.ui-widget-overlay').live("click", function() {
        dialog.dialog("close");
    });

    var settings = {
        type: "POST",
        url: $("#login_form").attr('action'),
        data: $("#login_form").serialize(),
        dataType: 'html',
        success: function() {
            window.location = self.location;
        },
        //The following part handles server side detected form validation errors
        error: function(xhr, ajaxOptions, thrownError) {
            var jsonObject = jQuery.parseJSON(xhr.responseText);
            if (jsonObject != null) {
                //Makes error message DIV block visible
                document.getElementById('loginDialog_emailAddress.errors').style.display = "block";
                //Sets colour of input field to red, indicating an input error
                document.getElementById("loginDialog_emailAddress").setAttribute("class", "error");
                document.getElementById("loginDialog_password").setAttribute("class", "error");
                //Sets error message of DIV block
                document.getElementById('loginDialog_emailAddress.errors').innerHTML = jsonObject.password;
            }
        }
    };

    //Client side feedback form validation and submission. Please notice that there also exists a server side form validation
    //Be aware of the following issue: http://www.ozonesolutions.com/programming/2011/09/ajaxform-and-validation-plugins-for-jquery/
    loginDialogValidator = $('#login_form').validate(
    {
        submitHandler: function(form) {
            // pass options to ajaxSubmit
            $(form).ajaxSubmit(settings);
            return false;
        },
        rules: {
            emailAddress: {
                required: true,
                email: true
            },
            password: {
                required: true
            }
        },
        messages: {
            emailAddress: {
                required: "Please enter a valid email address",
                email: "Your email address must be in the format of name@domain.com"
            },
            password: {
                required: "Please enter your password"
            }
        }
    });
    //How JQuery form Plugin works: http://jquery.malsup.com/form/#getting-started
    $('#login_form').ajaxForm(settings);
});
//Opens the dialog
function openLoginDialogForm() {
    loginDialogValidator.resetForm();
    dialog.dialog("open");
}
//Closes the dialog
function closeLoginDialogForm() {
    dialog.dialog("close");
}