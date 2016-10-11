package awsl_perfomance_test_wotransaction;

class InputData {
    private int size;
    private int n;
    private int id;
    InputData(){

    }
    InputData(int size, int n, int id) {
        this.size = size;
        this.n = n;
        this.id = id;
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
