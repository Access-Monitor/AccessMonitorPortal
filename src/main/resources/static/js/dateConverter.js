$(document).ready(function () {

    jQuery("[id=timestamp]").each(function() {
        let timestamp = parseInt($(this).text())
        let date = new Date(timestamp)
        let month = date.getMonth() + 1;
        $(this).text(date.getHours() + " : " + (date.getMinutes()<10?'0':'') + date.getMinutes())

        let date_title =  document.createElement("h1")
        date_title.innerText = date.getDate() + "/" + month + "/" + date.getFullYear()
        $(this).before(date_title)

        let title =  document.createElement("h1")
        if(COLOR == "red")
            title.style.cssText = "color: rgb(238,23,9);" ;
        else
            title.style.cssText = "color: rgb(96,203,45);" ;

        title.innerText = "Detection Time: "
        $(this).before(title)
 })
});