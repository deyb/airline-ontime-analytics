# A8 - Ghost of Assignments Past

This project analyzes A4 - On-Time Performance Data

Pre-requisites:
1. Java (v1.8+)
2. Spark (v2.2.0 compiled with Scala 2.11.X and hadoop-2.7)
3. Scala (v2.11.X)
4. .Renviron should exist to generate the report in R
5. Hadoop(v2.8.1)

This project can output the result using both spark and hadoop. To run the project with hadoop, use `make build` and `make run`. For 
spark version use `make sbuild` and `make srun`. 

The hadoop version might require setup in your local machine. You may use `make setup` to push the whole input directory in HDFS. Also, if you have the `input` directory setup in local/HDFS, you will be able to run the whole program directly with `make all` which builds the JAR, cleans HDFS `output` and runs the JAR with hadoop.

Before running the project:
1. Update the variables SCALA_HOME,SPARK_HOME,HADOOP_HOME,MY_CLASSPATH,SC_CLASSPATH as per your configuration in Makefile.
2. Copy the input files to be processed in a folder `input` in the same level as src folder.
3. For the hadoop version and spark version, the output will be located in `output`. 

More details can be found in the report.pdf and diff.pdf.
