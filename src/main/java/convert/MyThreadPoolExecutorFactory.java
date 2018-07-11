package convert;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MyThreadPoolExecutorFactory {
    private static volatile ThreadPoolExecutor threadPoolExecutor = null;

    private MyThreadPoolExecutorFactory(){}

    public static ThreadPoolExecutor getExecutor() {
        if(threadPoolExecutor == null) {
            synchronized (MyThreadPoolExecutorFactory.class) {
                if(threadPoolExecutor == null) {
                    threadPoolExecutor = new ThreadPoolExecutor(5, 10,
                            60, TimeUnit.SECONDS, new LinkedBlockingDeque());
                }
            }
        }
        return threadPoolExecutor;
    }
}
