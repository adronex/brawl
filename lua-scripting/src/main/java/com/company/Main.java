package com.company;

import javax.script.ScriptException;

import static spark.Spark.*;

public class Main {

	public static void main(String[] args) throws ScriptException {
		ScriptCaller scriptCaller = new ScriptCaller();
		initSpark(scriptCaller);
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