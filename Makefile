
HADOOP_HOME = /home/dey/tools/hadoop-2.8.1
MY_CLASSPATH = $(HADOOP_HOME)/share/hadoop/common/hadoop-common-2.8.1.jar:$(HADOOP_HOME)/share/hadoop/mapreduce/hadoop-mapreduce-client-common-2.8.1.jar:$(HADOOP_HOME)/share/hadoop/mapreduce/hadoop-mapreduce-client-core-2.8.1.jar:$(HADOOP_HOME)/share/hadoop/common/lib/*:sout:.

SCALA_HOME = /home/dey/tools/scala-2.11.11
SPARK_HOME = /home/dey/tools/spark-2.2.0-bin-hadoop2.7
SC_CLASSPATH = $(SCALA_HOME)/lib/*:$(SPARK_HOME)/jars/*:out:.
SPARK_OPTIONS=--master local[*] --driver-memory 12G


all: build clean run

sall: sbuild clean srun

build: compile jar

sbuild: scompile sjar

compile:
	mkdir -p out
	javac -cp "$(MY_CLASSPATH)" -d out src/main/java/org/neu/**/*.java 

jar:
	cp src/main/resources/log4j.properties out/
	cp -r META-INF/MANIFEST.MF out
	cd out; jar cvmf MANIFEST.MF A8.jar *
	mv out/A8.jar .

scompile:
	mkdir -p sout
	$(SCALA_HOME)/bin/scalac -cp "$(SC_CLASSPATH)" -d sout src/main/scala/org/exp/*.scala src/main/java/org/neu/**/*.java 
	javac -cp "$(MY_CLASSPATH)" -d sout src/main/java/org/neu/**/*.java 
	$(SCALA_HOME)/bin/scalac -cp "$(SC_CLASSPATH)" -d sout src/main/scala/org/exp/*.scala src/main/java/org/neu/**/*.java 

sjar:	
	cp src/main/resources/* sout/
	cp -r SMETA-INF/MANIFEST.MF sout
	cd sout; jar cvmf MANIFEST.MF A8S.jar *
	mv sout/A8S.jar .

run:
	$(HADOOP_HOME)/bin/hadoop jar A8.jar input output
	$(HADOOP_HOME)/bin/hadoop fs -copyToLocal output output

srun:
	$(SPARK_HOME)/bin/spark-submit $(SPARK_OPTIONS) A8S.jar input output

clean:
	-$(HADOOP_HOME)/bin/hdfs dfs -rm -r output
	-rm -rf output
	@true

setup: # this takes a while
	$(HADOOP_HOME)/bin/hdfs dfs -mkdir -p input;    \
	for f in input/*; do                            \
		$(HADOOP_HOME)/bin/hdfs dfs -put $$f input; \
	done;                                         \

teardown:
	-$(HADOOP_HOME)/bin/hdfs dfs -rm -r input output

report:
	Rscript -e "rmarkdown::render('report.Rmd')"

