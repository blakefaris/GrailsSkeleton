package com.example.common

class Message {

    MessageType type
    String description
    String title
    int code

    Message(Throwable e) {
        this.type = MessageType.ERROR
        this.description = e != null ? e.getMessage() : ""
    }

    Message(MessageType type, String description) {
        this.type = type
        this.description = description
    }

    Message(MessageType type, String description, String title) {
        this.type = type
        this.description = description
        this.title = title
    }

    Message(MessageType type, int code) {
        this.type = type
        this.code = code
    }

    Message(MessageType type, String description, int code) {
        this.type = type
        this.description = description
        this.code = code
    }

    Message(MessageType type, String description, int code, String errorKey) {
        this.type = type
        this.description = description
        this.code = code
    }

    Message(MessageType type, String description, String title, int code) {
        this.type = type
        this.description = description
        this.title = title
        this.code = code
    }

    Message(MessageType type, String description, String title, int code, String errorKey) {
        this.type = type
        this.description = description
        this.title = title
        this.code = code
    }

    boolean isInformational() {
        this.type.equals(MessageType.INFORMATIONAL)
    }

    boolean isError() {
        this.type.equals(MessageType.ERROR)
    }

    boolean isConfirmation() {
        this.type.equals(MessageType.CONFIRMATION)
    }

    @Override
    String toString() {
        return description
    }
}
