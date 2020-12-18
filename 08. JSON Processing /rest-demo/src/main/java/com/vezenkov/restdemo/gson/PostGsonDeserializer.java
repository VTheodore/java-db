package com.vezenkov.restdemo.gson;

import com.google.gson.*;
import com.vezenkov.restdemo.exception.InvalidEntityException;
import com.vezenkov.restdemo.model.Post;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class PostGsonDeserializer implements JsonDeserializer<Post> {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    @Override
    public Post deserialize(JsonElement json, Type type,
                            JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        JsonObject jsonObject = json.getAsJsonObject();

        JsonElement jsonId = jsonObject.get("id");
        JsonElement jsonTile = jsonObject.get("title");
        JsonElement jsonContent = jsonObject.get("content");
        JsonElement jsonImageUrl = jsonObject.get("imageUrl");
        JsonElement jsonAuthorId = jsonObject.get("authorId");
        JsonElement jsonCreated = jsonObject.get("created");

        if(jsonTile == null || jsonContent == null || jsonImageUrl == null) {
            throw new InvalidEntityException("Title, content, and image URL are required.");
        }
        Post post = new Post(jsonTile.getAsString().toUpperCase(), jsonContent.getAsString(), jsonImageUrl.getAsString());
        if(jsonId != null) {
            post.setId(jsonId.getAsLong());
        }
        if(jsonAuthorId != null) {
            post.setAuthorId(jsonAuthorId.getAsLong());
        }
        if(jsonCreated != null) {
            try {
                post.setCreated(sdf.parse(jsonCreated.getAsString()));
            } catch (ParseException e) {
                throw new InvalidEntityException("Error parsing creation date: " + e.getMessage());
            }
        }
        return post;
    }
}