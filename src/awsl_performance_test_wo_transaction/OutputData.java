package awsl_performance_test_wo_transaction;

class OutputData {
    private long computingTime;

    OutputData(){

    }
    OutputData(long computingTime) {
        this.computingTime = computingTime;
    }

    public long getComputingTime() {
        return computingTime;
    }

    public void setComputingTime(long computingTime) {
        this.computingTime = computingTime;
    }


}
