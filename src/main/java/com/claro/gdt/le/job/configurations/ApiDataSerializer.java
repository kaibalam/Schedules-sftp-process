package com.claro.gdt.le.job.configurations;

import com.claro.gdt.le.job.models.responses.ApiData;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class ApiDataSerializer extends JsonSerializer<Object> {

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        ApiData apiData = (ApiData)value;
        gen.writeObjectField(apiData.getName(), apiData.getData());
        gen.writeEndObject();
    }
}
