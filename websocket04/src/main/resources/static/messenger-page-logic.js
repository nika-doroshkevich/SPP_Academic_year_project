let stompClient = null;
let webSocketId = null;
let listUsers = null;

function connectPrivate() {
    console.log("connectPrivate() 1");
    let socket = new SockJS('/emails-websocket');
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
            let subject = JSON.parse(email.body).subject;
            console.log('connectPrivate() 10 ' + subject);
            showOneMessage(subject);
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

function sendMessage() {
    console.log("sendMessage");
    let message = document.getElementById('message').value;
    console.log("message" + message);
    stompClient.send("/app/send-email-private/" + webSocketId,
        {},
        JSON.stringify({'subject': message}));
}

function showOneMessage(message) {
    console.log("showEmail() 1");

    const messageHtml = ` <li class="clearfix">
                            <div class="message-data">
                                <span class="message-data-time">10:12 AM, Today</span>
                            </div>
                            <div class="message my-message">${message}</div>
                            </li>`;

    let messages = document.querySelector('#messages');
    let li = document.createElement('li');
    li.innerHTML = messageHtml;
    messages.append(li);
}

async function createWebSocketConnection(firstUser, secondUser) {
    console.log('createWebSocketConnection() 1 ' + firstUser + ' ' + secondUser);
    await fetch('/api/websockets/' + firstUser + '/' + secondUser)
        .then(response => response.text())
        .then((response) => {
            webSocketId = response;
            console.log('createWebSocketConnection() 2 ' + response);
        })
        .catch(err => console.log(err))
}

async function showPreviousMessages() {
    let listMessages = null;
    console.log("showPreviousMessages() 1");
    console.log('showPreviousMessages() 2 ' + webSocketId);

    const response = await fetch('/api/emails/' + webSocketId, {
        method: 'GET',
        headers: {'Accept': 'application/json'}
    });

    if (response.ok === true) {
        listMessages = await response.json();
        //console.log(listMessages);
        listMessages.forEach(element => {
            let messageValue = element.message;
            //console.log(mess);
            showOneMessage(messageValue)
        });
    }
}

async function onLoadEvent() {

    const response = await fetch('/api/users', {
        method: 'GET',
        headers: {'Accept': 'application/json'}
    });

    if (response.ok === true) {
        listUsers = await response.json();
        //console.log("listUsers" + listUsers);
        listUsers.forEach(element => {
            //let userValue = element.username;
            //console.log("user " + userValue);
            getCard(element)
        });
    }
}

function getCard(user) {
    //console.log("user.username " + user.username);
    const userHtml = ` <li class="clearfix" onclick="connection(${user.id})">
                            <img src="https://bootdey.com/img/Content/avatar/avatar2.png" alt="avatar">
                                <div class="about">
                                <div class="name">${user.username}</div>
                                <div class="status"><i class="fa fa-circle online"></i> online</div>
                                </div>
                        </li>`;

    let users = document.querySelector('#users');
    let li = document.createElement('li');
    li.innerHTML = userHtml;
    users.append(li);
}

async function connection(selected) {
    console.log("selected " + selected);
    let connectedUsername = null;

    listUsers.forEach(element => {
        if (element.id === selected) {
            //console.log("element.id" + element.id);
            connectedUsername = element.username;
            //console.log("element.username " + element.username);
        }
    });
    $('#sendMessage').removeAttr('disabled');
    let currentUserUsername = $("#currentUserUsername").text();
    console.log("currentUserUsername " + currentUserUsername);
    disconnect();
    console.log("disconnect");
    await createWebSocketConnection(currentUserUsername, connectedUsername);
    console.log("createWebSocketConnection");
    connectPrivate();
    console.log("connectPrivate");
    await showPreviousMessages();
}