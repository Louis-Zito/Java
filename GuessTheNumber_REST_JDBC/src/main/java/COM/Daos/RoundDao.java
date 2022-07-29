package COM.Daos;
import COM.Entities.Round;
import java.util.List;

public interface RoundDao {

    Round addRound(Round round);

    List<Round> getAllRounds(int ID);

    Round findByID(int ID);

    boolean updateRound(Round round);

    boolean deleteRound(int ID);
}