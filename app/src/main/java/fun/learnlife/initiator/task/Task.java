package fun.learnlife.initiator.task;

import android.os.Process;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import androidx.annotation.IntRange;

public abstract class Task implements Runnable {

    private String TAG;
    private ArrayList<Task> mTasks = null;
    private int priority = 0;
    private boolean visit;
    private String msg;
    protected Random random = new Random();

    /**
     * size为入度
     */
    protected List<Class<? extends Task>> dependsTasks = new ArrayList<>();
    private int dSize = 0;

    private CountDownLatch mDepends;
    protected long sTime;
    protected long eTime;

    public Task() {
        addDepends();
        dSize = dependsTasks.size();
        mDepends = new CountDownLatch(dSize);
    }

    public boolean runOnMain() {
        return false;
    }

    public boolean runOnIdle() {
        return false;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int dSize() {
        return dSize;
    }

    public void waitBefore() {
        try {
            mDepends.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void notifyFinished() {
        mDepends.countDown();
    }

    @Override
    public void run() {
        waitBefore();
        onStart();
        onRun();
        onFinish();
        notifyAfter();//通知后续任务，前置的已经完成蛤
    }

    public void setTasks(ArrayList<Task> mTasks) {
        this.mTasks = mTasks;
    }

    private void notifyAfter() {
        if (mTasks != null && mTasks.size() > 0) {
            for (Task task : mTasks) {
                if (task.depends(this.getClass())) {
                    task.notifyFinished();
                }
            }
        }
    }

    protected void onStart() {
        sTime = System.currentTimeMillis();
    }

    protected abstract void onRun();

    protected void onFinish() {
        eTime = System.currentTimeMillis();
    }

    /**
     * 添加前置任务
     */
    public void addDepends() {

    }

    public boolean depends(Class clz) {
        if (dependsTasks.contains(clz)) {
            return true;
        }
        return false;
    }

    /**
     * 入度减去1
     */
    public void removeDep() {
        dSize--;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @IntRange(from = Process.THREAD_PRIORITY_FOREGROUND, to = Process.THREAD_PRIORITY_LOWEST)
    public int getPriority() {
        return priority;
    }

    public boolean isVisit() {
        return visit;
    }

    public void setVisit(boolean visit) {
        this.visit = visit;
    }

    public String getMsg() {
        return TAG;
    }
}
