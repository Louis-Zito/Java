package com.data;
import com.models.ToDo;
import java.util.List;

public interface ToDoDao {

    ToDo add(ToDo todo);

    List<ToDo> getAll();

    ToDo findById(int id);

    //nothing returned so use bool here for confirmation
    //true if item exists and is updated
    boolean update(ToDo todo);

    //true if item exists and is deleted
    boolean deleteById(int id);
}
