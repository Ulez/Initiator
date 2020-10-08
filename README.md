# Initiator-Android启动器

#### Android 启动优化方向：

1、合理利用多线程，核心线程数的设置。利用Systrace辅助查看各任务的启动时间耗时

cpu time：真正使用的时间。（数据小为IO密集型操作，可以随便开线程开启该任务，如使用线程池的newCachedThreadPool。）

wall time：包含等待的时间片。（就绪状态：等待时间片的到来。或者阻塞状态：主动的等待条件）

2、延迟初始化（不重要的任务）

3、IdleHandler

4、sp优化

5、启动阶段不启动子进程

6、提前异步类加载，class.forName或者new对象。思路是替换系统的classLoader，打印出来。

--------

#### 启动器功能：

按照任务的依赖关系，进行拓扑排序，然后按照是否必须运行在主线程，用对应的线程池或者handler执行任务，简化启动的代码。

拓扑排序示例：

<img src="https://github.com/Ulez/Initiator/blob/master/screenshots/1.png" width = "116" height = "120" align=center />

<img src="https://github.com/Ulez/Initiator/blob/master/screenshots/2.png" width = "116" height = "80" align=center />

运行效果：

<img src="https://github.com/Ulez/Initiator/blob/master/screenshots/3.png" width = "316" height = "120" align=center />

使用方式：

```
Initiator.getInstance()
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
```



