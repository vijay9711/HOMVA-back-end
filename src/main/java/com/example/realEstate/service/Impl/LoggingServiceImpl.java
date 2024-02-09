package com.example.realEstate.service.Impl;

import com.example.realEstate.entity.Log;
import com.example.realEstate.repository.LogRepository;
import com.example.realEstate.service.LoggingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoggingServiceImpl implements LoggingService {

    private final LogRepository logRepository;

    public LoggingServiceImpl(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Override
    @Transactional
    public void saveLog(Log log) {
        logRepository.save(log);
    }
}
