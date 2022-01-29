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
            },1000);

        }



    });

});

function fire_ajax_submit() {

    var registration = {}

    registration["firstname"] = $("#firstname").val();
    registration["lastname"] = $("#lastname").val();
    registration["email"] = $("#email").val();
    registration["password"] = $("#password").val();

    $("#btn-search").prop("disabled", true);


    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/addNewAdmin",
        data: JSON.stringify(registration),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {


            toggleAlert("alert-success", true , "Admin creato con successo !");

            setTimeout(function(){
                toggleAlert("alert-success");
            },1000);

            $("#firstname").val("");
            $("#lastname").val();
            $("#email").val();
            $("#password").val();
            $("#repeat_password").val();

        },
        error: function (e) {

            const resp = JSON.parse(e.responseText);

            toggleAlert("alert-danger", true , resp["msg"]);

            setTimeout(function(){
                toggleAlert("alert-danger");
            },1000);

        }
    });

}