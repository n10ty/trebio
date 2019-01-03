$(function () {
    $('#datetimepicker1').datetimepicker({
        'timeZone': moment.tz.guess(),
        'format': 'D/MM/YYYY HH:m:ss Z'
    });
});