package ThreadStudy1;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class TestMatrix {


    public static void main(String[] args) throws InterruptedException {
        int n=10000;//矩阵的阶
        int[][] matrix1=create_Matrix(n,n);
        int[][] matrix2=create_Matrix(n,n);
        int[][] result =new int[matrix1.length][matrix2.length];
        long startTime;
        long endTime;
        int threadNum=matrix1.length;
        CountDownLatch countDownLatch = new CountDownLatch(threadNum);
        OperateMatrix operateMatrix=new OperateMatrix(matrix1,matrix2);
        //要是一万个线程同时算真的没有问题吗？
        //多线程实现矩阵乘法
        System.out.println("多线程时：");
        startTime = System.currentTimeMillis();
        for(int i=0;i<matrix1.length;i++){
            Thread thread=new Thread(new threadWork(operateMatrix,countDownLatch));
            thread.start();
        }
        countDownLatch.await();//等待所有线程都计算完成
        endTime = System.currentTimeMillis();
        System.out.println("计算"+n+"阶矩阵相乘用时："+(endTime-startTime));
        //普通方法求n阶矩阵相乘
        System.out.println("单线程时：");
        startTime = System.currentTimeMillis();
        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix1[i].length; j++) {
                result[i][j] = 0;
                for (int k = 0; k < matrix1[0].length; k++) {
                    result[i][j] = result[i][j] + matrix1[i][k] * matrix2[k][j];
                }
            }
        }
        endTime = System.currentTimeMillis();
        System.out.println("计算"+n+"阶矩阵相乘用时："+(endTime-startTime));

        //尝试使用线程池
        System.out.println("线程池时：");
        OperateMatrix operateMatrix2=new OperateMatrix(matrix1,matrix2);
        operateMatrix2.setLine(0);
        startTime = System.currentTimeMillis();
        ExecutorService pool = Executors.newCachedThreadPool();
        for(int i=0;i<matrix1.length;i++){
            Thread thread=new Thread(new threadWork2(operateMatrix2));
            pool.execute(thread);
        }
        pool.shutdown();
        endTime = System.currentTimeMillis();
        System.out.println("计算"+n+"阶矩阵相乘用时："+(endTime-startTime));
    }

    //生成随机矩阵的方法
    public  static int[][]create_Matrix(int row_number,int column_number){
        int Matrix[][]=new int[row_number][column_number];
        for (int i = 0; i <= row_number - 1; i++) {
            for (int j = 0; j <= column_number - 1; j++) {
                Matrix[i][j] = random_number();
            }
        }
        return Matrix;
    }

    private static int random_number() {
        int number;
        number = new java.util.Random().nextInt(100)+1;
        return number;
    }

}
