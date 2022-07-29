package COM.Daos;
import COM.Entities.Game;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository  //CRUD
//@Profile("GameDatabase")
public class GameDaoDB implements GameDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public GameDaoDB(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Game addGame(Game game) {
        //gameID auto-key, isFinished default = false
        final String sql = "INSERT INTO game(answer) VALUES (?);";
        //holds auto-generated key
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update((Connection conn) -> {
            //RETURN_GENERATED obtains generated key for new Game
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, game.getAnswer());
            return statement;}, keyHolder);
        //KeyHolder.getKey() returns key generated by SQL for game object
        game.setGameID(keyHolder.getKey().intValue());
        return game;
    }

    @Override
    public List<Game> getAllGames() {
        final String sql = "SELECT gameID, answer, isFinished FROM game;";
        return jdbcTemplate.query(sql, new GameMapper());
    }

    @Override
    public Game findByID(int gameID) {
        final String sql = "SELECT * FROM game WHERE gameID = ?;";
        return jdbcTemplate.queryForObject(sql, new GameMapper(), gameID);
    }

    @Override
    //Update returns no. of rows updated
    public boolean updateGame(Game game) {
        final String sql = "UPDATE game SET isFinished = ?;";
        //will return TRUE if an update occurred as 1+
        return jdbcTemplate.update(sql, game.getFinished()) > 0;
    }

    @Override
    //Update returns no. of rows deleted
    public boolean deleteGame(int gameID) {
        final String sql = "DELETE FROM game WHERE gameID = ?;";
        return jdbcTemplate.update(sql, gameID) > 0;
    }

    private static final class GameMapper implements RowMapper<Game>{
        @Override
        public Game mapRow(ResultSet rs, int index) throws SQLException {
            Game game = new Game();
            game.setGameID(rs.getInt("gameID"));
            game.setAnswer(rs.getString("answer"));
            game.setFinished(rs.getBoolean("isFinished"));
            return game;
        }
    }
}