package oslomet.webprog.motorvogn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class VognController {

    @Autowired
    private VognRepository rep;

    @PostMapping("/lagre")
    public void leggTil(Motorvogn innMotorvogn) {
        rep.add(innMotorvogn);
    }

    @PostMapping("/visArray")
    public List<Motorvogn> vis() {
        return rep.vis();
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
    public void slett() {
        rep.slett();
    }

    @GetMapping("/slettBil")
    public void slettBil(int id) {
        rep.slettBil(id);
    }

    @GetMapping("/hentEnBil")
    public Motorvogn hentEnBil(int id) {
        return rep.hentEnBil(id);
    }

    @GetMapping("/endreBil")
    public void endreBil(Motorvogn m) {
        rep.endreBil(m);
    }

}
