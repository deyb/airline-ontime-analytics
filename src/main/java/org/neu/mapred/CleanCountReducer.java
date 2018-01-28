/**
 * 
 */
package org.neu.mapred;

import java.io.IOException;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * @author dey
 *
 */
public class CleanCountReducer extends Reducer<Text, Text, Text, NullWritable> {
	@Override
	protected void reduce(Text textRecord, Iterable<Text> counts, Context context)
			throws IOException, InterruptedException {
		int sumCounts = 0;
		double sumDelay = 0.0;
		for (Text t : counts) {
			String s = t.toString();
			int comma = s.indexOf(',');
			sumCounts += Integer.parseInt(s.substring(0, comma));
			sumDelay  += Double.parseDouble(s.substring(comma + 1));
		}	
		
		context.write(new Text(String.format("%s,%d,%.3f", textRecord.toString(), sumCounts, sumDelay)), NullWritable.get());
	}
}
