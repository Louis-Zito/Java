package com.App;
//packages critical
//main com.App package with "App" file and Controllers PACKAGE inside
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

    //starts component scan, misses any shallower packages
    @SpringBootApplication
    public class App {
        //Spring boot has built in web server, therefore uses PSVM method
        //other Spring MVC apps have no server so use ext web server to start/load
        public static void main(String[] args) {
            SpringApplication.run(App.class, args);
        }
    }

