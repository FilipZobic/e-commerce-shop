package org.zobic.ecommerceshopapi.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class WhiteSpaceRemovalDeserializer extends JsonDeserializer<String> {
    @Override
    public String deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        String value = jp.getValueAsString();
        try {
            String trimmedValue = value.trim();
            return (trimmedValue.length() != 0 ? trimmedValue : null);
        } catch (Exception e) {
            return null;
        }
    }
}
