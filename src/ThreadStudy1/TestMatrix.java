package ThreadStudy1;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class TestMatrix {
    static long[] time1=new long[3];//多线程用时
    static long[] time2=new long[3];//单线程用时
    static long[] time3=new long[3];//线程池用时
    public static void main(String[] args) throws InterruptedException {
        //循环计算从10到1000规模的矩阵乘法耗时
        int t=0;//这是为了方便把时间存到数组
        for(int n=10;n<=1000;n*=10){
            int[][] matrix1 = create_Matrix(n, n);
            int[][] matrix2 = create_Matrix(n, n);
            int[][] result = new int[matrix1.length][matrix2.length];
            long startTime;
            long endTime;
            int threadNum = matrix1.length;
            CountDownLatch countDownLatch = new CountDownLatch(threadNum);
            OperateMatrix operateMatrix = new OperateMatrix(matrix1, matrix2);
            operateMatrix.setLine(0);
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
        time1[t]=endTime-startTime;
        System.out.println("计算"+n+"阶矩阵相乘用时："+time1[t]);
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
            time2[t] = endTime - startTime;
            System.out.println("计算" + n + "阶矩阵相乘用时：" + time2[t]);
        //尝试使用线程池
        CountDownLatch countDownLatch2 = new CountDownLatch(threadNum);
        System.out.println("线程池时：");
        OperateMatrix operateMatrix2=new OperateMatrix(matrix1,matrix2);
        operateMatrix2.setLine(0);
        startTime = System.currentTimeMillis();
        ExecutorService pool = Executors.newCachedThreadPool();
        for(int i=0;i<matrix1.length;i++){
            Thread thread=new Thread(new threadWork(operateMatrix2,countDownLatch2));
            pool.execute(thread);
        }
            countDownLatch2.await();
            pool.shutdown();
            endTime = System.currentTimeMillis();
            time3[t] = endTime - startTime;
            System.out.println("计算" + n + "阶矩阵相乘用时：" + time3[t]);
            //operateMatrix2.printMatrix();
            t++;
        }
       /* for(int i=0;i<3;i++){
            System.out.println(time1[i]);
        }*/
       //下面是绘图函数
        StandardChartTheme mChartTheme = new StandardChartTheme("CN");
        mChartTheme.setLargeFont(new Font("黑体", Font.BOLD, 20));
        mChartTheme.setExtraLargeFont(new Font("宋体", Font.PLAIN, 15));
        mChartTheme.setRegularFont(new Font("宋体", Font.PLAIN, 15));
        ChartFactory.setChartTheme(mChartTheme);
        CategoryDataset mDataset = GetDataset();
        //设置图表各种参数
        JFreeChart jFreeChart=ChartFactory.createLineChart(
                "多种线程方式计算矩阵乘法时间差异",//图名字
                "规模",//横坐标
                "所用时间",//纵坐标
                mDataset,//数据源
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        ChartFrame chartFrame=new ChartFrame("规模时间统计表",jFreeChart);
        chartFrame.pack();
        chartFrame.setVisible(true);

    }
    //设置数据集
    public static CategoryDataset GetDataset(){
        DefaultCategoryDataset mDataset=new DefaultCategoryDataset();
        mDataset.addValue(time1[0],"多线程","10");
        mDataset.addValue(time1[1],"多线程","100");
        mDataset.addValue(time1[2],"多线程","1000");
        mDataset.addValue(time2[0],"单线程","10");
        mDataset.addValue(time2[1],"单线程","100");
        mDataset.addValue(time2[2],"单线程","1000");
        mDataset.addValue(time3[0],"线程池","10");
        mDataset.addValue(time3[1],"线程池","100");
        mDataset.addValue(time3[2],"线程池","1000");
        return mDataset;
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
