package com.example.demo.service;

import com.example.demo.model.RequestData;
import com.example.demo.repository.RequestDataRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = "dataCache")
public class DataService {

    private static final Logger logger = LoggerFactory.getLogger(DataService.class);

    @Autowired
    private RequestDataRepository repository;

    @Cacheable(key = "#data.hashCode()", unless = "#result == null")
    public Optional<RequestData> findByData(String data) {
        logger.info("Поиск данных в базе данных для: {}", data);
        ObjectMapper objectMapper = new ObjectMapper();
        return repository.findAll().stream()
                .filter(entry -> {
                    try {
                        // Сравнение объектов JSON для устойчивости
                        return objectMapper.readTree(entry.getData()).equals(objectMapper.readTree(data));
                    } catch (Exception e) {
                        return false;
                    }
                })
                .findFirst();
    }

    @CachePut(key = "#data.hashCode()")
    public RequestData saveData(String data) {
        RequestData requestData = new RequestData();
        requestData.setData(data);
        return repository.save(requestData);
    }
}
