package awsl_performance_test;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.AWSLambdaClient;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

class ParallelMulInvoker extends Thread{
    private static final Log logger = LogFactory.getLog(ParallelMulInvoker.class);
    private static final String awsAccessKeyId = "AKIAJLCK3VX4HPPGEJQQ";
    private static final String awsSecretAccessKey = "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";
    private static final String regionName = "us-west-2";
    private static final String functionName = "ParallelCompTest";
    private static Region region;
    private static AWSCredentials credentials;
    private static AWSLambdaClient lambdaClient;
    private InputData inputData;
    private OutputData outputData;

    ParallelMulInvoker(InputData inputData, OutputData outputData){
        this.inputData = inputData;
        this.outputData = outputData;
    }

    @Override
    public void run() {
        //initialising the lambda client
        credentials = new BasicAWSCredentials(awsAccessKeyId,
                awsSecretAccessKey);
        lambdaClient = new AWSLambdaClient(credentials);
        region = Region.getRegion(Regions.fromName(regionName));
        lambdaClient.setRegion(region);
        //creating a JSON of input object
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "";
        try {
            json = objectMapper.writeValueAsString(inputData);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try {
            //creating a request object and adding the input JSON
            InvokeRequest invokeRequest = new InvokeRequest();
            invokeRequest.setFunctionName(functionName);
            invokeRequest.setPayload(json);

            long startTime = System.currentTimeMillis();
            //invoke the function
            OutputData receivedData = objectMapper.readValue(byteBufferToString(
                    lambdaClient.invoke(invokeRequest).getPayload(),
                    Charset.forName("UTF-8")), OutputData.class);
            //print the response time of function and the time was taken to calculate
            System.out.println("The time of response is " + (System.currentTimeMillis() - startTime) + " | " + receivedData.getMessage());
            //add to result matrix calculated part
            addMatrix(receivedData.getMatrixC());
        } catch (Exception e) {
            logger.error(e.getMessage());
            System.out.println(e.getMessage());
        }
    }
    private static String byteBufferToString(ByteBuffer buffer, Charset charset) {
        byte[] bytes;
        if (buffer.hasArray()) {
            bytes = buffer.array();
        } else {
            bytes = new byte[buffer.remaining()];
            buffer.get(bytes);
        }
        return new String(bytes, charset);
    }
    private void addMatrix(int[][] A){
        double rowsToCount = inputData.getSize()/inputData.getN();
        double startRow = rowsToCount*inputData.getId();
        double endRow = startRow + rowsToCount;
        for (int i = (int)startRow; i < (int)endRow; i++){
            for (int j = 0; j < A.length; j++){
                outputData.getMatrixC()[i][j] =  outputData.getMatrixC()[i][j] + A[i][j];
            }
        }
    }
    public InputData getInputData() {
        return inputData;
    }

    public OutputData getOutputData() {
        return outputData;
    }
}
