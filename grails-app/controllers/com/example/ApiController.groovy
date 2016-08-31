package com.example

import com.example.common.MessageType
import com.example.controllers.HttpStatusViewMappingType
import com.example.common.Message
import grails.converters.JSON
import grails.converters.XML
import org.grails.web.errors.GrailsWrappedRuntimeException
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.http.HttpStatus

class ApiController {

    MessageSource messageSource

    def index() {
        notFound404()
    }

    def options() {
        render ''
    }

    def handleException(Exception ex) {
        log.error('Exception', ex)
        response.status = HttpStatus.INTERNAL_SERVER_ERROR.value()
        renderResponse createErrorViewModel('There was an error with the request.'), HttpStatusViewMappingType.INTERNAL_SERVER_ERROR.view
    }

    def badRequest400() {
        logError(HttpStatus.BAD_REQUEST)
        response.status = HttpStatus.BAD_REQUEST.value()
        renderResponse createErrorViewModel('Bad Request'), HttpStatusViewMappingType.BAD_REQUEST.view
    }

    def unauthorized401() {
        logError(HttpStatus.UNAUTHORIZED)
        response.status = HttpStatus.UNAUTHORIZED.value()
        renderResponse createErrorViewModel('Unauthorized'), HttpStatusViewMappingType.UNAUTHORIZED.view
    }

    def forbidden403() {
        logError(HttpStatus.FORBIDDEN)
        response.status = HttpStatus.FORBIDDEN.value()
        renderResponse createErrorViewModel('Forbidden'), HttpStatusViewMappingType.METHOD_NOT_ALLOWED.view
    }

    def notFound404() {
        logError(HttpStatus.NOT_FOUND)
        response.status = HttpStatus.NOT_FOUND.value()
        renderResponse createErrorViewModel('Not Found'), HttpStatusViewMappingType.NOT_FOUND.view
    }

    def methodNotAllowed405() {
        logError(HttpStatus.METHOD_NOT_ALLOWED)
        response.status = HttpStatus.METHOD_NOT_ALLOWED.value()
        renderResponse createErrorViewModel('Method Not Allowed'), HttpStatusViewMappingType.BAD_REQUEST.view
    }

    def serverError500() {
        logError(HttpStatus.INTERNAL_SERVER_ERROR)
        response.status = HttpStatus.INTERNAL_SERVER_ERROR.value()
        renderResponse createErrorViewModel('Internal Server Error'), HttpStatusViewMappingType.INTERNAL_SERVER_ERROR.view
    }

    def unavailable503() {
        logError(HttpStatus.SERVICE_UNAVAILABLE)
        response.status = HttpStatus.SERVICE_UNAVAILABLE.value()
        renderResponse createErrorViewModel('Internal Server Error'), HttpStatusViewMappingType.SERVICE_UNAVAILABLE.view
    }

    List<Message> processCommandErrors(command) {
        List<Message> messages = []
        command?.errors?.allErrors?.each {
            String message = messageSource.getMessage(it, LocaleContextHolder.locale)
            if (messages.size() > 0) {
                String duplicate = messages.find {
                    return it.description.equalsIgnoreCase(message)
                }
                duplicate ?: messages.add(new Message(MessageType.ERROR, message))
            } else {
                messages.add(new Message(MessageType.ERROR, message))
            }
        }

        return messages
    }

    void logError(HttpStatus httpStatus) {
        logException()
        log.error(['status': httpStatus.value(), 'uri': request.forwardURI, 'Content-Type': request.getHeader("Content-Type")])
    }

    void logException() {
        def exception = request.getAttribute('exception')
        if (exception && exception instanceof Throwable) {
            if (exception instanceof GrailsWrappedRuntimeException) {
                log.error "exception $exception.className, line $exception.lineNumber has throw $exception.cause"
            }
            log.error('Error controller caught exception', exception)
        }
    }

    void redirectOrRenderResponse(RedirectParams redirectParams) {
        request.withFormat {
            json {
                render redirectParams.model as JSON
            }
            html {
                flash.model = redirectParams.model
                redirect controller: redirectParams.controller, action: redirectParams.action, params: redirectParams.params
            }
            multipartForm {
                // NOTE: Even with grails.mime.use.accept.header set to true, it was not being honored, so manually check.
                // This allows correct response for API requests when uploading files.
                if (request.getHeader('Accept') == 'application/json') {
                    render redirectParams.model as JSON
                } else {
                    flash.model = redirectParams.model
                    redirect controller: redirectParams.controller, action: redirectParams.action, params: redirectParams.params
                }
            }
            xml {
                render redirectParams.model as XML
            }
            '*' {
                flash.model = redirectParams.model
                redirect controller: redirectParams.controller, action: redirectParams.action, params: redirectParams.params
            }
        }
    }

    void renderResponse(Object viewModel, String view = HttpStatusViewMappingType.UNAUTHORIZED.view) {
        // This relies on request.format, which really is request.getHeader('Content-Type').
        // request.withFormat is common for Grails APIs?
        // If so I don't agree with it as the request.getHeader('Accept') should be used for the response.
        request.withFormat {
            json {
                render viewModel as JSON
            }
            html {
                render view: view, model: viewModel
            }
            multipartForm {
                // NOTE: Even with grails.mime.use.accept.header set to true, it was not being honored, so manually check.
                // This allows correct response for API requests when uploading files.
                if (request.getHeader('Accept') == 'application/json') {
                    render viewModel as JSON
                } else {
                    render view: view, model: viewModel
                }
            }
            xml {
                render viewModel as XML
            }
            '*' {
                render view: view, model: viewModel
            }
        }
    }

    Message createErrorViewModel(String message) {
        new Message(MessageType.ERROR, message)
    }

    protected List<Message> getViewModelMessagesFromFlash() {
        flash?.model?.viewModel?.messages ?: []
    }
}

class RedirectParams {
    String controller
    String action
    Map<String, String> params
    Map<String, Object> model
}
