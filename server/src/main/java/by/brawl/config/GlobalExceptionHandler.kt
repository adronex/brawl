package by.brawl.config

import by.brawl.controller.GenericController
import org.slf4j.LoggerFactory
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
open class GlobalExceptionHandler {

    @ExceptionHandler(Throwable::class)
    fun exceptionHandler(ex: Throwable): String {
        LOG.error(ex.message, ex)
        throw ex;
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(GenericController::class.java)!!
    }
}