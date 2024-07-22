package org.scoula.todo.exception;

public class PasswordMissmatchException extends Exception {
    public PasswordMissmatchException() {
        super("incorrect password.");
    }
}
