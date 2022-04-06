package oslomet.webprog.motorvogn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
public class VognController {

    @Autowired
    private VognRepository rep;

    @PostMapping("/lagre")
    public void leggTil(Motorvogn innMotorvogn, HttpServletResponse response) throws IOException{
        if(!rep.add(innMotorvogn)) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Det har oppstått en feil, plis prøv igjen senere");
        }
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
        if(!rep.slett()) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Prøv igjen senere");
        }
    }

    @GetMapping("/slettBil")
    public void slettBil(int id) {
        rep.slettBil(id);
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

}
