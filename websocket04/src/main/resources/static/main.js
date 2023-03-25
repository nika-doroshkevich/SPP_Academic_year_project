$("#dropDownList").change(async function() {
    // var selectedVal = this.value;
    let selectedVal = $('#dropDownList').val();
    console.log('dropDownList() 1 ' + selectedVal);
    let selectedTest = $('#dropDownList :selected').text();
    console.log('dropDownList() 2 ' + selectedTest);
    if (selectedVal > 0) {
        $('#sendEmailPrivate').removeAttr('disabled');
        let currentUserUsername = $("#currentUserUsername").text();
        console.log('dropDownList() 3 ' + currentUserUsername);
        disconnect();
        await createWebSocketConnection(currentUserUsername, selectedTest);
        console.log('dropDownList() 4 ');
        connectPrivate();
        console.log('dropDownList() 5 ');
        await showPreviousMessages();
    } else {
        disconnect();
        $('#sendEmailPrivate').attr('disabled','disabled');
    }
});