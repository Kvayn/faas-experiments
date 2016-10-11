package awsl_perfomance_test_wotransaction;

class OutputData {
    private long computingTime;
    private String message;

    OutputData(){

    }
    OutputData(long computingTime, String message) {
        this.computingTime = computingTime;
        this.message = message;
    }
    public String getMessage() {
        return message;
    }

    public long getComputingTime() {
        return computingTime;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setComputingTime(long computingTime) {
        this.computingTime = computingTime;
    }

    @Override
    public String toString() {
        return "OutputData{" +
                "computingTime=" + computingTime +
                ", message='" + message + '\'' +
                '}';
    }
}
