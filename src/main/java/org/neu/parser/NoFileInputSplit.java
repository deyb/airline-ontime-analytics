package org.neu.parser;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;

/**
 * 
 * @author dey
 *
 */
public class NoFileInputSplit extends TextInputFormat {
	
    @Override
    protected  boolean isSplitable(JobContext context,Path file) {
        return false;
    }
}