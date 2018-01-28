/**
 * 
 */
package org.neu.mapred;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.neu.model.SanitizedRecord;
import org.neu.parser.Sanitizer;

/**
 * @author dey
 *
 */
public class CleanCountMapper extends Mapper<LongWritable, Text, Text, Text> {
	@Override
	protected void map(LongWritable ignore, Text value, Context context) throws IOException, InterruptedException {
		final String strRecord = value.toString();
		
		if (Sanitizer.isValid(strRecord)) {
			final SanitizedRecord record = Sanitizer.getSanitizedRecord(strRecord);
			
			context.write(new Text(String.format("%d,%d,%s,%s", record.year, record.month, record.airportCode, record.airlineCode)), 
					new Text(String.format("%d,%.3f", 1, record.delay)));
		}
	}
}
