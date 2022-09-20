package com.tim.beatham.pubgolf.configuration

import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.client.HttpClientErrorException.Unauthorized

@ControllerAdvice
class UnauthorizedControllerAdvice {
    @ExceptionHandler(Unauthorized::class)
    @ResponseBody
    fun exception(unauthorized: Unauthorized): String {
        return "{ status: 'failure' }"
    }
}