package org.neu.boot;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.neu.mapred.CleanCountCombiner;
import org.neu.mapred.CleanCountMapper;
import org.neu.mapred.CleanCountReducer;
import org.neu.parser.NoFileInputSplit;

/**
 * 
 * @author dey
 *
 */
public class CleanCountDriver extends Configured implements Tool {
	@Override
	public int run(String[] args) throws Exception {
		final Configuration conf = new Configuration();

		Job job = Job.getInstance(conf, "Count and Clean");

		job.setJarByClass(CleanCountDriver.class);

		job.setMapperClass(CleanCountMapper.class);
		job.setReducerClass(CleanCountReducer.class);
		job.setCombinerClass(CleanCountCombiner.class);

		job.setInputFormatClass(NoFileInputSplit.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);

		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		boolean jobStatus = job.waitForCompletion(true);
		if (jobStatus && job.isSuccessful()) {
			return 0;
		}

		return 1;
	}
}
