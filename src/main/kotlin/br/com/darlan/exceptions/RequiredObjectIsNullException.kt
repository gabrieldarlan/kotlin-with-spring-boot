package br.com.darlan.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class RequiredObjectIsNullException : RuntimeException {
    constructor() : super("It is not allowed to persit a null object")
    constructor(exception: String?) : super(exception)
}