/**
 * @Package: site.ckylin.tools.methods
 * @author: CKylinMC
 * @description:
 * @date: 2020-08-05 9:34
 */
package site.ckylin.tools.methods;

/**
 * The type Methods.
 */
public class Methods {

    //https://www.cnblogs.com/fangzhaolee/archive/2012/07/14/2591566.html
    /*public static String getCaller()
    {
        int i;
        StackTraceElement[] stack = (new Throwable()).getStackTrace();
        for (i=0; i < stack.length; i++)
        {
            StackTraceElement ste=stack[i];
            System.out.println(ste.getClassName()+"."+ste.getMethodName()+"(...);");
            System.out.println(i+"--"+ste.getMethodName());
            System.out.println(i+"--"+ste.getFileName());
            System.out.println(i+"--"+ste.getLineNumber());
        }
    }*/

    /**
     * Get caller stack trace element.
     *
     * @return the stack trace element
     */
    public static StackTraceElement getCaller() {
        return Thread.currentThread().getStackTrace()[3];
    }

    /**
     * Get self element info stack trace element.
     *
     * @return the stack trace element
     */
    public static StackTraceElement getSelfElementInfo() {
        return Thread.currentThread().getStackTrace()[2];
    }

    /**
     * Get full stack stack trace element [ ].
     *
     * @return the stack trace element [ ]
     */
    public static StackTraceElement[] getFullStack() {
        return Thread.currentThread().getStackTrace();
    }
}
