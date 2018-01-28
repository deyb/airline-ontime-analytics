package org.exp

import org.apache.spark.{SparkConf, SparkContext}
import org.neu.parser.Sanitizer

/**
  * @author deyb(dey.b@husky.neu.edu)
  */
object OTP {
  def main(args: Array[String]): Unit = {
    val inputDir = args(0)
    val outputDir = args(1)

    val conf = new SparkConf()
      .setAppName("On-Time-Performance")
      .setMaster("local[*]")

    val sc = new SparkContext(conf)
    sc.textFile(inputDir)
      .filter(Sanitizer.isValid(_))
      .map(line => {
        val record = Sanitizer.getSanitizedRecord(line)
        ((record.year, record.month, record.airportCode, record.airlineCode), (1, record.delay))
      })
      .reduceByKey{case ((nr1, nsum1), (nr2, nsum2)) => (nr1 + nr2, nsum1 + nsum2) }
      .map{case (a, b) => a.productIterator.mkString(",") + "," + b.productIterator.mkString(",")}
      .saveAsTextFile(outputDir)
  }
}
