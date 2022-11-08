package com.softserve.itacademy.json_model;

import com.softserve.itacademy.model.ToDo;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class JsonToDoRequest {
    private String title;

    @UniqueElements
    @NotNull
    @NotEmpty
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public JsonToDoRequest() {
    }

    public JsonToDoRequest(String title) {
        this.title = title;
    }

    public static ToDo toModel(JsonToDoRequest jsonToDoRequest){
        ToDo toDo = new ToDo();
        toDo.setTitle(jsonToDoRequest.getTitle());
        return toDo;
    }
}
