function validerPers(pers) {
    let regex = /^\d{11}$/
    if(!regex.test(pers)) {
        $("#persF").html("Skriv inn riktig personnummer");
        return false;
    }
    $("#persF").html("");
    return true;
}

function validerNavn(navn) {
    let regex = /^[a-zæøåA-ZÆØÅ \-]{2,20}$/
    if(!regex.test(navn)) {
        $("#navnF").html("Skriv inn 2 til 20 tegn");
        return false;
    }
    $("#navnF").html("");
    return true;
}

function validerAdresse(adresse) {
    let regex = /^[a-zæøåA-ZÆØÅ .'\-0-9]{2,50}$/
    if(!regex.test(adresse)) {
        $("#adresseF").html("Skriv inn riktig adresse");
        return false;
    }
    $("#adresseF").html("");
    return true;
}

function sjekkValider() {
    let pers = $("#innPers").val();
    let navn = $("#innNavn").val();
    let adresse = $("#innAdresse").val();

    let sjekk = false;

    if(validerPers(pers)) sjekk = true;
    if(validerNavn(navn)) sjekk =  true;
    if(validerAdresse(adresse)) sjekk = true;

    return sjekk;
}

function validerAltMain() {
    if(sjekkValider()) {
        $.getScript("index.js", function() {
            lagre();
        });
    }
}

function validerAltEndre() {
    if(sjekkValider()) {
        $.getScript("endre.js", function() {
            endreBil();
        });
    }
}