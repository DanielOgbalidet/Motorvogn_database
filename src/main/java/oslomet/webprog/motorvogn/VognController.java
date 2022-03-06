package oslomet.webprog.motorvogn;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class VognController {

    private final ArrayList<Motorvogn> VognArray = new ArrayList<>();

    @PostMapping("/lagre")
    public void leggTil(Motorvogn innMotorvogn) {
        VognArray.add(innMotorvogn);
    }

    @PostMapping("/visArray")
    public ArrayList<Motorvogn> vis() {
        return VognArray;
    }

    @GetMapping("/menyMerke")
    public ArrayList<String> merke() {
        ArrayList<String> merker = new ArrayList<>();
        merker.add("Volvo");
        merker.add("Toyota");
        merker.add("BMW");
        return merker;
    }

    @GetMapping("/menyType")
    public ArrayList<String> type(String merke) {
        ArrayList<String> volvo = new ArrayList<>();
        volvo.add("A8");
        volvo.add("B56");
        volvo.add("A2");

        ArrayList<String> toyota = new ArrayList<>();
        toyota.add("T97");
        toyota.add("T23");
        toyota.add("P4");

        ArrayList<String> bmw = new ArrayList<>();
        bmw.add("B34");
        bmw.add("M34");
        bmw.add("W34");

        if(merke.equals("Volvo")) return volvo;
        else if (merke.equals("Toyota")) return toyota;
        return bmw;
    }

    @GetMapping("/slett")
    public void slett() {
        VognArray.clear();
    }

}
