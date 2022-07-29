package com.controllers;
import com.data.ToDoDao;
import com.models.ToDo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.Max;
import java.util.List;

//Controller:
//1 makes class injectable
//2 tells Spring MVC to scan for HTTP request methods
//3 tells Spring MVC to convert meth results to JSON
@RestController
//include resource, here TODOs, in HTTP request for resource
//api is preference, not required
@RequestMapping("/api/todo")
public class ToDoController {

    private final ToDoDao dao;

    //adds dependency
    //don't need "Autowired"/"Inject" due to @Repository / memory dao
    //2 Repository files: InMem and DB
    //see AppProps file profiles.active for which option will be injected via setting
    public ToDoController(ToDoDao dao) {
        this.dao = dao;
    }

    @GetMapping
    //now reacts to GET at http://localhost:8080/api/todo
    public List<ToDo> all(){
        return dao.getAll();
    }

    //create: POST
    //POST req for path /api/todo
    //override default 200 return w/204 Created
    //won't do this if dealing with invalid/operation fails later
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    //RequestBody = Model binding, used for JSON obj creation
    //model binding: MVC takes HTTP req and transforms into todo
    //as long as data: named right, present in right places
    //could als be in URL/form but here want Body JSON
    public ToDo create(@RequestBody ToDo todo){
        return dao.add(todo);
    }

    //below adds DYNAMIC chunk to base URL
    //findById activates by GET w/URL ending with an id
    //eg /api/todo/413
    @GetMapping("/{id}")
    //PathVar tells MVC to expect value id in URL, convert to int
    public ResponseEntity<ToDo> findById(@PathVariable int id){
        //ResponseEntity: can return flexible response types
        //data payload as generic; can take explicit status code
        //has several static utility methods also
        //<ToDo> allows JSON todo in body
        ToDo result = dao.findById(id);
        if (result == null){
            //if null: ret null & 404 not found
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        //.ok will serialize found generic/todo and send 200 ok
        return ResponseEntity.ok(result);
    }

    //update: PUT
    //typeless ResponseEntity, nothing to return, use for Status here
    //2 param: URL id, body todo
    //id in body but convention is have URL operating on resource use this form
    @PutMapping("/{id}")
    //return type-less response entity as no todo return
    public ResponseEntity update(@PathVariable int id, @RequestBody ToDo todo){
        ResponseEntity response = new ResponseEntity(HttpStatus.NOT_FOUND);
        //check URL id vs body todo id
        if (id != todo.getId()){
            //422 Un-processable means request error, here id doesn't match body id
            response = new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (dao.update(todo)){
            //if update, no return, NO_CONTENT is polite expected response
            response = new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return response;
    }

    //delete
    @DeleteMapping("/{id}")
    //return typeless RE, no todo return
    public ResponseEntity delete(@PathVariable int id){
        if (dao.deleteById(id)){
            //delete ok: 204 no content
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        //not deleted: 404 not found
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
