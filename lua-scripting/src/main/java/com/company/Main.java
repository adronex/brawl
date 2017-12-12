package com.company;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.ScriptException;
import java.io.IOException;
import java.net.URISyntaxException;

import static spark.Spark.*;

public class Main {

	private static final Logger LOG = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) throws ScriptException {
		try {
			ScriptCaller scriptCaller = new ScriptCaller();
			initSpark(scriptCaller);
		} catch (IOException | URISyntaxException e) {
			LOG.error("Application init error. Can not read scripts into string.", e);
		}
	}

	private static void initSpark(ScriptCaller scriptCaller) {
		options("/*", (request, response) -> {
			String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");

			if (accessControlRequestHeaders != null) {
				response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
			}
			String accessControlRequestMethod = request.headers("Access-Control-Request-Method");

			if (accessControlRequestMethod != null) {
				response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
			}
			return "OK";
		});
		before((request, response) -> response.header("Access-Control-Allow-Origin", "*"));
		post("/farm/execute", (req, res) -> scriptCaller.callJs(req.body()));
	}
}