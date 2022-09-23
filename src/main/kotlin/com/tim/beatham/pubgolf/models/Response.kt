package com.tim.beatham.pubgolf.models

import com.fasterxml.jackson.annotation.JsonValue
import java.util.StringJoiner

enum class Status {
    Success,
    Failure;

    @JsonValue
    fun toLower(): String {
        return this.toString().lowercase()
    }
}

open class Response<T>(val status: Status, val body: T) {
    companion object {
        fun <T> createErrorResponse(body: T) = Response(Status.Failure, body)
        fun <T> createSuccessResponse(body: T) = Response(Status.Success, body)
    }
}