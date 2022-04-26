$(function () {
    sjekkInlogget();
    velgMerker();
    vis();
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
    }).fail(function(jqXHR) {
        const json = $.parseJSON(jqXHR.responseText);
        $("#feil").html(json.message);
    });
}

function vis() {
    $.post("/visArray", function (data) {
        ut(data);
    }).fail(function(jqXHR) {
        const json = $.parseJSON(jqXHR.responseText);
        $("#feil").html(json.message);
    });
}

function ut(motorvogn) {
    let ut = "<h1>Motorvogner</h1>: <br>";
    ut += "<table class='table table-striped'>" +
        "<tr>" +
        "<th>Personnr</th>" +
        "<th>Navn</th>" +
        "<th>Adresse</th>" +
        "<th>Kjennetegn</th>" +
        "<th>Merke</th>" +
        "<th>Type</th>" +
        "<th>Slett bil</th>" +
        "<th>Endre bil</th>" +
        "</tr>"
    for(let vogn of motorvogn){
        ut += "<tr>" +
            "<td>"+vogn.personNr+"</td>" +
            "<td>"+vogn.navn+"</td>" +
            "<td>"+vogn.adresse+"</td>" +
            "<td>"+vogn.kjennetegn+"</td>" +
            "<td>"+vogn.merke+"</td>" +
            "<td>"+vogn.type+"</td>" +
            "<td><button name='endre' class='btn btn-danger' onclick='slettBil("+vogn.id+")'>Slett</button></td>" +
            "<td><button name='endre' class='btn btn-info' onclick='endreBil("+vogn.id+")'>Endre</button></td>" +
            "</tr>"
    }
    ut += "</table>";
    $("#visRegister").html(ut);

    $("input[name = fjern]").val("");
    $("#valgMerker")[0].selectedIndex = 0;
    $("#innType").html("");
}

function slettBil(id) {
    const url = "/slettBil?id=" + id;
    $.get(url, function() {
        vis();
    });
}

function slett() {
    $.get("/slett", function () {
        $("#visRegister").html("");
        vis();
    }).fail(function(jqXHR) {
        const json = $.parseJSON(jqXHR.responseText);
        $("#feil").html(json.message);
    });
}

function endreBil(id) {
    $.get("/sjekkInlogget", function(data) {
        if(data) {
            window.location.href = "endre.html?id=" + id;
        }
    });
}

function loginIndex() {
    window.location.href = "login.html";
}

function logout() {
    $.get("/logout", function() {
        window.location.reload();
    });
}

function sjekkInlogget() {
    $.get("/sjekkInlogget", function(data) {
        if(!data) {
            $("#leggTil").hide();
            $("#slett").hide();
            $("#logout").attr('disabled', true);
            $("#loginIndex").attr('disabled', false);
            $("[name = endre]").hide();
            $("#tableMotorvogn").hide();
        } else {
            $("#leggTil").show();
            $("#slett").show();
            $("#logout").attr('disabled', false);
            $("#loginIndex").attr('disabled', true);
            $("[name = endre]").show();
            $("#tableMotorvogn").show();
        }
    });
}

function krypterAlt() {
    $.post("/krypterAlt", function() {
    });
}