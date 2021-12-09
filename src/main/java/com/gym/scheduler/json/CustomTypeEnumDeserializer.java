package com.gym.scheduler.json;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.gym.scheduler.models.ClassType;
import lombok.NoArgsConstructor;

import java.io.IOException;

public class CustomTypeEnumDeserializer extends StdDeserializer<ClassType> {
    protected CustomTypeEnumDeserializer(){
        this(null);
    }
    protected CustomTypeEnumDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public ClassType deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String type = null;
        if(node.textValue() != null){
            type = node.textValue();
        }
        for(ClassType classType: ClassType.values()){
            if(classType.getType().equals(type)){
                return classType;
            }
        }
        return ClassType.DANCE;
    }
}
