function connect() {
    console.log("connect() 1");
    var socket = new SockJS('/emails-websocket');
    console.log("connect() 2");
    stompClient = Stomp.over(socket);
    console.log("connect() 3");
    stompClient.connect({}, function(frame) {
        console.log("connect() 4");
        console.log('connect() 5 ' + frame);
        stompClient.subscribe('/topic/emails', function(email) {
            console.log("connect() 6");
            console.log('connect() 7 ' + email);
            console.log('connect() 8 ' + email.body);
            console.log('connect() 9 ' + JSON.parse(email.body).subject);
            var subject = JSON.parse(email.body).subject;
            console.log('connect() 10 ' + subject);
            showEmail(subject);
        });
    });
}

function sendEmail() {
    var subject = document.getElementById('subject').value;
    stompClient.send("/app/send-email",
        {},
        JSON.stringify({'subject':subject}));
}

function showEmail(subject) {
    console.log("showEmail() 1");
    console.log('showEmail() 2 ' + subject);
    $("#greetings").append("<tr><td>" + subject + "</td></tr>");
}