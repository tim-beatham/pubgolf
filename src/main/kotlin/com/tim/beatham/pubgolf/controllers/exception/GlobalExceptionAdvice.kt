package com.tim.beatham.pubgolf.controllers.exception

import com.tim.beatham.pubgolf.models.Response
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody

@ControllerAdvice
class GlobalExceptionAdvice {
    @ResponseBody
    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): Response<String?> {
        return Response.createErrorResponse(ex.message)
    }
}