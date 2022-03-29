package oslomet.webprog.motorvogn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VognRepository {

    @Autowired
    private JdbcTemplate db;

    public void add(Motorvogn innMotorvogn) {
        String sql = "INSERT INTO Motorvogn (personNr, navn, adresse, kjennetegn, merke, type) VALUES(?, ?, ?, ?, ?, ?)";
        db.update(sql, innMotorvogn.getPersonNr(), innMotorvogn.getNavn(), innMotorvogn.getAdresse(), innMotorvogn.getKjennetegn(),
                innMotorvogn.getMerke(), innMotorvogn.getType());
    }

    public List<Motorvogn> vis() {
        String sql = "SELECT * FROM Motorvogn";
        List<Motorvogn> alleVogner = db.query(sql, new BeanPropertyRowMapper(Motorvogn.class));
        return alleVogner;
    }

    public void slett() {
        String sql = "DELETE FROM Motorvogn";
        db.update(sql);
    }

}
