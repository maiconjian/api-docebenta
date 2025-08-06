package com.doce.benta.generic.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public abstract class AbstractServiceDao<T extends AbstractModel,GRep extends CrudRepository>{

    @Autowired
    protected GRep repository;

    public T getOne(UUID id){
        if(id == null){
            return null;
        }
        return (T) repository.findById(id).orElse(null);
    }

    public T save(T entity){
        return (T) repository.save(entity);
    }

    public void delete(T entity){
        this.repository.delete(entity);
    }
}

