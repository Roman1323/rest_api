package com.softserve.itacademy.json_model;

import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.model.User;

import java.time.LocalDateTime;

public class JsonToDoResponse {
    private long id;

    private String title;

    private LocalDateTime created_at;

    private long owner_id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.created_at = createdAt;
    }

    public long getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(long owner_id) {
        this.owner_id = owner_id;
    }

    public JsonToDoResponse() {
    }

    public JsonToDoResponse(long id, String title, LocalDateTime created_at, long owner_id) {
        this.id = id;
        this.title = title;
        this.created_at = created_at;
        this.owner_id = owner_id;
    }

    public static JsonToDoResponse toJson (ToDo toDo){
        return new JsonToDoResponse(toDo.getId(),toDo.getTitle(),toDo.getCreatedAt(),toDo.getOwner().getId());
    }
}
