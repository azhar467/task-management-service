package com.azhar.taskmanagement.requestlogging;

import java.io.IOException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonConverter implements jakarta.persistence.AttributeConverter<String, String> {
  @Autowired
  ObjectMapper objectMapper;

  @Override
  public String convertToDatabaseColumn(String str) {
    String someClassJson = null;
    try {
      someClassJson = objectMapper.writeValueAsString(str);
    } catch (final JsonProcessingException e) {
      log.error("JSON writing error", e);
    }

    return someClassJson;
  }

  @Override
  public String convertToEntityAttribute(String str) {
    String someClass = null;
    if (StringUtils.isBlank(str)) {
      return someClass;
    }
    try {
      someClass = objectMapper.readValue(str, String.class);
    } catch (final IOException e) {
      log.error("JSON reading error", e);
    }
    return someClass;
  }
}
