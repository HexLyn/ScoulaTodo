package org.scoula.todo.exception;

public class UsernameDuplicateException extends Exception {
    public UsernameDuplicateException() {
        super("Already existing user ID");
    }
}
