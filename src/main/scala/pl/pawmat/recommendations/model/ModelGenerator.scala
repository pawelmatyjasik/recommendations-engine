package pl.pawmat.recommendations.model

import org.apache.spark.ml.classification.MultilayerPerceptronClassificationModel
import org.apache.spark.sql.SparkSession

class ModelGenerator(sparkSession: SparkSession, classifier: DeepLearningClassifier) {
  def createModel(): MultilayerPerceptronClassificationModel = {
    val data = sparkSession.read.format("csv")
      .option("inferSchema", "true")
      .option("header", "true")
      .load("training-data/basketball/CBFan.txt")

    classifier.buildModel(data)
  }
}
