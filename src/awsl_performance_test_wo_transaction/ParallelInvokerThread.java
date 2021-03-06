package awsl_performance_test_wo_transaction;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.AWSLambdaClient;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import configurations.ConfigReader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import java.nio.ByteBuffer;
import java.nio.charset.Charset;

class ParallelInvokerThread extends Thread{
    private static final Log logger = LogFactory.getLog(ParallelInvokerThread.class);
    private static String awsAccessKeyId;
    private static String awsSecretAccessKey;
    private static String regionName;
    private static String functionName;
    private static Region region;
    private static AWSCredentials credentials;
    private static AWSLambdaClient lambdaClient;
    private InputData inputData;
    private OutputData receivedData;
    private long responseTime;

    ParallelInvokerThread(InputData inputData){
        this.inputData = inputData;
        awsAccessKeyId = ConfigReader.getConfig().getAwsAccessKeyId();
        awsSecretAccessKey = ConfigReader.getConfig().getAwsSecretAccessKey();
        regionName = ConfigReader.getConfig().getRegion();
        functionName = ConfigReader.getConfig().getFunctionNameWOTransaction();
    }

    @Override
    public void run() {
        //initialization of lambda client
        credentials = new BasicAWSCredentials(awsAccessKeyId,
                awsSecretAccessKey);
        lambdaClient = new AWSLambdaClient(credentials);
        region = Region.getRegion(Regions.fromName(regionName));
        lambdaClient.setRegion(region);

        //creating a JSON to call the function
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "";
        try {
            json = objectMapper.writeValueAsString(inputData);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try {
            //configure the function request
            InvokeRequest invokeRequest = new InvokeRequest();
            invokeRequest.setFunctionName(functionName);
            //set the input data
            invokeRequest.setPayload(json);
            long startTime = System.currentTimeMillis();
            //invoking the function, received data saved in OutputData object
            receivedData = objectMapper.readValue(byteBufferToString(
                    lambdaClient.invoke(invokeRequest).getPayload(),
                    Charset.forName("UTF-8")), OutputData.class);
            responseTime = System.currentTimeMillis() - startTime;
            //print the response time of function and the time was taken to calculate
            //System.out.println("The time of response is " + (System.currentTimeMillis() - startTime) +
            //        " | The calculation time is " + receivedData.getComputingTime() + "ms" + " | The total perform time is " + (System.currentTimeMillis() - calcStartTime));
        } catch (Exception e) {
            logger.error(e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    //transforms ByteBuffer into the String
    static String byteBufferToString(ByteBuffer buffer, Charset charset) {
        byte[] bytes;
        if (buffer.hasArray()) {
            bytes = buffer.array();
        } else {
            bytes = new byte[buffer.remaining()];
            buffer.get(bytes);
        }
        return new String(bytes, charset);
    }

    public InputData getInputData() {
        return inputData;
    }

    public OutputData getReceivedData() {
        return receivedData;
    }

    public long getResponseTime() {
        return responseTime;
    }
}
