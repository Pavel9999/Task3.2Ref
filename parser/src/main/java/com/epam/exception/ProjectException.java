package com.epam.exception;

public class ProjectException extends Exception {

    private String _path;

    public String getPath() {
        return _path;
    }

    public ProjectException(String message, String path){
        super(message);
        _path = path;
    }


}
