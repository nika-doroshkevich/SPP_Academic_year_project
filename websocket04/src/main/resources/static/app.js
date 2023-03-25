var stompClient = null;
var webSocketId = null;

function connectPrivate() {
    console.log("connectPrivate() 1");
    var socket = new SockJS('/emails-websocket');
    console.log("connectPrivate() 2");
    stompClient = Stomp.over(socket);
    console.log("connectPrivate() 3");
    stompClient.connect({}, function (frame) {
        console.log("connectPrivate() 4");
        console.log('connectPrivate() 5 ' + frame);
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