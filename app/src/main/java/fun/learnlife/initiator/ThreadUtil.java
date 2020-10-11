package fun.learnlife.initiator;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadUtil {
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = Math.max(2, CPU_COUNT - 1);
    private static final int MAX_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final long KEEP_ALIVE_TIME = 60L;
    private static Handler sUIHandler = new Handler(Looper.getMainLooper());

    /**
     * 在UI线程执行
     *
     * @param runnable
     */
    public static void runOnUIThread(Runnable runnable) {
        runOnUIThread(runnable, 0);
    }

    public static void runOnUIThread(Runnable runnable, long delayMills) {
        if (runnable != null) {
            if (Thread.currentThread() == Looper.getMainLooper().getThread() && delayMills == 0) {
                runnable.run();
            } else {
                sUIHandler.postDelayed(runnable, delayMills);
            }
        }
    }

    /**
     * 核心线程
     * 高优先级
     */
    private static ExecutorService mHighService = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

    /**
     * 非核心线程
     * 适合IO密集型任务
     * 低优先级
     */
    private static ExecutorService mIoService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, KEEP_ALIVE_TIME, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());


    private static ExecutorService mLowService = Executors.newSingleThreadScheduledExecutor();


    public static void executeHigh(Runnable runnable) {
        mHighService.execute(runnable);
    }

    public static void executeIo(Runnable runnable) {
        mIoService.execute(runnable);
    }

    public static void executeLow(Runnable runnable) {
        mLowService.execute(runnable);
    }

}
