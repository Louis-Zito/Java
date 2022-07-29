package COM.Daos.TestRoundDao;
import COM.Daos.GameDao;
import COM.Daos.RoundDao;
import COM.Entities.Game;
import COM.Entities.Round;
import COM.TestApplicationConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class RoundDaoTest {

    @Autowired
    RoundDao roundDao;

    @Autowired
    GameDao gameDao;

    @Before
    //delete all existing Rounds using existing gameIDs
    //includes test for deleteRounds
    public void setUp(){
            List<Round> rounds = roundDao.getAllRounds(1);
            for(Round round : rounds){
                roundDao.deleteRound(1);
        }
    }//end setUp

    @Test
    public void Add_ID_GetAll_Rounds(){
        int gameID = 1;
        Game newGame = new Game();
        newGame.setGameID(1);
        newGame.setAnswer("1234");
        newGame.setFinished(false);
        gameDao.addGame(newGame);

        Round round1 = new Round(gameID, "1234");
        round1.setResult("e:1:p:1");
        roundDao.addRound(round1);

        Round round2 = new Round(gameID, "4567");
        round2.setResult("e:2:p:2");
        roundDao.addRound(round2);

        Round round3 = new Round(gameID, "1289");
        round3.setResult("e:3:p:3");
        roundDao.addRound(round3);

        List<Round> rounds = roundDao.getAllRounds(gameID);
        assertEquals(3, rounds.size());
        assertNotNull(rounds);

        assertThat(rounds, containsInAnyOrder(
                hasProperty("result", is("e:1:p:1")),
                hasProperty("result", is("e:2:p:2")),
                hasProperty("result", is("e:3:p:3"))
        ));

        assertThat(rounds, containsInAnyOrder(
                hasProperty("guess", is("1234")),
                hasProperty("guess", is("4567")),
                hasProperty("guess", is("1289"))
        ));

    }

}