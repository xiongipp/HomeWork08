package ThreadStudy1;

import java.util.concurrent.CountDownLatch;

public class threadWork implements Runnable{
    private OperateMatrix om=null;
    private CountDownLatch countDownLatch;
    public threadWork(OperateMatrix om ,CountDownLatch countDownLatch){
        this.om=om;
        this.countDownLatch=countDownLatch;
    }
    @Override
    public void run() {
        om.operate();
        countDownLatch.countDown();//每计算完成一行，线程数减一。
    }
}
