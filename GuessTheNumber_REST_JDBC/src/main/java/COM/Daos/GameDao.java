package COM.Daos;
import COM.Entities.Game;
import java.util.List;

public interface GameDao {

    Game addGame(Game game);

    List<Game> getAllGames();

    Game findByID(int ID);

    boolean updateGame(Game game);

    boolean deleteGame(int ID);
}
