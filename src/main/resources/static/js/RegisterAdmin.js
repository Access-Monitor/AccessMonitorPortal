function toggleAlert(clasz, display , msg){
    $(".alert")
        .removeClass("display")
        .removeClass("alert-info")
        .removeClass("alert-success")
        .removeClass("alert-danger")
        .addClass(clasz);
    if(display){
        $(".alert").addClass("display")
    }
    if(clasz === "alert-success"){
        $(".alert > span").text(msg);

    }else if(clasz === "alert-danger"){
        $(".alert > span").text(msg);
    }
}

$(document).ready(function () {

    $("#reg-form").submit(function (event) {

        //stop submit the form, we will post it manually.
        event.preventDefault();

        if($("#password").val() == $("#repeat_password").val()) {
            fire_ajax_submit();
        }
        else{

            toggleAlert("alert-danger", true , "Le due password inserite non coincidono!");

            setTimeout(function(){
                toggleAlert("alert-danger");
            },2000);

        }



    });

});

function fire_ajax_submit() {

    let flag = true;
    var registration = {}

    registration["firstname"] = $("#firstname").val();

    if(!registration["firstname"].match(/^[a-zA-Z]+$/) ){
        toggleAlert("alert-danger", true , "Il nome può contenere solo caratteri");
        setTimeout(function(){
            toggleAlert("alert-danger");
        },2000);
        flag = false;
    }

    registration["lastname"] = $("#lastname").val();

    if(!registration["lastname"].match(/^[a-zA-Z]+$/) ){
        toggleAlert("alert-danger", true , "Il cognome può contenere solo caratteri");

        setTimeout(function(){
            toggleAlert("alert-danger");
        },2000);
        flag = false;
    }

    registration["email"] = $("#email").val();
    if(!registration["email"].match(/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w+)+$/ )){
        toggleAlert("alert-danger", true , "Inserisci una mail valida");

        setTimeout(function(){
            toggleAlert("alert-danger");
        },2000);
        flag = false;
    }

    registration["password"] = $("#password").val();
    registration["repeat_password"] = $("#repeat_password").val();

    if (registration["password"] != registration["repeat_password"]){
        toggleAlert("alert-danger", true , "Conferma correttamente la password");

        setTimeout(function(){
            toggleAlert("alert-danger");
        },2000);
        flag = false;
    }

    if(!registration["password"].match(/^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/ )){
        toggleAlert("alert-danger", true , "La password deve contenere almeno 8 caratteri, almeno una lettera ed un numero");

        setTimeout(function(){
            toggleAlert("alert-danger");
        },2000);
        flag = false;
    }


    $("#btn-search").prop("disabled", true);

    if (flag) {
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/addNewAdmin",
            data: JSON.stringify(registration),
            dataType: 'json',
            cache: false,
            timeout: 600000,
            success: function (data) {


                toggleAlert("alert-success", true, "Admin creato con successo !");

                setTimeout(function () {
                    toggleAlert("alert-success");
                }, 2000);


                $("#firstname").val("");
                $("#lastname").val("");
                $("#email").val("");
                $("#password").val("");
                $("#repeat_password").val("");

            },
            error: function (e) {

                const resp = JSON.parse(e.responseText);

                toggleAlert("alert-danger", true, resp["msg"]);

                setTimeout(function () {
                    toggleAlert("alert-danger");
                }, 2000);

            }
        });
    }
}