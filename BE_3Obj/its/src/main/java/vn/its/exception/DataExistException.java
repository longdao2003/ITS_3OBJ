package vn.its.exception;

public class DataExistException extends RuntimeException{
    public DataExistException(String message){
        super(message);
    }
}
