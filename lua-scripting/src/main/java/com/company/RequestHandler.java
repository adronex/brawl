package com.company;

import spark.Request;

import javax.script.ScriptException;
import java.util.HashMap;
import java.util.Map;

public class RequestHandler {
	private ScriptCaller scriptCaller = new ScriptCaller();
	private Map<String, String> usersSessions = new HashMap<>();

	public RequestHandler() throws ScriptException {
	}

	public synchronized String handleRequest(Request request) throws ScriptException, NoSuchMethodException {
		scriptCaller.setState(usersSessions.get(request.ip()));
		String gameState = scriptCaller.executeCommand(request.body());
		usersSessions.put(request.ip(), gameState);
		return gameState;
	}
}
