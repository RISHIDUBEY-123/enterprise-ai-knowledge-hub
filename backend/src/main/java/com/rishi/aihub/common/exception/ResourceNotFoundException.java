package com.rishi.aihub.common.exception;

public class ResourceNotFoundException extends BusinessException {

    public ResourceNotFoundException(String resource, String id) {
        super(resource + " not found with id : " + id);
    }

}