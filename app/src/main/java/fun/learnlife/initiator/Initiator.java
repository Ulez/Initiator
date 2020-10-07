package fun.learnlife.initiator;

import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

import fun.learnlife.initiator.task.Task;

public class Initiator {

    private static Initiator mInstance = null;
    private Stack<Task> tmpStack = new Stack<>();
    private ArrayList<Task> taskAll = new ArrayList<>();
    private ArrayList<Task> taskSorted = new ArrayList<>();

    private Initiator() {

    }

    public static Initiator getInstance() {
        if (mInstance == null) {
            synchronized (Initiator.class) {
                if (mInstance == null) {
                    mInstance = new Initiator();
                }
            }
        }
        return mInstance;
    }

    /**
     * 添加各种任务。
     *
     * @param task
     * @return
     */
    public Initiator addTask(Task task) {
        taskAll.add(task);
        return mInstance;
    }

    /**
     * 开启初始化任务
     */
    public void start() {
        sort();
        ThreadUtil.executeHigh(new Runnable() {
            @Override
            public void run() {
                for (final Task t : taskSorted) {
                    if (!t.runOnMain()) {
                        ThreadUtil.executeHigh(t);
                    } else {
                        ThreadUtil.runOnUIThread(t);
                    }
                }
            }
        });
        Log.e("lcy","start tasks end");
    }


    /**
     * 拓扑排序，有向无环图。
     */
    private void sort() {
        Iterator<Task> it = taskAll.iterator();
        while (it.hasNext()) {
            Task task = it.next();
            task.setTasks(taskAll);
            if (task.dSize() == 0) {
                tmpStack.push(task);
                task.setVisit(true);
            }
            while (!tmpStack.isEmpty()) {
                Task tmp = tmpStack.pop();
                tmp.setVisit(true);
                taskSorted.add(tmp);
                //找出该任务的每个邻接任务，入度减去1.
                Iterator<Task> it2 = taskAll.iterator();
                while (it2.hasNext()) {
                    Task t = it2.next();
                    if (!t.isVisit() && t.depends(tmp.getClass()) && t.dSize() > 0) {
                        t.removeDep();
                        if (t.dSize() == 0) {
                            tmpStack.push(t);
                            t.setVisit(true);
                        }
                    }
                }
            }
        }
    }
}
