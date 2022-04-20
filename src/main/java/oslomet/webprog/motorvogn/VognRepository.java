package oslomet.webprog.motorvogn;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VognRepository {

    private final Logger logger = LoggerFactory.getLogger(VognRepository.class);

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
}
