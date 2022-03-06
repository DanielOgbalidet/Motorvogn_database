package oslomet.webprog.motorvogn;

public class Motorvogn {
    private String personNr;
    private String navn;
    private String adresse;
    private String kjennetegn;
    private String merke;
    private String type;

    public Motorvogn(String personNr, String navn, String adresse, String kjennetegn,
                     String merke, String type) {
        this.personNr = personNr;
        this.navn = navn;
        this.adresse = adresse;
        this.kjennetegn = kjennetegn;
        this.merke = merke;
        this.type = type;
    }

    public Motorvogn() {}

    public String getPersonNr() {
        return personNr;
    }

    public void setPersonNr(String personNr) {
        this.personNr = personNr;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getKjennetegn() {
        return kjennetegn;
    }

    public void setKjennetegn(String kjennetegn) {
        this.kjennetegn = kjennetegn;
    }

    public String getMerke() {
        return merke;
    }

    public void setMerke(String merke) {
        this.merke = merke;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
