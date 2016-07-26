var loginDialogValidator;
$(function() {
    var settings = {
        type: "POST",
        url: $("#login_form").attr('action'),
        data: $("#login_form").serialize(),
        dataType: 'html',
        success: function() {
            var root = location.protocol + '//' + location.host;
            window.location = root + "/metagenomics";
        },
        //The following part handles server side detected form validation errors
        error: function(xhr, ajaxOptions, thrownError) {
            var jsonObject = jQuery.parseJSON(xhr.responseText);
            if (jsonObject != null) {
                //Makes error message DIV block visible
                document.getElementById('loginDialog_userName.errors').style.display = "block";
                //Sets colour of input field to red, indicating an input error
                document.getElementById("loginDialog_userName").setAttribute("class", "error");
                document.getElementById("loginDialog_password").setAttribute("class", "error");
                //Sets error message of DIV block
                document.getElementById('loginDialog_userName.errors').innerHTML = jsonObject.password;
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
        },
        rules: {
            userName: {
                required: true
            },
            password: {
                required: true
            }
        },
        messages: {
            userName: {
                required: "Please enter your user name"
            },
            password: {
                required: "Please enter your password"
            }
        }
    });
    //How JQuery form Plugin works: http://jquery.malsup.com/form/#getting-started
    $('#login_form').ajaxForm(settings);
});