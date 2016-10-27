package awsl_performance_test;

class OutputData {
    private int[][] matrixC;
    private long calcTime;
    private int id;
    public OutputData(){

    }

    public OutputData(int[][] matrixC, long calcTime, int id) {
        this.matrixC = matrixC;
        this.calcTime = calcTime;
        this.id = id;
    }

    public int[][] getMatrixC() {
        return matrixC;
    }

    public long getCalcTime() {
        return calcTime;
    }

    public int getId() {
        return id;
    }

    public void setMatrixC(int[][] matrixC) {
        this.matrixC = matrixC;
    }

    public void setCalcTime(long calcTime) {
        this.calcTime = calcTime;
    }

    public void setId(int id) {
        this.id = id;
    }
}
