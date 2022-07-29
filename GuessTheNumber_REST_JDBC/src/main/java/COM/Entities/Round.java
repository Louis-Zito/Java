package COM.Entities;
import java.time.LocalDateTime;
import java.util.Objects;

public class Round {
    private int roundID;
    private int gameID;
    private LocalDateTime time;
    private String guess;
    private String result;


    public Round() {
    }

    public Round(int roundID) {
    }

    public Round(int gameID, String guess){
        this.gameID = gameID;
        this.guess = guess;
    }

    public Round(int gameID, String guess, String result){
        this.gameID = gameID;
        this.guess = guess;
        this.result = result;
    }

    //service.makeGuess constructor
    public Round(int gameID, String guess, LocalDateTime time, String result){
        this.gameID = gameID;
        this.guess = guess;
        this.time = time;
        this.result = result;
    }

    public int getRoundID() {return roundID;}

    public void setRoundID(int roundID) {
        this.roundID = roundID;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getGuess() {
        return guess;
    }

    public void setGuess(String guess) {
        this.guess = guess;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Round round = (Round) o;

        if (roundID != round.roundID) return false;
        if (gameID != round.gameID) return false;
        if (!Objects.equals(time, round.time)) return false;
        if (!Objects.equals(guess, round.guess)) return false;
        return Objects.equals(result, round.result);
    }

    @Override
    public int hashCode() {
        int result1 = roundID;
        result1 = 31 * result1 + gameID;
        result1 = 31 * result1 + (time != null ? time.hashCode() : 0);
        result1 = 31 * result1 + (guess != null ? guess.hashCode() : 0);
        result1 = 31 * result1 + (result != null ? result.hashCode() : 0);
        return result1;
    }

    @Override
    public String toString(){
        return ("Game: " + gameID + ", Guess: " + guess + ", Time: " + time);
    }

}
