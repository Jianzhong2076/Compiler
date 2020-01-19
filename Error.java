package com.zccp.tongyin;

public class Error {
    private int code;
    private String message;
    private String suggestion;
    private Position position;



    public Error(int code, String message, String suggestion, Position position) {
        this.code = code;
        this.message = message;
        this.suggestion = suggestion;
        this.position = position;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override   // 重写父类方法
    public String toString() {
        return "\r\nError{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", suggestion='" + suggestion + '\'' +
                ", position=" + position +
                '}';
    }
}
