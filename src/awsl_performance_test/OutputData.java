package awsl_performance_test;

import java.util.Arrays;

class OutputData {
    private int[][] matrixC;
    private String message;
    public OutputData(){

    }
    OutputData(int[][] matrixC, String message) {
        this.matrixC = matrixC;
        this.message = message;
    }

    public int[][] getMatrixC() {
        return matrixC;
    }

    public String getMessage() {
        return message;
    }

    public void setMatrixC(int[][] matrixC) {
        this.matrixC = matrixC;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "OutputData{" +
                "matrixC=" + Arrays.toString(matrixC) +
                '}';
    }
}
