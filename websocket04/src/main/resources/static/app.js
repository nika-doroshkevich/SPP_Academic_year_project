var stompClient = null;
var webSocketId = null;

function connectPrivate() {
    console.log("connectPrivate() 1");
    var socket = new SockJS('/emails-websocket');
    console.log("connectPrivate() 2");
    stompClient = Stomp.over(socket);
    console.log("connectPrivate() 3");
    console.log('connectPrivate() 3.1 ' + stompClient);
    stompClient.connect({}, function (frame) {
        console.log("connectPrivate() 4");
        console.log('connectPrivate() 5 ' + frame);
        console.log('connectPrivate() 5.1 ' + webSocketId);
        stompClient.subscribe('/user/' + webSocketId + '/topic/emails', function (email) {
            console.log("connectPrivate() 6");
            console.log('connectPrivate() 7 ' + email);
            console.log('connectPrivate() 8 ' + email.body);
            console.log('connectPrivate() 9 ' + JSON.parse(email.body).subject);
            var subject = JSON.parse(email.body).subject;
            console.log('connectPrivate() 10 ' + subject);
            showEmail(subject);
        });
    });
}

function disconnect() {
    console.log("disconnect() 1");
    console.log('disconnect() 2 ' + stompClient);
    if (stompClient !== null) {
        console.log('disconnect() 3 ' + stompClient);
        stompClient.disconnect();
        console.log('disconnect() 4 ' + stompClient);
    }
    console.log('disconnect() 5 ' + stompClient);
    stompClient = null
    console.log('disconnect() 6 ' + stompClient);
    console.log("Disconnected");
}

function sendEmailPrivate() {
    var subject = document.getElementById('subject').value;
    stompClient.send("/app/send-email-private/" + webSocketId,
        {},
        JSON.stringify({'subject': subject}));
}

function showEmail(subject) {
    console.log("showEmail() 1");
    console.log('showEmail() 2 ' + subject);
    $("#greetings").append("<tr><td>" + subject + "</td></tr>");
}

function createWebSocketConnection(firstUser, secondUser) {
    console.log('createWebSocketConnection() 1 ' + firstUser + ' ' + secondUser);
    fetch('/api/websockets/' + firstUser + '/' + secondUser)
        .then(response => response.text())
        .then((response) => {
            webSocketId = response;
            console.log('createWebSocketConnection() 2 ' + response);
        })
        .catch(err => console.log(err))
}