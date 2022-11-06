package com.wvoort.wc2022.predictservice.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.time.Instant;

class InstantSerializer implements JsonSerializer<Instant> {

    @Override
    public JsonElement serialize(Instant instant, Type srcType, JsonSerializationContext context) {
        return new JsonPrimitive(instant.toString());
    }
}
