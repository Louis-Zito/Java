package COM.ServiceLayer;
import COM.Daos.GameDao;
import COM.Daos.RoundDao;
import COM.Entities.Game;
import COM.Entities.Round;
import org.springframework.stereotype.Service;
import java.util.*;

import static java.util.stream.Collectors.joining;

@Service
public class ServiceLayer {

    private final GameDao gameDaoDB;
    private final RoundDao roundDaoDB;

    public List<Game> allGames;
    public List<Round> gameRounds;

    public ServiceLayer(GameDao gameDaoDB, RoundDao roundDaoDB) {
        this.gameDaoDB = gameDaoDB;
        this.roundDaoDB = roundDaoDB;
    }

    public int newGame() {
        Game game = new Game();
        game.setAnswer(generateAnswer());
        game = gameDaoDB.addGame(game);
        return game.getGameID();
    }
    public Round makeGuess(Round round) {
        Round guessRound = new Round(round.getGameID(), round.getGuess());
        Game gameOfRound = gameDaoDB.findByID(round.getGameID());
        //calculate result, make new round for update
        String newResult = calculateResult(guessRound.getGuess(), gameOfRound.getAnswer());
        guessRound.setResult(newResult);
        Round completedRound = roundDaoDB.addRound(guessRound);
        //pull Round back out of database with Date included
        completedRound = roundDaoDB.findByID(completedRound.getRoundID());
        //set Game to isFinished = True if result = e:4:p:0
        if (newResult.equals("e:4:p:0")){
            gameOfRound.setFinished(true);
            gameDaoDB.updateGame(gameOfRound);
        }
        //return Round with SQL auto-time completed after Update
        return completedRound;
    }

    public List<Game> getAllGames(){
        allGames = gameDaoDB.getAllGames();
        for(Game game : allGames){
            if(game.getFinished() == false){
                game.setAnswer("Hidden: Game isn't complete!");
            }
        }
        return allGames;
    }

    public Game getGameByID(int gameID) {
        Game game = new Game();
        game = gameDaoDB.findByID(gameID);
        if(game.getFinished() == false){
            game.setAnswer("Hidden: Game isn't complete!");
        }
        return game;
    }

    public List<Round> ListOfRounds(int gameID) {
        gameRounds = roundDaoDB.getAllRounds(gameID);
        return gameRounds;
    }

    //generate 4 non-repeating numerical chars (0-9) using SET
    public String generateAnswer(){
        Random r = new Random();
        int counter = 3;
        //Set ensures no repeats possible
        Set<Integer> generatedSet = new LinkedHashSet<>();
        while (generatedSet.size() <= counter) {
            Integer next = r.nextInt(10);
            generatedSet.add(next);
        }
        //Convert set of Integers to single String object via Stream & Collector
        String answer = generatedSet.stream().map(String::valueOf).collect(joining());
        return answer;
    }

    public String calculateResult(String guess, String answer) {
        int p = 0;
        int e = 0;
        if (guess.equals(answer)) {
            return "e:4:p:0";
        } else {
            for (int i = 0; i < 4; i++) {
                if (guess.charAt(i) == answer.charAt(i)) {
                    e++;
                    continue;
                }
                if (answer.indexOf(guess.charAt(i)) > -1) {
                    p++;
                    continue;
                }
            }
        }
        return "e:" + e + ":p:" + p;
    }//end CalculateResult

    //testing for repeats, input > 4, no input - found in POST:Guess
    public boolean testGuessForIncorrectInput(String guess){
        boolean inputError = false;
        //no repeat values
        if (guess.length() != guess.chars().distinct().count()){
            inputError = true;
        }
        //must be length = 4
        if (guess.length() != 4){
            inputError = true;
        }
        //cannot be blank
        if (guess == "")
            inputError = true;
        return inputError;
    }
}//end class
