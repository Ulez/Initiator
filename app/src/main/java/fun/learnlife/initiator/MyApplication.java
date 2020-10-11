package fun.learnlife.initiator;

import android.app.Application;
import android.content.Context;

import fun.learnlife.initiator.mytask.C1;
import fun.learnlife.initiator.mytask.C10;
import fun.learnlife.initiator.mytask.C11;
import fun.learnlife.initiator.mytask.C12;
import fun.learnlife.initiator.mytask.C13;
import fun.learnlife.initiator.mytask.C14;
import fun.learnlife.initiator.mytask.C2;
import fun.learnlife.initiator.mytask.C22;
import fun.learnlife.initiator.mytask.C23;
import fun.learnlife.initiator.mytask.C3;
import fun.learnlife.initiator.mytask.C7;
import fun.learnlife.initiator.mytask.C8;
import fun.learnlife.initiator.mytask.C9;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Initiator.getInstance()
                .addTask(new C23())
                .addTask(new C22())
                .addTask(new C14())
                .addTask(new C13())
                .addTask(new C12())
                .addTask(new C11())
                .addTask(new C10())
                .addTask(new C9())
                .addTask(new C8())
                .addTask(new C7())
                .addTask(new C3())
                .addTask(new C2())
                .addTask(new C1())
                .start();
    }

    @Override
    public Context getApplicationContext() {
        return this;
    }
}
