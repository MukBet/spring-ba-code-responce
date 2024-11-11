package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // Указывает, что этот класс будет REST-контроллером
public class GreetingController {

    @GetMapping("/greeting") // Маршрут для обработки GET-запросов по пути /greeting
    public String getGreeting() {
        return "!!Привет, мир! Это микросервис на Java!";
    }
}
