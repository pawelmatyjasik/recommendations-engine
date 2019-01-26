package pl.pawmat.recommendations

import org.apache.spark
import org.apache.spark.ml.classification.MultilayerPerceptronClassificationModel
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}
import pl.pawmat.recommendations.data.{CustomerBetData, CustomerBetDataPrediction}
import pl.pawmat.recommendations.model.{DeepLearningClassifier, ModelGenerator}

object RecommendationsApp {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("SparkMe Application")
      .setMaster("local")

    val sc = new SparkContext(conf)

    val model = initModel()
    predictBet(model)
    System.in.read()
    sc.stop()
  }

  def initModel(): MultilayerPerceptronClassificationModel = {
    val creator = new ModelGenerator(sparkSession = sparkSession, classifier = new DeepLearningClassifier)

    creator.createModel()
  }

  def predictBet(model: MultilayerPerceptronClassificationModel): Unit = {
    val betData = Seq(
      CustomerBetDataPrediction(4, 17, 51, 41),
      CustomerBetDataPrediction(12, 5, 6, 0),
      CustomerBetDataPrediction(4, 83, 7, 1),
      CustomerBetDataPrediction(4, 2, 3, 1),
      CustomerBetDataPrediction(4, 3, 1, 6)
    )

    val data = sparkSession.createDataFrame(betData)
    val prediction = (new DeepLearningClassifier).predict(data)
  }

  def sparkSession: SparkSession = {
    SparkSession
      .builder()
      .appName("Recommendations classifier")
      .master("local")
      .getOrCreate()
  }
}
