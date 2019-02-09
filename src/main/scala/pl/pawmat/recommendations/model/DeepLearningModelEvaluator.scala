package pl.pawmat.recommendations.model

import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.sql.SparkSession
import pl.pawmat.recommendations.data.{CustomerBetData, CustomerDataGenerator}

class DeepLearningModelEvaluator(dataGenerator: CustomerDataGenerator, sparkSession: SparkSession, deepLearningClassifier: DeepLearningClassifier) {
  def SampleTeamId = 4

  def evaluate(intermediateLayers: Array[Int], numberOfSamples: Int): Double = {
    val data = sparkSession.createDataFrame(
      preProcessLearningData(dataGenerator.generateOneTeamFanData(SampleTeamId, numberOfSamples)))

    val splits = data.randomSplit(Array(0.8, 0.2), seed = 1234L)
    val trainingData = splits(0)
    val verificationData = splits(1)
    trainingData.collectAsList()

    val model = deepLearningClassifier.buildModel(trainingData, intermediateLayers)

    val result = deepLearningClassifier.transform(model, verificationData)
    val predictionWithLabels = result.select("prediction", "label")
    val evaluator = new MulticlassClassificationEvaluator()
      .setMetricName("accuracy")

    evaluator.evaluate(predictionWithLabels)
  }

  /**
    * Process data to reflect the fact that customer does not distinguish between home and away games.
    */
  def preProcessLearningData(input: Seq[CustomerBetData]): Seq[CustomerBetData] = {
    input.flatMap(
      data => Seq(CustomerBetData(data.leagueId, data.teamOneId, 0, data.popularity, data.label),
        CustomerBetData(data.leagueId, data.teamTwoId, 0, data.popularity, data.label))
    )
  }
}
