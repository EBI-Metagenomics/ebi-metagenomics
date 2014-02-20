$(function() {
    $("#datepicker").datepicker({
        showOn: 'button',
        buttonText: 'Choose a date',
        buttonImage: 'img/calendar.png',
        buttonImageOnly: true,
        numberOfMonths: 1,
        maxDate: '+24m',
        minDate: '0d',
        showButtonPanel: true,
        format: 'd-m-Y'
    });
});