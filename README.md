This is perfomance test of AWS Lambda. This code multiplies matrix concurrently. 
There is two variants of running:
 
-with transfering matrix as arguments of function on AWS Lambda and back
"awsl_performance_test_wotransaction"

-just call a function with light argument and check perfomance of computing
"awsl_performance_test"

To start test you need to define input data: fields "size" and "n", where size is
length of matrix and run a certain main class(depends, wich test do you want to perform). There are builded archives with test functions in folder "LambdaJars". So far there is no instraction how to upload your own function, this code warks with already uoloaded functions.
