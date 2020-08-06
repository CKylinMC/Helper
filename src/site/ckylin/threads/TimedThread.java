/**
 * @Package: site.ckylin.soso.threadHelper
 * @author: CKylinMC
 * @description:
 * @date: 2020-06-26 14:38
 */
package site.ckylin.threads;

/**
 * 计时线程类
 */
public class TimedThread {
    private Thread target;
    private ThreadMsg msg;
    private int timeout = 5;
    private int status = 0;
    /**
     * The constant READY.
     */
    public static final int READY = 0;
    /**
     * The constant RUNNING.
     */
    public static final int RUNNING = 1;
    /**
     * The constant COMPLETED.
     */
    public static final int COMPLETED = 2;
    /**
     * The constant TIMEDOUT.
     */
    public static final int TIMEDOUT = 3;
    /**
     * The constant INTERRUPTED.
     */
    public static final int INTERRUPTED = 4;
    private Thread runnerThread;

    /**
     * Instantiates a new Timed thread.
     *
     * @param target the target
     * @param msg    the msg
     */
    public TimedThread(Thread target, ThreadMsg msg) {
        this.target = target;
        this.msg = msg;
    }

    /**
     * Instantiates a new Timed thread.
     *
     * @param target  the target
     * @param msg     the msg
     * @param timeout the timeout
     */
    public TimedThread(Thread target, ThreadMsg msg, int timeout) {
        this.target = target;
        this.timeout = timeout;
        this.msg = msg;
    }

    /**
     * Instantiates a new Timed thread.
     *
     * @param target the target
     * @param msg    the msg
     */
    public TimedThread(Runnable target, ThreadMsg msg) {
        this.target = new Thread(target);
        this.msg = msg;
    }

    /**
     * Instantiates a new Timed thread.
     *
     * @param target  the target
     * @param msg     the msg
     * @param timeout the timeout
     */
    public TimedThread(Runnable target, ThreadMsg msg, int timeout) {
        this.target = new Thread(target);
        this.timeout = timeout;
        this.msg = msg;
    }

    /**
     * 绑定通信消息类
     *
     * @param msg the msg
     */
    public void bind(ThreadMsg msg) {
        this.msg = msg;
    }

    /**
     * Get data string.
     *
     * @return the string
     */
    public String getData() {
        return msg.getData();
    }

    /**
     * 启动线程
     */
    public void start() {
        Runner Runner = new Runner();
        runnerThread = new Thread(Runner);
        runnerThread.setDaemon(true);
        runnerThread.start();
    }

    /**
     * Interrupt.
     */
    public void interrupt() {
        runnerThread.interrupt();
    }

    /**
     * 运行，并等待返回值
     *
     * @return the string
     */
    public String runAndWait() {
        start();
        try {
            runnerThread.join();
        } catch (InterruptedException ignored) {
        }
        return getData();
    }

    /**
     * Get status int.
     *
     * @return the int
     */
    public int getStatus() {
        return status;
    }

    /**
     * 运行容器线程
     */
    public class Runner implements Runnable {
        /**
         * Instantiates a new Runner.
         */
        public Runner() {
            target.setDaemon(true);
        }

        @Override
        public void run() {
            status = RUNNING;
            target.start();
            int waitMax = timeout * 1000;
            int waitCount = 0;
            while (!isInterrupted()) {
                if (msg.isOk()) break;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    break;
                }
                waitCount += 100;
                if (waitCount >= waitMax) {
                    status = TIMEDOUT;
                    break;
                }
            }
            if (isInterrupted()) status = INTERRUPTED;
            else if (status == RUNNING) status = COMPLETED;
        }

        private boolean interrupted = false;

        /**
         * Is interrupted boolean.
         *
         * @return the boolean
         */
        public boolean isInterrupted() {
            return interrupted;
        }

        /**
         * Interrupt.
         *
         * @param on the on
         */
        public void interrupt(boolean on) {
            this.interrupted = on;
        }
    }
}
