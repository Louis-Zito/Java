package COM.Entities;

import java.util.Objects;

public class Game {
    private int gameID;
    private String answer;
    private Boolean isFinished;

    public Game() {
        //used in GameMapper
    }

    public Game(int gameID){
        this.gameID = gameID;
    }

    public Game(String answer, boolean isFinished){
        this.answer = answer;
        this.isFinished = isFinished;
    }

    public Game(int gameID, String answer, boolean isFinished){
        this.gameID = gameID;
        this.answer = answer;
        this.isFinished = isFinished;
    }


    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Boolean getFinished() {
        return isFinished;
    }

    public void setFinished(Boolean finished) {
        isFinished = finished;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Game game = (Game) o;

        if (gameID != game.gameID) return false;
        if (!Objects.equals(answer, game.answer)) return false;
        return Objects.equals(isFinished, game.isFinished);
    }

    @Override
    public int hashCode() {
        int result = gameID;
        result = 31 * result + (answer != null ? answer.hashCode() : 0);
        result = 31 * result + (isFinished != null ? isFinished.hashCode() : 0);
        return result;
    }

    @Override
    public String toString(){
        return "Game: " + gameID + ", Answer: " + answer + ", Complete: " + isFinished;
    }
}
