package com.company;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

import javax.script.*;

public class Main {

    public static void main(String[] args) throws ScriptException {

        Globals globals = JsePlatform.standardGlobals();
        LuaValue chunk = globals.loadfile("scripts/hello.lua");
        chunk.call();
    }
}
