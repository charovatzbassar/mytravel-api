package com.example.api.service;

import java.util.List;

public interface BaseService<Entity, Dto, Id> {
    List<Entity> getAll();
    Entity getById(Id id);
    Entity create(Dto data);
    Entity update(Id id, Dto data);
    Entity delete(Id id);
}
