$(function () {
    const id = window.location.search.substring(1);
    const url = "/hentEnBil?" + id;
    $.get(url, function(data) {
        $("#id").val(data.id);
        $("#innPers").val(data.personNr);
        $("#innNavn").val(data.navn);
        $("#innAdresse").val(data.adresse);
        $("#innKjennetegn").val(data.kjennetegn);
        let merke = data.merke;
        setMerke(merke);
        velgMerker();
        velgType(merke);
    }).fail(function(jqXHR) {
        const json = $.parseJSON(jqXHR.responseText);
        $("#feil").html(json.message);
    });
});

let m;

function setMerke(merke) {
    m = merke;
}

function getMerke() {
    return m;
}

function velgMerker() {
    $.get("/menyMerke", function (merker){
        formaterMerker(merker);
    });
}

function formaterMerker (merker){
    let ut = "<select id='valgMerker' onchange='velgType(this.value)'>";
    for(let merke of merker){
        if(merke === getMerke()) {
            ut += "<option value='"+merke+"' selected>"+merke+"</option>";
        }
        else ut += "<option value='"+merke+"'>"+merke+"</option>";
    }
    ut += "</select>";
    $("#innMerke").html(ut);
}

function velgType(merke){
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

function endreBil() {
    const Motorvogn = {
        id: $("#id").val(),
        personNr: $("#innPers").val(),
        navn: $("#innNavn").val(),
        adresse: $("#innAdresse").val(),
        kjennetegn: $("#innKjennetegn").val(),
        merke: $("#valgMerker").val(),
        type: $("#valgTyper").val()
    }

    $.get("/endreBil", Motorvogn, function() {
        window.location.href = "index.html";
    });
}