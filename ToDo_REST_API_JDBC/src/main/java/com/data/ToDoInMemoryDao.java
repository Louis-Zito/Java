package com.data;
import com.models.ToDo;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

//data stored in mem/repo
//Repo denotes injectable bean at class level
//like Component BUT denotes collection-like for data store
//expects Add, Update, Find, Get, Delete w/o knowing how data stored
@Repository //use with DAO usually!!!!!
@Profile("memory")
public class ToDoInMemoryDao implements ToDoDao{

    //non-instance-linked List of ToDo's
    private static final List<ToDo> todos = new ArrayList<>();

    @Override
    public ToDo add(ToDo todo) {
        //Stream to get next max ID value or make 1??
        int nextId = todos.stream()
                .mapToInt(i -> i.getId())
                .max()
                .orElse(0) + 1;
        todo.setId(nextId);
        todos.add(todo);
        return todo;
    }

    @Override
    public List<ToDo> getAll() {
        return new ArrayList<>(todos);
    }

    @Override
    public ToDo findById(int id) {
        //filter Ids until first match of id arg
        //not found return null
        return todos.stream()
                .filter(i -> i.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean update(ToDo todo) {
        int index = 0;
        //find the index of todo to update
        while (index < todos.size() && todos.get(index).getId() != todo.getId()){
            index++;
        }
        //index 0, size is +1
        //if don't go past size: update
        if (index < todos.size()){
            todos.set(index, todo);
        }
        //return bool if index never passed size
        //as then update occurred
        return index < todos.size();


    }

    @Override
    public boolean deleteById(int id) {
        //return true if stream found id arg
        //if found "removeIf" took out of List
        return todos.removeIf(i -> i.getId() == id);
    }
}
