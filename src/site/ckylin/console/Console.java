/**
 * @Package: site.ckylin.console
 * @author: CKylinMC
 * @description:
 * @date: 2020-08-05 9:31
 */
package site.ckylin.console;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Objects;

public class Console {

    private static Console instance;
    private static Console lastInstance;

    public static Console getInstance() {
        if (Console.instance == null) {
            if (Console.lastInstance != null) {
                Console.instance = Console.lastInstance;
            } else {
                Console.instance = new Console();
            }
        }
        return instance;
    }

    public static Console getLastInstance() {
        if (Console.lastInstance == null) {
            new Console();
        }
        return lastInstance;
    }

    public Console() {
        Console.lastInstance = this;
    }

    private ArrayList<String> logs;
    private String lastLine = "";

    private PrintStream out = System.out;

    public boolean debugOn = false;
    public static boolean globalDebugOn = true;

    public boolean mute = false;
    public static boolean globalMute = false;

    public void setOut(PrintStream out) {
        this.out = out;
    }

    public PrintStream getOut() {
        return out;
    }

    public void resetOut() {
        setOut(System.out);
    }

    public boolean isLogging() {
        return logs == null;
    }

    public void setLogging(boolean on) {
        if (on) {
            logs = new ArrayList<>();
        } else {
            logs = null;
        }
    }

    public ArrayList<String> getLogs() {
        if (isLogging()) {
            if (!Objects.equals(lastLine, "")) {
                logs.add(lastLine);
                lastLine = "";
            }
            return logs;
        } else {
            return new ArrayList<>();
        }
    }

    private void addLog(String s) {
        addLog(s, false);
    }

    private void addLog(String s, boolean newLine) {
        if (isLogging()) {
            if (newLine) {
                if (!Objects.equals(lastLine, "")) {
                    logs.add(lastLine);
                    lastLine = "";
                }
                logs.add(s);
            } else {
                lastLine += s;
            }
        }
    }

    private void printProxy(String s) {
        if (!(Console.globalMute || mute)) System.out.print(s);
        addLog(s);
    }

    private void printlnProxy(String s) {
        if (!(Console.globalMute || mute)) System.out.println(s);
        addLog(s, true);
    }

    private void printDebugProxy(String s) {
        if (!Console.globalDebugOn) return;
        if (!debugOn) return;
        printProxy(s);
    }

    private void printlnDebugProxy(String s) {
        if (!Console.globalDebugOn) return;
        if (!debugOn) return;
        printlnProxy(s);
    }

    public void log(Object... args) {
        for (Object arg : args) {
            printProxy(arg.toString());
        }
        printlnProxy("");
    }

    public void warn(Object... args) {
        printProxy("[WARNING] ");
        log(args);
    }

    public void error(Object... args) {
        printProxy("[ERROR] ");
        log(args);
    }

    public void debug(Object obj, Object... args) {
        debug(obj.getClass().getSimpleName(), args);
    }

    public void debug(String sender, Object... args) {
        printDebugProxy("[" + sender + "] ");
        for (Object arg : args) {
            printDebugProxy(arg.toString());
        }
        printlnDebugProxy("");
    }
}
