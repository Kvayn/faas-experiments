This is a performance testsuite for AWS Lambda. It results from the research on FaaS within the Service Tooling research initiative of the ZHAW Service Prototyping Lab (https://blog.zhaw.ch/icclab/category/research-approach/themes/service-tooling/).

The code runs a concurrent algorithm of matrix multiplying and uses 100 instances of one Lambda function. There are two variants of running the tests:
 
-with transferring matrix as arguments of the function into AWS Lambda and back
"awsl_performance_test_wo_transaction"

-just call a function with light argument and check performance of computing
"awsl_performance_test"


An example for running the test looks like this:

 - check out the project
 - choose the variant you want to perform
 - go to "MainParallel" class in particular package and change k and matrixSize variables, where k is number of performed tests and matrixSize is a dimension of computing matrix
 - the lambda functions are already uploaded into AWS service so just run the program and you will have your results in the file in the results folder

In the LambdaJars folder you can find prebuilt jar archives with Lambda functions that are uploaded and used for test.
If you wand to upload your own functions or use your AWS account you need to change the following fields in the ParallelInvokerThread class:
 - awsAccessKeyId
 - awsSecretAccessKey
 - regionName
 - functionName
Then upload your jar(s) configuring the performance settings. Make sure that the name of created function matches the functionName field. InputData and OutputData PoJo's are used to exchange data between functions.

The folder "results" contains reference results we have achieved in October 2016. We will periodically re-run the tests to check for performance improvements
