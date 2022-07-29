package com.App.Controllers;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
//RestController makes POJO: plain old java obj into web enabled controller
//RestController registered as any other @Component
//transforms data in URL/req body to Java Obj and values
//http://localhost:8080/api

//well-formed web req activates RestController
//it exe actions that result in data/model
//model merges into View, result in HTTP response as content
@RestController
//this class only handles URLs starting w/ "/api"
//can also map at method level
@RequestMapping("/api")
public class SimpleController {

    //mapped to only handle GET HTTP requests
    @GetMapping
    public String[] helloWorld() {
        String[] result = {"Hello", "World", "!"};
        //returned to Spring MVC framework, serialized, JSON, HTTP response
        return result;
    }

    //if POST with /api/calculate activate method, adds Request url above
    @PostMapping("/calculate")
    //parameters in order of expression
    //names imp as mapped to HTTP key/value pairs
    public String calculate(int operand1, String operator, int operand2){
        int result = 0;
        switch(operator) {
            case "+":
                result = operand1 + operand2;
                break;
            case "-":
                result = operand1 - operand2;
                break;
            case "*":
                result = operand1 * operand2;
                break;
            case "/":
                result = operand1 / operand2;
                break;
            default:
                String message = String.format("operator '%s' is invalid", operator);
                throw new IllegalArgumentException(message);
        }
    //note return of just String vs [], Spring takes any return and does best to JSON
    return String.format("%s %s %s = %s", operand1, operator, operand2, result);
    }

    //DELETE example using ID
    //activate w/DELETE HTTP, URL with {}
    @DeleteMapping("/resource/{id}")
    //overrides default void return of 200 with NO_CONTENT 204
    @ResponseStatus(HttpStatus.NO_CONTENT)
    //void return only returns 200 default if no override above
    //PathVariable tells Spring find it in URL
    //param name must match var chunk {} name
    public void delete(@PathVariable int id){
        //this is where we use id to delete
    }
}//end class