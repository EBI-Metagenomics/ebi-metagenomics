$(function() {
    $("#datepicker").datepicker({
        showOn: 'button',
        buttonText: 'Choose a date',
        buttonImage: '/images/calendar.gif',
        buttonImageOnly: false,
        numberOfMonths: 1,
        maxDate: '+24m',
        minDate: '0d',
        showButtonPanel: true,
        format: 'd-m-Y'
    });
});