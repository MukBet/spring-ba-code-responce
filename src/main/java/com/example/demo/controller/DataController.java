package com.example.demo.controller;

import com.example.demo.model.RequestData;
import com.example.demo.service.DataService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/data")
public class DataController {

    @Autowired
    private DataService dataService;

    @PostMapping("/save")
    public Map saveGetData(@RequestParam(required = false) Map<String, String> params) {
        try {
            // Преобразование параметров в JSON-строку для хеширования и сохранения
            ObjectMapper mapper = new ObjectMapper();
            String jsonData = mapper.writeValueAsString(params);
            // Проверка наличия данных в кеше по хэшу JSON-строки
            Optional<RequestData> cachedData = dataService.findByData(jsonData);

            if (cachedData.isPresent()) {
                // Если данные есть в кеше, возвращаем их
                return mapper.readValue(cachedData.get().getData(), Map.class);
            }
            else
            {
                // Если данных нет в кеше, сохраняем их и возвращаем входные данные
                dataService.saveData(jsonData);
                return params;
            }
        } catch (Exception e) {
            throw new RuntimeException("Ошибка обработки данных", e);
        }
    }

    @GetMapping("/save")
    public Map savePostData(@RequestParam(required = false) Map<String, String> params) {
        try {
            // Преобразование параметров в JSON-строку для хеширования и сохранения
            ObjectMapper mapper = new ObjectMapper();
            String jsonData = mapper.writeValueAsString(params);
            // Проверка наличия данных в кеше по хэшу JSON-строки
            Optional<RequestData> cachedData = dataService.findByData(jsonData);

            if (cachedData.isPresent()) {
                // Если данные есть в кеше, возвращаем их
                return mapper.readValue(cachedData.get().getData(), Map.class);
            }
            else
            {
                // Если данных нет в кеше возвращаем входные данные
                return params;
            }
        } catch (Exception e) {
            throw new RuntimeException("Ошибка обработки данных", e);
        }
    }
}
