package pl.pawmat.recommendations

import org.apache.spark.{SparkConf, SparkContext}

object RecommendationsApp {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("SparkMe Application")
      .setMaster("local")

    val sc = new SparkContext(conf)

    System.in.read()
    sc.stop()
  }
}
