package com.tim.beatham.pubgolf.controllers.exception

import com.tim.beatham.pubgolf.models.Response
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

class UserNotFoundException : RuntimeException {
    constructor(id: String) : super("User with id $id not found") {}
}

@ControllerAdvice
class UserNotFoundExceptionAdvice {
    @ResponseBody
    @ExceptionHandler(UserNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun userAlreadyExistsHandler(userException: UserNotFoundException): Response<String?> {
        return Response.createErrorResponse(userException.message)
    }
}