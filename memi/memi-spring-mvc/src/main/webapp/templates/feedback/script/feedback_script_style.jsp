<script type="text/javascript">
    $.fx.speeds._default = 1000;
    var validator;
    $(function() {
//        prepare options for AJAX submission
        var options = {
            type: "POST",
            url: $("#feedback_form").attr('action'),
            data: $("#feedback_form").serialize(),
            dataType: "json",
            success: function(data, textStatus, jqXHR) {
                var $pathName = window.location.pathname;
                var $pos = window.location.pathname.search("/metagenomics");
                window.location.href = window.location.pathname.substring(0, window.location.pathname.lastIndexOf('/') + 1) + "feedbackSuccess";
            },
            error: function(xhr, ajaxOptions, thrownError) {
                var jsonObject = jQuery.parseJSON(xhr.responseText);
                if (jsonObject != null) {
                    $("#addressErrorMessage").html(jsonObject.sender);
                    $("#subjectErrorMessage").html(jsonObject.emailSubject);
                    $("#msgError").html(jsonObject.message);
                }
            }
        };

//      Client side feedback form validation and submission. Please notice that there also exists a server side form validation
//      Be aware of the following issue: http://www.ozonesolutions.com/programming/2011/09/ajaxform-and-validation-plugins-for-jquery/
        validator = $('#feedback_form').validate(
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

        //Resets input fields to their original value (requires form plugin), removes classes indicating invalid elements and hides error messages.
        $('#resetFeedbackFrom').click(function() {
            validator.resetForm();
            return false;
        });
    })(jQuery);
</script>

<script type="text/javascript">
    //    Global variable to track display value of DIV with ID feedback_div (see ../feedback/feedbackDiv.html file)
    var feedbackDivDisplayValue;
    function showFeedbackForm() {
        if (feedbackDivDisplayValue == null) {
            var feedbackDivElement = document.getElementById("feedback_div");
            feedbackDivDisplayValue = feedbackDivElement.style.display;
        }
        if (feedbackDivDisplayValue == "none") {
            feedbackDivDisplayValue = "block";
            $("#feedback_div").show("slide", { direction: "right" }, 2000);
        }
        else {
            hideFeedbackForm();
        }
    }

    function hideFeedbackForm() {
        feedbackDivDisplayValue = "none";
        $("#feedback_div").hide("slide", { direction: "right" }, 2000);
        validator.resetForm();
    }
</script>
<style>
    #feedback_div {
        
        position: fixed;
        _position: absolute; /* position fixed is not recognise on IE6 */
        right: 0;
        top: 11px;
        width: 450px;
        height: 650px;
        border: 0px #dddddd solid;
        z-index: 90;
    }
</style>
<!-- Script and no script implementation for DIV with Id 'extra_feedback' (You'll find it in rootTemplate.jsp) -->
<script>
    document.write('<style type="text/css">#noscript_feedbackLink{display: none;}');
</script>
<noscript>
    <style type="text/css">
        #script_feedbackLink {
            display: none;
        }

        #feedback_div {
        }
    </style>
</noscript>