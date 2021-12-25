package com.example.anticafe.exception;

public final class RoomNotFoundException extends RuntimeException{

    public RoomNotFoundException() {super("Room not found");}
}
