package by.brawl.util

import org.slf4j.Logger
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.userdetails.UsernameNotFoundException

object Exceptions {

    fun produceAccessDenied(log: Logger, message: String): AccessDeniedException {
        val e = AccessDeniedException(message)
        log.error(message, e)
        return e
    }

    fun produceIllegalArgument(log: Logger, message: String): IllegalArgumentException {
        val e = IllegalArgumentException(message)
        log.error(message, e)
        return e
    }

    fun produceIllegalState(log: Logger, message: String): IllegalStateException {
        val e = IllegalStateException(message)
        log.error(message, e)
        return e
    }

    fun produceNullPointer(log: Logger, message: String): NullPointerException {
        val e = NullPointerException(message)
        log.error(message, e)
        return e
    }

    fun produceUsernameNotFound(log: Logger, message: String): UsernameNotFoundException {
        val e = UsernameNotFoundException(message)
        log.error(message, e)
        return e
    }

    fun logError(log: Logger, e: Exception, message: String) {
        log.error(message, e)
    }

}