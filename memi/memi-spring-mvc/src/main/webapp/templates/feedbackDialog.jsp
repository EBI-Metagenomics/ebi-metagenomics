<script type="text/javascript">
    $.fx.speeds._default = 1000;
    $(function() {
//        Global variables
        var $feedbackForm = $("#feedback_form");
        var $feedbackDiv = $("#feedback_div");

//        prepare options for AJAX submission
        var options = {
            type: "POST",
            url: $("#feedback_form").attr('action'),
            data: $("#feedback_form").serialize(),
            dataType: "json",
            success: function(data, textStatus, jqXHR) {
                window.location = self.location;
            },
            error: function(xhr, ajaxOptions, thrownError) {
                cleanUpErrorDivs();
                var jsonObject = jQuery.parseJSON(xhr.responseText);
                if (jsonObject != null) {
                    $("#addressErrorMessage").html(jsonObject.sender);
                    $("#subjectErrorMessage").html(jsonObject.emailSubject);
                    $("#msgError").html(jsonObject.message);
                }
            }
        };
        <%-- Client side feedback form validation and submission. Please notice that there also exists a server side form validation--%>
        <%--Be aware of the following issue: http://www.ozonesolutions.com/programming/2011/09/ajaxform-and-validation-plugins-for-jquery/--%>
        $('#feedback_form').validate(
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
        <%--Link to how JQuery form Plugin works: http://jquery.malsup.com/form/#getting-started--%>
        $('#feedback_form').ajaxForm(options);

        <%--Feedback dialog specification--%>
        var $dialog =
                $feedbackDiv.dialog({
                    autoOpen: false, // Avoids auto open after a new dialog instance is being created
                    title: 'JQuery Feedback Dialog', //Dialog title
                    closeOnEscape: true, //Dialog closes when it has focus and the user presses the esacpe (ESC) key
                    height: 750,
                    width: 700,
                    buttons: { "Close": function() {
                        $(this).dialog("close");
                    }},
                    show: 'slide',
//                    hide: "slide",
                    position: 'right',
                    draggable: false
//                    beforeClose: function(event, ui) {
//                        $("#errorMessage").html("");
//                    }
                })

        //Open dialog command by clicking link with Id jqueryLogin
        $("#jqueryFeedback").click(function() {
            if ($dialog.dialog("isOpen")) {
                $dialog.dialog("close");
            }
            else {
                $feedbackForm.clearForm();
                $dialog.dialog("open");
//                jQuery(this).show("slide", { direction: "right" }, 1000);
//                $dialog.show("slide", { direction: "right" }, 1000);
            }
            return false;
        });


        $('#clearFeedbackFrom').click(function() {
            $feedbackForm.clearForm();
            return false;
        });

        function cleanUpErrorDivs() {
            $("#addressErrorMessage").html("");
            $("#subjectErrorMessage").html("");
            $("#msgError").html("");
        }
    });
</script>

<%--Feedback DIV--%>
<div id="feedback_div">
    <h3>Beta Website Feedback</h3>

    <p>If you have feedback about this new website, please use the form below to send us your comments:</p>


    <form id="feedback_form" action="doFeedback" method="post">
        <div class="feedbackform_tab"></div>
        <fieldset>
            <table class="result" id="contact">
                <tr>
                    <td>
                        E-Mail address
                        <small>*</small>
                        : <input type="text" name="emailAddress" id="emailAddress"
                                 style="margin-left:14px; width:284px;"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div id="addressErrorMessage" class="error"></div>
                    </td>
                </tr>
                <tr>
                    <td>Feedback subject
                        <small>*</small>
                        : <input type="text" name="emailSubject" id="emailSubject" style="width:284px;"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div id="subjectErrorMessage" class="error"></div>
                    </td>
                </tr>
                <tr>
                    <td>Your comments
                        <small>*</small>
                        :<br/><textarea id="emailMessage" name="emailMessage" cols="50" rows="10"></textarea></td>
                </tr>
                <tr>
                    <td>
                        <div id="msgError" class="error"></div>
                    </td>
                </tr>
                <input type="hidden" name="leaveIt" id="leaveIt" style="margin-left:14px; width:284px;"/>
                <tr>
                    <td>
                        <input type="submit" name="submit" value="Submit" class="main_button"/>
                    <span class="clear_but" style="float:none;">| <a id="clearFeedbackFrom" href=""
                                                                     title="Clear">Clear</a></span>
                    </td>
                </tr>
            </table>
        </fieldset>
        <small>* Required</small>
    </form>
</div>