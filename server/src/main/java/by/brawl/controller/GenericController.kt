package by.brawl.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

open class GenericController<S>(protected val service: S) {

//    @ExceptionHandler(Exception::class)
//    @ResponseBody
//    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
//    fun handleException(ex: Exception) {
//        throw ex;
//    }
}