//author: Maxim Scheremetjew, EBI, InterPro
//This file keeps the code for the feedback dialog
//Client side and server side form validation is supported
var feedbackFormValidator;
$(function() {
//        prepare options for AJAX submission
    var options = {
        type: "POST",
        url: $("#feedback_form").attr('action'),
        data: $("#feedback_form").serialize(),
        dataType: 'html',
        success: function(data, textStatus, jqXHR) {
            var root = location.protocol + '//' + location.host;
            window.location.href = root + "/metagenomics/feedbackSuccess";
        },
        error: function(xhr, ajaxOptions, thrownError) {
            var jsonObject = jQuery.parseJSON(xhr.responseText);
            if (jsonObject != null) {
                $("#addressErrorMessage").html(jsonObject.emailAddress);
                $("#subjectErrorMessage").html(jsonObject.emailSubject);
                $("#msgError").html(jsonObject.message);
            }
        }
    };

//      Client side feedback form validation and submission. Please notice that there also exists a server side form validation
//      Be aware of the following issue: http://www.ozonesolutions.com/programming/2011/09/ajaxform-and-validation-plugins-for-jquery/
    feedbackFormValidator = $('#feedback_form').validate(
    {
        submitHandler: function(form) {
            // pass options to ajaxSubmit
            $(form).ajaxSubmit(options);
        },
        rules: {
            emailSubject: {
                required: true,
                minlength: 3,
                maxlength: 100
            },
            emailAddress: {
                required: true,
                email: true
            },
            emailMessage: {
                required: true,
                minlength: 3
            }
        },
        messages: {
            emailSubject:{
                required: "Please enter a feedback subject",
                minlength: jQuery.format("Feedback subject must be at least {0} characters long"),
                maxlength: jQuery.format("At most {0} characters are allowed")
            },
            emailAddress: {
                required: "Please enter a valid email address",
                email: "Your email address must be in the format of name@domain.com"
            },
            emailMessage: {
                required: "Please enter your feedback message",
                minlength: jQuery.format("Feedback message must be more than {0} characters")
            }
        }
    });
//      How JQuery form Plugin works: http://jquery.malsup.com/form/#getting-started
    $('#feedback_form').ajaxForm(options);


});
//Resets input fields to their original value (requires form plugin), removes classes indicating invalid elements and hides error messages.
function clearFeedbackForm() {
    feedbackFormValidator.resetForm();
    document.getElementById("feedbackForm_emailAddress").setAttribute("class", "");
    document.getElementById("feedbackForm_emailMessage").setAttribute("class", "");
    document.getElementById("feedbackForm_emailSubject").setAttribute("class", "");
}
var flag = true;
function hideFeedbackForm() {
//        var feedbackFormRightPosition = document.getElementById("feedback_div").style.right;
    $("#feedback_div").animate({
        right: '-=437px'
    }, 700);
    clearFeedbackForm();
}
//Shows feedback form
function showFeedbackForm() {
    $("#feedback_div").animate({
        right: '+=437px'
    }, 700);
}

function slideFeedbackForm() {
    if (flag) {
        flag = false;
        showFeedbackForm();
    }
    else {
        flag = true;
        hideFeedbackForm();
    }
}