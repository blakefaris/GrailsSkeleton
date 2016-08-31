package com.example.common

enum MessageType {
    CONFIRMATION,
    ERROR,
    INFORMATIONAL;

    static MessageType getValue(String messageType) {
        messageType ? valueOf(messageType.toUpperCase()) : null
    }

}
