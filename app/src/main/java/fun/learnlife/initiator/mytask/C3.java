package fun.learnlife.initiator.mytask;

import android.os.SystemClock;
import android.util.Log;

import fun.learnlife.initiator.task.Task;

public class C3 extends Task {
    public String TAG = "C3";

    @Override
    public void onRun() {
        Log.i("lcy", TAG + " start! depends on C1,C2 run on "+Thread.currentThread().getName());
        SystemClock.sleep((long) (Math.random() * 1000)+1000);
    }

    @Override
    protected void onFinish() {
        super.onFinish();
        Log.e("lcy", TAG + " finished in " + (eTime - sTime) + " ms");
    }

    @Override
    public void addDepends() {
        dependsTasks.add(C1.class);
        dependsTasks.add(C2.class);
    }
    @Override
    public String getMsg() {
        return TAG;
    }
}
