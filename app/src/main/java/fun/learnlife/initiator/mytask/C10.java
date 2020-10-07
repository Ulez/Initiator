package fun.learnlife.initiator.mytask;

import android.os.SystemClock;
import android.util.Log;

import fun.learnlife.initiator.task.Task;

public class C10 extends Task {
    public String TAG = "C10";

    @Override
    public void onRun() {
        Log.i("lcy", TAG + " start! depends on C7,C9 run on "+Thread.currentThread().getName());
        SystemClock.sleep((long) (Math.random() * 1000)+1000);
    }

    @Override
    protected void onFinish() {
        super.onFinish();
        Log.e("lcy", TAG + " finished in " + (eTime - sTime) + " ms");
    }

    @Override
    public void addDepends() {
        dependsTasks.add(C7.class);
        dependsTasks.add(C9.class);
    }
    @Override
    public String getMsg() {
        return TAG;
    }
}
