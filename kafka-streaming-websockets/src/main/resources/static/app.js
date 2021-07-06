var stompClient = null;
var countMessages = 0;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#stream").html("");
}

function connect() {
    var socket = new SockJS('/jdisp-websocket-endpoint');
    stompClient = Stomp.over(socket);
    stompClient.heartbeat.outgoing = 5000
    stompClient.heartbeat.incoming = 5000
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/dashboard/stream-in-prod', function (message) {
            showMessage(message.body);
        });
        stompClient.subscribe('/dashboard/stream-out-prod', function (message) {
            addDataToChart(message.body);
        });
        stompClient.subscribe('/dashboard/stream-in-cons', function (message) {
            showMessage(message.body);
        });
        stompClient.subscribe('/dashboard/stream-out-cons', function (message) {
            addDataToChartConsumtion(message.body);
        });
        stompClient.subscribe('/dashboard/stream-in-humidity', function (message) {
            showMessage(message.body);
        });
        stompClient.subscribe('/dashboard/stream-out-humidity', function (message) {
            addDataToChartHumidity(message.body);
        });
        stompClient.subscribe('/dashboard/stream-in-humidity-min', function (message) {
            showMessage(message.body);
        });
        stompClient.subscribe('/dashboard/stream-out-humidity-min', function (message) {
            addDataToChartHumidityMin(message.body);
        });
        stompClient.subscribe('/dashboard/stream-in-humidity-average', function (message) {
            showMessage(message.body);
        });
        stompClient.subscribe('/dashboard/stream-out-humidity-average', function (message) {
            addDataToChartHumidityAverage(message.body);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function showMessage(message) {

    countMessages++;
    $("#stream").append("<tr><td>" + message + "</td></tr>");
    if(countMessages > 10){
        // console.log(countMessages);
        $("#stream tr:first").remove();
    }
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
});

