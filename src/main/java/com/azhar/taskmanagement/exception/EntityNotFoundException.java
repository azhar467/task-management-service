package com.azhar.taskmanagement.exception;

public class EntityNotFoundException extends RuntimeException{

    public EntityNotFoundException(String entityName, Long entityId){
        super(entityName+" with Id "+entityId+" Not Found");
    }
}
