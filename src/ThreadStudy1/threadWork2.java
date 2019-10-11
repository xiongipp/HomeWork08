package ThreadStudy1;

public class threadWork2 extends Thread {
    private OperateMatrix om;

    public threadWork2(OperateMatrix om ){
        this.om=om;

    }
    @Override
    public void run() {
        om.operate();
    }
}
