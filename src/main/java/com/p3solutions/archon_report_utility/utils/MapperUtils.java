package com.p3solutions.archon_report_utility.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import static com.p3solutions.archon_report_utility.constants.ExceptionConstants.ERROR_LOG_TEMPLATE;

@Slf4j
public class MapperUtils {
    ModelMapper modelMapper = new ModelMapper();

    public MapperUtils() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public <T, D> D map(final T entity, Class<D> outClass) {
        return modelMapper.map(entity, outClass);
    }

    public <T, D> List<D> map(final Collection<T> entityList, Class<D> outCLass) {
        return entityList.stream().map(entity -> map(entity, outCLass)).toList();
    }

    public <T> byte[] map(final T entity) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsBytes(entity);
        } catch (JsonProcessingException e) {
            log.error(ERROR_LOG_TEMPLATE, e.getMessage());
        }
        return null;
    }

    public <T> T map(final String entity, Class<T> outClass) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(entity, outClass);
        } catch (IOException e) {
            log.error(ERROR_LOG_TEMPLATE, e.getMessage());
        }
        return null;
    }
}
