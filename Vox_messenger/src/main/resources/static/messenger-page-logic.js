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
            let sendingTime = JSON.parse(email.body).sendingTime;
            let senderUserId = JSON.parse(email.body).senderUserId;
            console.log('connectPrivate() 10 ' + subject);
            let currentUserId = $("#currentUserId").text();
            showOneMessage(subject, currentUserId, sendingTime, senderUserId);
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
    document.getElementById('message').value = "";
    console.log("message" + message);
    if (message != null || message !== "") {
        let currentUserId = $("#currentUserId").text();
        console.log("currentUsername" + currentUserId);
        stompClient.send("/app/send-email-private/" + webSocketId + "/" + currentUserId,
            {},
            JSON.stringify({'subject': message, 'sendingTime': null, 'senderUserId': null}));
    }
}

function showOneMessage(message, userId, sendingTime, senderUserId) {
    let messageHtml;
    let currentUserId = $("#currentUserId").text();

    let timeWithoutSeconds = sendingTime.substr(0, 5);

    //console.log("currentUserId " + currentUserId);
    //console.log("userId " + userId);
    //console.log("senderUserId " + senderUserId);

    let currentUserIdText = "" + currentUserId;
    let senderUserIdText = "" + senderUserId;

    if (senderUserId === null || currentUserIdText === senderUserIdText) {
        if (userId === currentUserId) {
            messageHtml = `<li class="clearfix message-item">
                            <div class="message-data text-right" >
                                <span class="message-data-time">${timeWithoutSeconds}</span>
                            </div>
                            <div class="message other-message float-right">${message}</div>
                       </li>`;
        } else {
            messageHtml = `<li class="clearfix message-item">
                            <div class="message-data">
                                <span class="message-data-time">${timeWithoutSeconds}</span>
                            </div>
                            <div class="message my-message">${message}</div>
                       </li>`;
        }
    } else {
        if (userId === currentUserId) {
            messageHtml = `<li class="clearfix message-item">
                            <div class="message-data">
                                <span class="message-data-time">${timeWithoutSeconds}</span>
                            </div>
                            <div class="message my-message">${message}</div>
                       </li>`;
        } else {
            messageHtml = `<li class="clearfix message-item">
                            <div class="message-data text-right" >
                                <span class="message-data-time">${timeWithoutSeconds}</span>
                            </div>
                            <div class="message other-message float-right">${message}</div>
                       </li>`;
        }
    }

    let messages = document.querySelector('#messages');
    let li = document.createElement('li');
    li.innerHTML = messageHtml;
    messages.append(li);

    let objDiv = document.getElementById("chatHistory");
    objDiv.scrollTop = objDiv.scrollHeight;
}

function cleanPreviousMessages() {
    console.log("clean");

    document.querySelectorAll('.message-item')
        .forEach(element => element.remove());
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
    cleanPreviousMessages();
    let listMessages = null;
    console.log("showPreviousMessages() 1");
    console.log('showPreviousMessages() 2 ' + webSocketId);

    const response = await fetch('/api/emails/' + webSocketId, {
        method: 'GET',
        headers: {'Accept': 'application/json'}
    });

    if (response.ok === true) {
        listMessages = await response.json();
        console.log("listMessages: ");
        console.log(listMessages);
        listMessages.forEach(element => {
            let messageValue = element.message;
            let userIdValue = element.userId;
            let sendingTimeValue = element.sendingTime;
            //console.log("sendingTimeValue: " + sendingTimeValue);
            showOneMessage(messageValue, userIdValue, sendingTimeValue, null);
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
        listUsers.forEach(element => {
            showOneUser(element);
        });
    }
}

function cleanDisplayingUsersList() {
     document.querySelectorAll('.user-item')
         .forEach(element => element.remove());
}

function showOneUser(user) {
    const userHtml = ` <li class="clearfix user-item" onclick="connection(${user.id})">
                            <img src="${user.avatarImage}"/>
                                <div class="about">
                                <div class="name">${user.username}</div>
                                <div class="status"><i class="fa fa-circle online"></i> online</div>
                                </div>
                        </li>`;

    let users = document.querySelector('#users');
    let div = document.createElement('div');
    div.innerHTML = userHtml;
    users.append(div);
}

function searchUsers() {
    let query = document.getElementById('search').value;

    if (query != null || query !== "") {
        cleanDisplayingUsersList();
        listUsers.forEach(element => {
            if (element.username.toLowerCase().includes(query.toLowerCase())) {
                showOneUser(element);
            }
        });
    }
}

function showInfoAboutUser(username) {
    document.getElementById('infoUsername').textContent = username;

    listUsers.forEach(element => {
        if (element.username === username) {
            document.getElementById("image-container").src = element.avatarImage;
        }
    });
}

async function connection(selected) {
    console.log("selected " + selected);
    let connectedUsername = null;

    listUsers.forEach(element => {
        if (element.id === selected) {
            connectedUsername = element.username;
        }
    });
    $('#sendMessage').removeAttr('disabled');
    let currentUserUsername = $("#currentUserUsername").text();
    disconnect();
    console.log("disconnect");
    await createWebSocketConnection(currentUserUsername, connectedUsername);
    console.log("createWebSocketConnection");
    connectPrivate();
    console.log("connectPrivate");
    showInfoAboutUser(connectedUsername);
    await showPreviousMessages();
}