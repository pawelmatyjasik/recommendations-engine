package pl.pawmat.recommendations

import org.apache.spark
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}
import pl.pawmat.recommendations.model.{DeepLearningClassifier, ModelGenerator}

object RecommendationsApp {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("SparkMe Application")
      .setMaster("local")

    val sc = new SparkContext(conf)

    initModel()

    System.in.read()
    sc.stop()
  }

  def initModel(): Unit = {
    val spark = SparkSession
      .builder()
      .appName("Recommendations classifier")
      .master("local")
      .getOrCreate()

    val creator = new ModelGenerator(sparkSession = spark, classifier = new DeepLearningClassifier)

    creator.createModel()
  }

}
