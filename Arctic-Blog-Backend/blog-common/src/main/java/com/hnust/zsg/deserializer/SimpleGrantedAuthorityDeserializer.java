package com.hnust.zsg.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;

/**
 * 实现SimpleGrantedAuthority支持反序列化
 */
public class SimpleGrantedAuthorityDeserializer extends StdDeserializer<SimpleGrantedAuthority> {
    public SimpleGrantedAuthorityDeserializer() {
        super(SimpleGrantedAuthority.class);
    }
    @Override
    public SimpleGrantedAuthority deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode tree = p.getCodec().readTree(p);
        return new SimpleGrantedAuthority(tree.get("authority").textValue());
    }
}