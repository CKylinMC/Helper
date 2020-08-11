/**
 * @Package: site.ckylin.tools.console
 * @author: CKylinMC
 * @description:
 * @date: 2020-08-05 9:31
 */
package site.ckylin.tools.console;

import site.ckylin.tools.methods.Methods;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Objects;

/**
 * The type Console.
 */
public class Console {

    private static Console instance;
    private static Console lastInstance;

    /**
     * Gets instance.
     *
     * @return the instance
     */
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

    /**
     * Gets last instance.
     *
     * @return the last instance
     */
    public static Console getLastInstance() {
        if (Console.lastInstance == null) {
            new Console();
        }
        return lastInstance;
    }

    /**
     * Instantiates a new Console.
     */
    public Console() {
        Console.lastInstance = this;
    }

    private ArrayList<String> logs = new ArrayList<>();
    private String lastLine = "";

    private PrintStream out = System.out;

    /**
     * The Debug on.
     */
    public boolean debugOn = false;
    /**
     * The constant globalDebugOn.
     */
    public static boolean globalDebugOn = true;

    /**
     * The Mute.
     */
    public boolean mute = false;
    /**
     * The constant globalMute.
     */
    public static boolean globalMute = false;

    /**
     * Sets out.
     *
     * @param out the out
     */
    public void setOut(PrintStream out) {
        this.out = out;
    }

    /**
     * Gets out.
     *
     * @return the out
     */
    public PrintStream getOut() {
        return out;
    }

    /**
     * Reset out.
     */
    public void resetOut() {
        setOut(System.out);
    }

    /**
     * Is logging boolean.
     *
     * @return the boolean
     */
    public boolean isLogging() {
        return logs == null;
    }

    /**
     * Sets logging.
     *
     * @param on the on
     */
    public void setLogging(boolean on) {
        if (on) {
            logs = new ArrayList<>();
        } else {
            logs = null;
        }
    }

    /**
     * Gets logs.
     *
     * @return the logs
     */
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
                if (lastLine != null && !Objects.equals(lastLine, "")) {
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

    /**
     * Log.
     *
     * @param args the args
     */
    public void log(Object... args) {
        for (Object arg : args) {
            printProxy(arg.toString());
        }
        printlnProxy("");
    }

    /**
     * Warn.
     *
     * @param args the args
     */
    public void warn(Object... args) {
        printProxy("[WARNING] ");
        log(args);
    }

    /**
     * Info.
     *
     * @param args the args
     */
    public void info(Object... args) {
        printProxy("[INFO] ");
        log(args);
    }

    /**
     * Error.
     *
     * @param args the args
     */
    public void error(Object... args) {
        printProxy("[ERROR] ");
        log(args);
    }

    /**
     * Debug.
     *
     * @param obj  the obj
     * @param args the args
     */
    public void debug(Object obj, Object... args) {
        debug(obj.getClass().getSimpleName(), args);
    }

    /**
     * Debug.
     * Levels:
     * <ul>
     *     <li>0 - File name.</li>
     *     <li>1 - File name and line numbers.</li>
     *     <li>2 - Method name.</li>
     *     <li>3 - Class name.</li>
     *     <li>4 - Class name and method name.</li>
     * </ul>
     *
     * @param level SenderInfoLevel
     * @param args  the args
     */
    public void debug(Integer level, Object... args) {
        String sender = "";
        StackTraceElement caller = Methods.getCaller();
        switch (level) {
            default:
            case 0:
                sender = caller.getFileName();
                break;
            case 1:
                sender = caller.getFileName() + caller.getLineNumber();
                break;
            case 2:
                sender = caller.getMethodName();
                break;
            case 3:
                sender = caller.getClassName();
                break;
            case 4:
                sender = caller.getClassName() + "." + caller.getMethodName();
                break;
        }
        debug(sender, args);
    }

    /**
     * Debug.
     *
     * @param sender the sender
     * @param args   the args
     */
    public void debug(String sender, Object... args) {
        printDebugProxy("[" + sender + "] ");
        for (Object arg : args) {
            printDebugProxy(arg.toString());
        }
        printlnDebugProxy("");
    }

    /**
     * Code Debug.
     *
     * @param args the args
     */
    public void codedebug(Object... args) {
        printDebugProxy("[" + Thread.currentThread().getStackTrace()[2].getFileName() + ":" + Thread.currentThread().getStackTrace()[2].getLineNumber() + "] ");
        for (Object arg : args) {
            printDebugProxy(arg.toString());
        }
        printlnDebugProxy("");
    }
}
