package com.example.common

import grails.rest.Link

class ViewModel {

    /**
     * List of links the help with hypermedia (state transformation).
     * We are manually managing a list of links instead of using @Linkable.
     */
    Map<String, Link> links = [:]

    List<Message> messages = new ArrayList<Message>()

}
