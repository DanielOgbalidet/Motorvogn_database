$(function () {
    velgMerker();
});

function velgMerker() {
    $.get("/menyMerke", function (merker){
        formaterMerker(merker);
    });
}

function formaterMerker (merker){
    let ut = "<select id='valgMerker' onchange='velgType()'>";
    ut += "<option disabled selected>Velg</option>"
    for(let merke of merker){
        ut += "<option value='"+merke+"'>"+merke+"</option>";
    }
    ut += "</select>";
    $("#innMerke").html(ut);
}

function velgType(){
    let merke = $("#valgMerker").val();
    let url = "/menyType?merke="+merke;

    $.get(url, function (typer){
        formaterTyper(typer);
    });
}

function formaterTyper (typer){
    let ut = "<select id='valgTyper'>";
    for(let type of typer){
        ut += "<option value='"+type+"'>"+type+"</option>"
    }
    ut += "</select>";
    $("#innType").html(ut);
}

function lagre() {
    const Motorvogn = {
        personNr: $("#innPers").val(),
        navn: $("#innNavn").val(),
        adresse: $("#innAdresse").val(),
        kjennetegn: $("#innKjennetegn").val(),
        merke: $("#valgMerker").val(),
        type: $("#valgTyper").val()
    }

    $.post("/lagre", Motorvogn, function () {
        vis();
    });
}

function vis() {
    $.post("/visArray", function (data) {
        ut(data);
    });
}

function ut(motorvogn) {
    let ut = "Alle bilene: <br>";
    ut += "<table class='table table-striped'>" +
        "<tr>" +
        "<th>Personnr</th>" +
        "<th>Navn</th>" +
        "<th>Adresse</th>" +
        "<th>Kjennetegn</th>" +
        "<th>Merke</th>" +
        "<th>Type</th>" +
        "</tr>"
    for(let vogn of motorvogn){
        ut += "<tr>" +
            "<td>"+vogn.personNr+"</td>" +
            "<td>"+vogn.navn+"</td>" +
            "<td>"+vogn.adresse+"</td>" +
            "<td>"+vogn.kjennetegn+"</td>" +
            "<td>"+vogn.merke+"</td>" +
            "<td>"+vogn.type+"</td>" +
            "</tr>"
    }
    ut += "</table>";
    $("#visRegister").html(ut);

    $("#slett").attr('disabled', false);
    $("input[name = fjern]").val("");
    $("#valgMerker")[0].selectedIndex = 0;
    $("#innType").html("");
}

function slett() {
    $.get("/slett", function () {
        $("#visRegister").html("");
    });
    $("#slett").attr('disabled', true);
}