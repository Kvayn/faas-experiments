This is a performance testsuite for AWS Lambda. It results from the research on FaaS within the Service Tooling research initiative of the ZHAW Service Prototyping Lab (https://blog.zhaw.ch/icclab/category/research-approach/themes/service-tooling/).

This code multiplies a matrix on the AWS Lambda service concurrently(100 threads default Lambda concurrent limit). There are two variants of running the tests:

 - with transferring matrix as arguments of the function on AWS Lambda and back
   "awsl_performance_test_wo_transaction"

 - just call a function with light argument and check performance of computing
   "awsl_performance_test"

Before start:
 You need to upload pre-built archives with functions from the folder "LambdaJars" to your AWS Lambda service.
 Configure your settings in the "jyaml.yml" file: AWS secure key id, AWS secret access key, region name and function names(the names of created before "Lambda functions").

An example for running the test looks like this:
 * choose the variant you want to perform
 * go to "MainParallel" class in particular package and change k and matrixSize variables, where k is number of performed tests and matrixSize is a dimension of computing matrix
 * the lambda functions are already uploaded into AWS service so just run the program and you will have your results in the file in the results folder

The folder "results" contains reference results we have achieved in October 2016. We will periodically re-run the tests to check for performance improvements

