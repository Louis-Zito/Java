package com.data;
import java.util.List;
import com.models.ToDo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

//2 daos: inMem and DB
//use Profiles in application.properties to select which dao to use
@Repository
@Profile("database")
//implement ToDoDao so all ops match in-mem dao
public class ToDoDatabaseDao implements ToDoDao{

    //pulled from Boot starter-jdbc, final as should not be changed
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ToDoDatabaseDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public ToDo add(ToDo todo) {

        final String sql = "INSERT INTO todo(todo, note) VALUES(?,?);";
        //keyholder is IF def something to hold keys
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        //UPDATE has 2 param: PrepStmt, KeyHolder: update(conn) {prepStmnt, keyholder}
        //PrepStatement creator gens PrepStmnt from a Connection, both JDBC classes
        //here use Lambda, unnamed meth, takes Conn as param, returns complete PrepStmnt
        jdbcTemplate.update((Connection conn) -> {
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, todo.getTodo());
            statement.setString(2, todo.getNote());
            return statement;
        }, keyHolder);

        todo.setId(keyHolder.getKey().intValue());

        return todo;
    }

    @Override
    public List<ToDo> getAll() {
        final String sql = "SELECT id, todo, note, finished FROM todo WHERE id = ?;";
        return jdbcTemplate.query(sql, new ToDoMapper());
    }

    @Override
    public ToDo findById(int id) {
        final String sql = "SELECT id, todo, note, finished FROM todo WHERE id = ?;";
        return jdbcTemplate.queryForObject(sql, new ToDoMapper());
    }

    @Override
    public boolean update(ToDo todo) {

        final String sql = "UPDATE todo SET todo = ?, "
                                + "note = ?, "
                                + "finished = ? "
                                + "WHERE id = ?;";

        //update returns bool if complete, only return if True/1 > 0????
        return jdbcTemplate.update(sql, todo.getTodo(), todo.getNote(), todo.isFinished(), todo.getId()) > 0;
    }

    @Override
    public boolean deleteById(int id) {
        final String sql = "DELETE FROM todo WHERE id = ?;";
        return jdbcTemplate.update(sql, id) > 0;
    }

    private static final class ToDoMapper implements RowMapper<ToDo>{
        @Override
        public ToDo mapRow(ResultSet rs, int index) throws SQLException {
            ToDo td = new ToDo();
            td.setId(rs.getInt("id"));
            td.setTodo(rs.getString("todo"));
            td.setNote(rs.getString("note"));
            td.setFinished(rs.getBoolean("finished"));
            return td;
        }
    }

}
