/**
 * @Package: site.ckylin.threads
 * @author: CKylinMC
 * @description:
 * @date: 2020-06-26 14:39
 */
package site.ckylin.threads;

/**
 * 线程通信消息类
 */
public class ThreadMsg {
    private String data;
    private boolean ok = false;

    /**
     * Gets data.
     *
     * @return the data
     */
    public String getData() {
        return data;
    }

    /**
     * Sets data.
     *
     * @param data the data
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * Is ok boolean.
     *
     * @return the boolean
     */
    public boolean isOk() {
        return ok;
    }

    /**
     * Sets ok.
     *
     * @param ok the ok
     */
    public void setOk(boolean ok) {
        this.ok = ok;
    }
}
