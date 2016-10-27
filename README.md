This is a perfomance testsuite for AWS Lambda. It results from the research on FaaS within the Service Tooling research initiative of the ZHAW Service Prototyping Lab (https://blog.zhaw.ch/icclab/category/research-approach/themes/service-tooling/).

The code multiplies a matrix concurrently. There are two variants of running the tests:
 
-with transfering matrix as arguments of the function on AWS Lambda and back
"awsl_performance_test_wotransaction"

-just call a function with light argument and check perfomance of computing
"awsl_performance_test"

To start the test you need to define input data: fields "size" and "n", where size is the length of each direction of the matrix, and run a certain main class depending on wich test you want to perform. There are pre-built archives with test functions in the folder "LambdaJars". So far there is no instructions on how to upload your own function. This code works only with already uploaded functions.

An example for running the test looks like this:

$ .... (TODO!)

The folder "results" contains reference results we have achieved in October 2016. We will periodically re-run the tests to check for performance improvements
