package oslomet.webprog.motorvogn;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VognRepository {

    private final Logger logger = LoggerFactory.getLogger(VognRepository.class);

    BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder(15);

    @Autowired
    private JdbcTemplate db;

    public boolean add(Motorvogn innMotorvogn) {
        String sql = "INSERT INTO Motorvogn (personNr, navn, adresse, kjennetegn, merke, type) VALUES(?, ?, ?, ?, ?, ?)";
        try {
            db.update(sql, innMotorvogn.getPersonNr(), innMotorvogn.getNavn(), innMotorvogn.getAdresse(), innMotorvogn.getKjennetegn(),
                    innMotorvogn.getMerke(), innMotorvogn.getType());
            return true;
        } catch(Exception e) {
            logger.error("Feil med add()" + e);
        }
        return false;
    }

    public List<Motorvogn> vis() {
        String sql = "SELECT * FROM Motorvogn";
        try {
            return db.query(sql, new BeanPropertyRowMapper<>(Motorvogn.class));
        } catch(Exception e) {
            logger.error("Feil i vis()" + e);
        }
        return null;
    }

    public boolean slett() {
        String sql = "DELETE FROM Motorvogn";
        try{
            db.update(sql);
            return true;
        } catch(Exception e) {
            logger.error("Feil med slett()" + e);
        }
        return false;
    }

    public List<String> visMerker() {
        String sql = "SELECT DISTINCT merke FROM Bil";
        return db.queryForList(sql, String.class);
    }

    public List<String> visT(String merke) {
        String sql = "SELECT Type FROM Bil WHERE merke = ?";
        return db.queryForList(sql, String.class, merke);
    }

    public void slettBil(int id) {
        String sql = "DELETE FROM Motorvogn WHERE id = ?";
        db.update(sql, id);
    }

    public Motorvogn hentEnBil(int id) {
        //Object[] param = new Object[1];
        //param[0] = id;
        //Hvis du bruker det over så må id i try byttes om til param
        String sql = "SELECT * FROM Motorvogn WHERE id = ?";
        try {
            return db.queryForObject(sql, BeanPropertyRowMapper.newInstance(Motorvogn.class), id);
        }catch(Exception e) {
            logger.error("Feil med hentEnBil()" + e);
        }
        return null;
    }

    public void endreBil(Motorvogn m) {
        String sql = "UPDATE Motorvogn SET personNr = ?, navn = ?, adresse = ?, kjennetegn = ?, merke = ?, type = ? WHERE id = ?";
        db.update(sql, m.getPersonNr(), m.getNavn(), m.getAdresse(), m.getKjennetegn(), m.getMerke(), m.getType(), m.getId());
    }

    /*
    public boolean login(Bruker bruker) {
        Object[] param = new Object[]{bruker.getBrukernavn(), bruker.getPassord()};
        String sql = "SELECT COUNT(*) FROM Bruker WHERE brukernavn = ? AND passord = ?";
        int antall;

        try{
            antall = db.queryForObject(sql, Integer.class, param);
        }catch (Exception e) {
            logger.error("Feil med login() " + e);
            return false;
        }
        return antall > 0;
    }
    Brukt når jeg ikke trengte å hashe passord
     */

    public boolean login(Bruker bruker) {
        String sql = "SELECT * FROM Bruker WHERE brukernavn = ?";

        try{
            Bruker b = db.queryForObject(sql, BeanPropertyRowMapper.newInstance(Bruker.class), bruker.getBrukernavn());
            if(b != null) {
                return sjekkPassord(bruker.getPassord(), b.getPassord());
            } else return false;
        }catch (Exception e) {
            logger.error("Feil med login() " + e);
            return false;
        }
    }

    public String krypterPassord(String passord) {
        return bCrypt.encode(passord);
    }

    public boolean sjekkPassord(String passord, String hashPassord) {
        return bCrypt.matches(passord, hashPassord);
    }

    /*
    public String hentPassord(int i) {
        String sql = "SELECT passord FROM Bruker WHERE id = ?";
        String passord =  db.queryForObject(sql, String.class, i);
        return krypterPassord(passord);
    }

    public void krypterAllePassord() {
        String sql = "UPDATE Bruker SET passord = ? WHERE id = ?";

        int i = 1;
        while(i > 0) {
            if(hentPassord(i) != null) {
                db.update(sql, hentPassord(i), i);
            } else i = 0;
            i++;
        }
    }

    Disse ble brukt når jeg allerede hadde to brukere inne i tabellen via data.sql. Trengs ikke lenger når alle brukere må opprettes
     */

    public boolean opprett(Bruker b) {
        String sql1 = "SELECT COUNT(*) FROM Bruker WHERE brukernavn = ?";
        String sql2 = "INSERT INTO Bruker (brukernavn, passord) VALUES (?, ?)";
        int antall = db.queryForObject(sql1, Integer.class, b.getBrukernavn());
        try {
            if(antall > 0) {
                return false;
            } else {
                db.update(sql2, b.getBrukernavn(), krypterPassord(b.getPassord()));
                return true;
            }
        } catch(Exception e) {
            logger.error("Feil med opprettelse av bruker " + e);
            return false;
        }
    }
}
