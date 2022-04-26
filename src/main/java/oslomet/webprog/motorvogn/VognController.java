package oslomet.webprog.motorvogn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@RestController
public class VognController {

    @Autowired
    private VognRepository rep;

    @Autowired
    private HttpSession session;

    public boolean valider(Motorvogn m) {
        String pers = "\\d{11}";
        String navn = "[a-zæøåA-ZÆØÅ \\-]{2,20}";
        String adresse = "[\\da-zæøåA-ZÆØÅ -'\\-]{2,50}";

        boolean persOK = m.getPersonNr().matches(pers);
        boolean navnOK = m.getNavn().matches(navn);
        boolean adresseOK = m.getAdresse().matches(adresse);

        return persOK && navnOK && adresseOK;
    }

    @PostMapping("/lagre")
    public void leggTil(Motorvogn innMotorvogn, HttpServletResponse response) throws IOException{
        if(session.getAttribute("LoggedIn") != null) {
            if (!valider(innMotorvogn)) {
                response.sendError(HttpStatus.NOT_ACCEPTABLE.value(), "Det har oppstått feil ved validering");
            } else {
                if (!rep.add(innMotorvogn)) {
                    response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Det har oppstått en feil, plis prøv igjen senere");
                }
            }
        } else response.sendError(HttpStatus.NOT_FOUND.value(), "Du må logge inn");
    }

    @PostMapping("/visArray")
    public List<Motorvogn> vis(HttpServletResponse response) throws IOException {
        List<Motorvogn> list = rep.vis();
        if(list == null) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Det har oppstått en feil, prøv igjen senere");
        }
        return list;
    }

    @GetMapping("/menyMerke")
    public List<String> merke() {
        return rep.visMerker();
    }

    @GetMapping("/menyType")
    public List<String> type(String merke) {
        return rep.visT(merke);
    }

    @GetMapping("/slett")
    public void slett(HttpServletResponse response) throws IOException{
        if(session.getAttribute("LoggedIn") != null) {
            if (!rep.slett()) {
                response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Prøv igjen senere");
            }
        } else response.sendError(HttpStatus.NOT_FOUND.value(), "Du må logge inn");
    }

    @GetMapping("/slettBil")
    public void slettBil(int id) {
        if(session.getAttribute("LoggedIn") != null) {
            rep.slettBil(id);
        }
    }

    @GetMapping("/hentEnBil")
    public Motorvogn hentEnBil(int id, HttpServletResponse response) throws IOException{
        Motorvogn m = rep.hentEnBil(id);
        if(m == null) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Prøv igjen senere");
        }
        return m;
    }

    @GetMapping("/endreBil")
    public void endreBil(Motorvogn m) {
        rep.endreBil(m);
    }

    @GetMapping("/login")
    public boolean login(Bruker bruker, HttpServletResponse response) throws IOException {
        if(rep.login(bruker)) {
            session.setAttribute("LoggedIn", bruker);
            return true;
        }
        response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Feil brukernavn eller passord");
        return false;
    }

    @GetMapping("/logout")
    public void logout() {
        session.removeAttribute("LoggedIn");
    }

    @GetMapping("/sjekkInlogget")
    public boolean sjekkInlogget() {
        return session.getAttribute("LoggedIn") != null;
    }

    @PostMapping("/krypterAlt")
    public void krypterAlt() {
        rep.krypterAllePassord();
    }
}
