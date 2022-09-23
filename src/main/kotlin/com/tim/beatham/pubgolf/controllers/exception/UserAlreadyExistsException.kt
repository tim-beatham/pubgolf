package com.tim.beatham.pubgolf.controllers.exception

import com.tim.beatham.pubgolf.models.Response
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

class UserAlreadyExistsException : RuntimeException {
    constructor(email: String) : super("User with email $email already exists") {}
}

@ControllerAdvice
class UserAlreadyExistsAdvice {
    @ResponseBody
    @ExceptionHandler(UserAlreadyExistsException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun userAlreadyExistsHandler(userException: UserAlreadyExistsException): Response<String?> {
        return Response.createErrorResponse(userException.message)
    }
}