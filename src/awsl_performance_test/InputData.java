package awsl_performance_test;

class InputData {
    private int[][] matrixA;
    private int[][] matrixB;
    private int size;
    private int n;
    private int id;
    InputData(){

    }
    InputData(int[][] matrixA, int[][] matrixB, int size, int n, int id) {
        this.matrixA = matrixA;
        this.matrixB = matrixB;
        this.size = size;
        this.n = n;
        this.id = id;
    }
    public int[][] getMatrixA() {
        return matrixA;
    }

    public int[][] getMatrixB() {
        return matrixB;
    }

    public int getSize() {
        return size;
    }

    public int getN() {
        return n;
    }

    public int getId() {
        return id;
    }

    public void setMatrixA(int[][] matrixA) {
        this.matrixA = matrixA;
    }

    public void setMatrixB(int[][] matrixB) {
        this.matrixB = matrixB;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setN(int n) {
        this.n = n;
    }

    public void setId(int id) {
        this.id = id;
    }
}
