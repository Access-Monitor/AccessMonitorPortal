var borderOk= '3px solid #080';
var borderNo='3px  solid #f00';
var firstnameOk= false;
var lastnameOk= false;
var telefonoOK=false;
var emailOk=false;
var roleOk =false;


function validaEmail() {
    var input = document.forms['addNewMember']['email'];
    if (input.value.match(/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w+)+$/)) {
        var xmlHttpReq = new XMLHttpRequest();
        xmlHttpReq.onreadystatechange = function() {

            if(this.responseText != "")
            var risp = JSON.parse(this.responseText)["msg"];


            if (this.readyState == 4 && this.status == 200
                && risp == '<ok/>') {
                emailOk = true;
                input.style.border = borderOk;
            } else {
                if (this.readyState == 4 && this.status == 200
                    && risp == 'Email già utilizzata!') {
                    input.style.border = borderNo;
                    emailOk = false;
                }
            }
            buttonState();
        }

        xmlHttpReq.open("GET", "/VerificaEmail?email="
            + encodeURIComponent(input.value), true);
        xmlHttpReq.send();
    } else {
        input.style.border = borderNo;
        emailOk = false;
    }
    buttonState();
}

function validaNumero() {
    var input= document.forms['addNewMember']['phone'];
    if(input.value.length==10 && input.value.match(/^[0-9]+$/)){
        telefonoOK=true;
        input.style.border = borderOk;
        buttonState();
    }
    else{
        input.style.border = borderNo;
        telefonoOK=false
        buttonState();
    }

}


function validaNome() {
    var input= document.forms['addNewMember']['firstname'];
    if(input.value.length>=3 && input.value.match(/^[a-zA-Z]+$/)){
        firstnameOk=true;
        input.style.border = borderOk;
        buttonState();
    }
    else{
        input.style.border = borderNo;
        firstnameOk=false
        buttonState();
    }

}

function validaCognome() {
    var input= document.forms['addNewMember']['lastname'];
    if(input.value.length>=3 && input.value.match(/^[a-zA-Z]+$/)){
        lastnameOk=true;
        input.style.border= borderOk;
        buttonState();
    }
    else{
        input.style.border= borderNo;
        lastnameOk=false;
        buttonState();
    }

}


function validaRuolo() {
    var input= document.forms['addNewMember']['role'];
    if(input.value.length>=2 && input.value.match(/^[a-zA-Z]+$/)){
        roleOk=true;
        input.style.border= borderOk;
        buttonState();
    }
    else{
        input.style.border= borderNo;
        roleOk=false;
        buttonState();
    }

}

function buttonState() {
    if (firstnameOk && roleOk && lastnameOk && emailOk && telefonoOK ) {
        document.getElementById('save').disabled = false;
    } else {
        document.getElementById('save').disabled = true;
    }
}

$("#cancel").click(function (event) {
    $("#firstname").val("");
    $("#lastname").val("");
    $("#role").val("");
    $("#phone").val("");
    $("#email").val("");

    $(':text').each(function () {
        $(this).css("border", "1px solid grey");
    })

    $('#email').each(function () {
        $(this).css("border", "1px solid grey");
    })

    $('#phone').each(function () {
        $(this).css("border", "1px solid grey");
    })


})
