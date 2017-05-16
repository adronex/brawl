package by.brawl.util;

import org.slf4j.Logger;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.text.MessageFormat;

public final class Exceptions {

	private Exceptions() {
		// Lock class from instantiation
	}

	public static AccessDeniedException produceAccessDenied(Logger log, String message, Object... messageArgs) {
		String formattedMessage = MessageFormat.format(message, messageArgs);
		AccessDeniedException e = new AccessDeniedException(formattedMessage);
		log.error(formattedMessage, e);
		return e;
	}

	public static IllegalArgumentException produceIllegalArgument(Logger log, String message, Object... messageArgs) {
		String formattedMessage = MessageFormat.format(message, messageArgs);
		IllegalArgumentException e = new IllegalArgumentException(formattedMessage);
		log.error(formattedMessage, e);
		return e;
	}

	public static IllegalStateException produceIllegalState(Logger log, String message, Object... messageArgs) {
		String formattedMessage = MessageFormat.format(message, messageArgs);
		IllegalStateException e = new IllegalStateException(formattedMessage);
		log.error(formattedMessage, e);
		return e;
	}

	public static NullPointerException produceNullPointer(Logger log, String message, Object... messageArgs) {
		String formattedMessage = MessageFormat.format(message, messageArgs);
		NullPointerException e = new NullPointerException(formattedMessage);
		log.error(formattedMessage, e);
		return e;
	}

	public static UsernameNotFoundException produceUsernameNotFound(Logger log, String message, Object... messageArgs) {
		String formattedMessage = MessageFormat.format(message, messageArgs);
		UsernameNotFoundException e = new UsernameNotFoundException(formattedMessage);
		log.error(formattedMessage, e);
		return e;
	}

	public static void logError(Logger log, Exception e, String message, Object... messageArgs) {
		String formattedMessage = MessageFormat.format(message, messageArgs);
		log.error(formattedMessage, e);
	}
}
