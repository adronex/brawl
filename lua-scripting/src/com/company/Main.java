package com.company;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, ScriptException, NoSuchMethodException {
        callJs();
    }

    public static void callJs() throws FileNotFoundException, ScriptException, NoSuchMethodException {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");
        engine.eval(new FileReader("js/field.js"));
        Invocable invocable = (Invocable) engine;
        invocable.invokeFunction("buyField", 1, 1);
        invocable.invokeFunction("buyField", 1, 2);
        String response = (String)invocable.invokeFunction("buyField", 1, 3);
        System.out.println(response);
    }

    public static void callLua() {
        Globals globals = JsePlatform.standardGlobals();
        LuaValue chunk = globals.loadfile("lua/hello.lua");
        chunk.call();
    }
}