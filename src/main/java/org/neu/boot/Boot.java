/*******************************************************************************
 * 
 * @author Bishwajeet Dey (dey.b@husky.neu.edu)
 ******************************************************************************/
package org.neu.boot;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.util.ToolRunner;

/**
 * Start point of the flow
 *
 * @author Bishwajeet Dey (dey.b@husky.neu.edu)
 */
public class Boot {
	public static void main(String[] args) throws Exception {
		int exit = ToolRunner.run(new Configuration(), new CleanCountDriver(), args);
		if (exit != 0) {
			System.err.println("Clean Count job exited with non zero return status. Exiting");
			System.exit(-1);
		}
	}
}
