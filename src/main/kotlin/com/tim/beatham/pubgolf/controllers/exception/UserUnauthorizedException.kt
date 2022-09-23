package com.tim.beatham.pubgolf.controllers.exception

import com.tim.beatham.pubgolf.models.Response
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

class UserUnauthorizedException : RuntimeException {
    constructor() : super("User is unauthorized") {}
}

@ControllerAdvice
class UserUnauthorizedExceptionAdvice {
    @ResponseBody
    @ExceptionHandler(UserUnauthorizedException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun userAlreadyExistsHandler(userException: UserUnauthorizedException): Response<String?> {
        return Response.createErrorResponse(userException.message)
    }
}