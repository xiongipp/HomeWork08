package ThreadStudy1;

public class OperateMatrix {
    int[][] matrix1 = null;        //第一个矩阵
    int[][] matrix2 = null;        //第二个矩阵
    int[][] result = null;          //存放矩阵相乘结果

    public static int line = 0;

    public static int getLine() {
        return line;
    }

    public static void setLine(int line) {
        OperateMatrix.line = line;
    }

    //定义构造函数，初始化要相称的矩阵信息
    public OperateMatrix(int[][] m1,int[][] m2) {
        this.matrix1 = m1;
        this.matrix2 = m2;
        result = new int[matrix1.length][matrix2[0].length];//给运算结果分配大小
    }
    //每调用一次该方法，就将两矩阵的乘法算一行
    public void operate(){
        OperateMatrix.line+=1;
        for(int i=0;i<matrix1[0].length;i++){
            int sum=0;
            for(int j=0;j<matrix2.length;j++){
                sum+=matrix1[OperateMatrix.line-1][j]*matrix2[i][j];
            }
            result[OperateMatrix.line-1][i]=sum;
        }
    }
    public void printMatrix(){
        for(int i=0;i<matrix1.length;i++){
            for(int j=0;j<matrix2.length;j++){
                System.out.print(result[i][j]+" ");
            }
            System.out.println();

        }

    }
}
