package configurations;

public class YmlEntity {
    private String region;
    private String awsAccessKeyId;
    private String awsSecretAccessKey;
    private String functionNameWOTransaction;
    private String functionNameWithTransaction;

    public String getFunctionNameWOTransaction() {
        return functionNameWOTransaction;
    }

    public String getFunctionNameWithTransaction() {
        return functionNameWithTransaction;
    }

    public String getRegion() {
        return region;
    }

    public String getAwsAccessKeyId() {
        return awsAccessKeyId;
    }

    public String getAwsSecretAccessKey() {
        return awsSecretAccessKey;
    }

}
