package by.brawl.util;

import org.slf4j.Logger;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public final class Exceptions {

	private Exceptions() {
		// Lock class from instantiation
	}

	public static AccessDeniedException produceAccessDenied(String message, Logger log) {
		AccessDeniedException e = new AccessDeniedException(message);
		log.error(message, e);
		return e;
	}

	public static IllegalArgumentException produceIllegalArgument(String message, Logger log) {
		IllegalArgumentException e = new IllegalArgumentException(message);
		log.error(message, e);
		return e;
	}

	public static IllegalStateException produceIllegalState(String message, Logger log) {
		IllegalStateException e = new IllegalStateException(message);
		log.error(message, e);
		return e;
	}

	public static NullPointerException produceNullPointer(String message, Logger log) {
		NullPointerException e = new NullPointerException(message);
		log.error(message, e);
		return e;
	}

	public static UsernameNotFoundException produceUsernameNotFound(String message, Logger log) {
		UsernameNotFoundException e = new UsernameNotFoundException(message);
		log.error(message, e);
		return e;
	}

	public static void logError(String message, Exception e, Logger log) {
		log.error(message, e);
	}
}
