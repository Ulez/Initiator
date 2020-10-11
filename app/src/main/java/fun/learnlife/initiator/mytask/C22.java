package fun.learnlife.initiator.mytask;

import android.os.SystemClock;
import android.util.Log;

import fun.learnlife.initiator.task.Task;

public class C22 extends Task {
    public String TAG = "C22";

    @Override
    public void onRun() {
        Log.i("lcy", TAG + " start! run on "+Thread.currentThread().getName());
        SystemClock.sleep((long) (Math.random() * 1000)+1000);
    }

    @Override
    protected void onFinish() {
        super.onFinish();
        Log.e("lcy", TAG + " finished in " + (eTime - sTime) + " ms");
    }

    @Override
    public boolean runOnMain() {
        return true;
    }

    @Override
    public boolean runOnIdle() {
        return true;
    }
}
