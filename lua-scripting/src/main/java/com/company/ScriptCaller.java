package com.company;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ScriptCaller {

	private final Invocable invocable;

	public ScriptCaller() throws ScriptException, IOException, URISyntaxException {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("js");
		engine.eval(concatFiles());
		this.invocable = (Invocable) engine;
	}

	private String concatFiles() throws IOException, URISyntaxException {
		String result = "";
		result += new String(Files.readAllBytes(Paths.get(ClassLoader.getSystemResource("js/api.js").toURI())));
		result += new String(Files.readAllBytes(Paths.get(ClassLoader.getSystemResource("js/objects.js").toURI())));
		result += new String(Files.readAllBytes(Paths.get(ClassLoader.getSystemResource("js/bag.js").toURI())));
		result += new String(Files.readAllBytes(Paths.get(ClassLoader.getSystemResource("js/field.js").toURI())));
		return result;
	}

	public String callJs(String requestBody) throws ScriptException, NoSuchMethodException {
		return (String)invocable.invokeFunction("commandHandler", requestBody);
	}

//	public void callLua() {
//		Globals globals = JsePlatform.standardGlobals();
//		LuaValue chunk = globals.loadfile("lua/hello.lua");
//		chunk.call();
//	}
}
