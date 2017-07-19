package by.brawl.util

import org.slf4j.Logger
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.userdetails.UsernameNotFoundException

import java.text.MessageFormat

object Exceptions {

    fun produceAccessDenied(log: Logger, message: String, vararg messageArgs: Any): AccessDeniedException {
        val formattedMessage = MessageFormat.format(message, *messageArgs)
        val e = AccessDeniedException(formattedMessage)
        log.error(formattedMessage, e)
        return e
    }

    fun produceIllegalArgument(log: Logger, message: String, vararg messageArgs: Any): IllegalArgumentException {
        val formattedMessage = MessageFormat.format(message, *messageArgs)
        val e = IllegalArgumentException(formattedMessage)
        log.error(formattedMessage, e)
        return e
    }

    fun produceIllegalState(log: Logger, message: String, vararg messageArgs: Any): IllegalStateException {
        val formattedMessage = MessageFormat.format(message, *messageArgs)
        val e = IllegalStateException(formattedMessage)
        log.error(formattedMessage, e)
        return e
    }

    fun produceNullPointer(log: Logger, message: String, vararg messageArgs: Any): NullPointerException {
        val formattedMessage = MessageFormat.format(message, *messageArgs)
        val e = NullPointerException(formattedMessage)
        log.error(formattedMessage, e)
        return e
    }

    fun produceUsernameNotFound(log: Logger, message: String, vararg messageArgs: Any): UsernameNotFoundException {
        val formattedMessage = MessageFormat.format(message, *messageArgs)
        val e = UsernameNotFoundException(formattedMessage)
        log.error(formattedMessage, e)
        return e
    }

    fun logError(log: Logger, e: Exception, message: String, vararg messageArgs: Any) {
        val formattedMessage = MessageFormat.format(message, *messageArgs)
        log.error(formattedMessage, e)
    }
}
