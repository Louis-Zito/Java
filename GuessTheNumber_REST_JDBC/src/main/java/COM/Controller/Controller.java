/**
 * @author Louis Zito
 * 07-15-22
 * "Guess the Number" game
 * using Spring Boot / MVC, REST, JDBC
 */
package COM.Controller;
import COM.Entities.Game;
import COM.Entities.Round;
import java.util.List;
import COM.ServiceLayer.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class Controller {

    @Autowired
    ServiceLayer service;

    @PostMapping("/begin")
    @ResponseStatus(HttpStatus.CREATED)
    //return 201 & gameID
    public int createGame() {
        return service.newGame();
    }

    @PostMapping("/guess")
    public ResponseEntity<Round> guess(@RequestBody Round round) {
        //test for no repeated characters in "guess"
        //if repeats found, input blank or input !== length of 4: return BAD_REQUEST
        if (service.testGuessForIncorrectInput(round.getGuess())) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        Round completedGuessRound =  service.makeGuess(round);

        return ResponseEntity.ok(completedGuessRound);
    }

    @GetMapping("/game")
    public List<Game> getAllGames(){
        return service.getAllGames();
    }

    @GetMapping("/game/{gameID}")
    public ResponseEntity<Game> getGameByID(@PathVariable int gameID){
        Game result = service.getGameByID(gameID);
        if (result == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/rounds/{gameID}")
    public ResponseEntity<List<Round>> getGameRounds(@PathVariable int gameID){
         List<Round> roundsOfGame = service.ListOfRounds(gameID);
        if (roundsOfGame == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(roundsOfGame);
    }
}
