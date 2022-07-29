package COM.Daos.TestGameDao;
import COM.Daos.GameDao;
import COM.TestApplicationConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.runner.RunWith;
import org.junit.Test;
import COM.Entities.Game;
import org.junit.Before;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class GameDaoDBTest {

    @Autowired
    GameDao gameDao;

    @Before
    //includes test for deleteGame
    public void setUp(){
        List<Game> games = gameDao.getAllGames();
        for(Game game : games){
            gameDao.deleteGame(game.getGameID());
        }
    }

    @Test
    public void addGame() {
        //includes test for findByID
        Game game = new Game();
        game.setAnswer("5678");
        game.setFinished(false);
        gameDao.addGame(game);

        Game retrieveGame = gameDao.findByID(game.getGameID());
        assertEquals(game, retrieveGame);
    }

    @Test
    public void getAllGames() {
        Game game = new Game();
        game.setAnswer("5678");
        game.setFinished(false);
        gameDao.addGame(game);

        Game game2 = new Game();
        game2.setAnswer("1234");
        game2.setFinished(false);
        gameDao.addGame(game2);

        Game game3 = new Game();
        game3.setAnswer("1289");
        game3.setFinished(false);
        gameDao.addGame(game3);

        List<Game> games = gameDao.getAllGames();

        assertTrue(games.contains(game));
        assertTrue(games.contains(game2));
        assertTrue(games.contains(game3));
        assertEquals(3,games.size());
    }

    @Test
    public void updateGame() {
        Game game = new Game();
        game.setAnswer("5678");
        game.setFinished(false);
        gameDao.addGame(game);

        Game retrievedGame = gameDao.findByID(game.getGameID());
        assertEquals(retrievedGame.getGameID(), game.getGameID());
        assertEquals(retrievedGame.getFinished(), game.getFinished());

        //updateGame sets isFinished to True
        boolean gameUpdated = gameDao.updateGame(game);

        assertFalse(retrievedGame.getFinished());
        assertTrue(gameUpdated);
    }

}