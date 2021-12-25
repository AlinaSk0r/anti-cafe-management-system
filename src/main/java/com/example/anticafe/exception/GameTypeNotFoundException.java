package com.example.anticafe.exception;

public final class GameTypeNotFoundException extends RuntimeException{

    public GameTypeNotFoundException() {super("Game type not found");}
}
