package pl.pawmat.recommendations

import org.apache.spark.ml.classification.MultilayerPerceptronClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.sql.{DataFrame, SparkSession}

object DeepLearningClassifier {

  def buildModel(): Unit = {
    val data = prepareLearningData

    val splits = data.randomSplit(Array(0.8, 0.2), seed = 1234L)
    val trainingData = splits(0)
    val modelValidationData = splits(1)

    // input layer of size 4 (features), two intermediate of size 5 and 4
    // and output of size 3 (classes)
    val networkLayers = Array[Int](4, 5, 4, 2)

    val trainer = new MultilayerPerceptronClassifier()
      .setLayers(networkLayers)
      .setBlockSize(128)
      .setSeed(1234L)
      .setMaxIter(100)

    val model = trainer.fit(trainingData)

    val result = model.transform(modelValidationData)
    val predictionAndLabels = result.select("prediction", "label")
    val evaluator = new MulticlassClassificationEvaluator()
      .setMetricName("accuracy")

    println(s"Test set accuracy = ${evaluator.evaluate(predictionAndLabels)}")
  }

  def prepareLearningData: DataFrame = {
    val spark = SparkSession
      .builder()
      .appName("Recomendations classifier")
      .master("local")
      .getOrCreate()

    val data = spark.read.format("csv")
      .option("inferSchema", "true")
      .option("header", "true")
      .load("training-data/basketball/CBFan.txt")

    val assembler = new VectorAssembler()
      .setInputCols(Array("league_id", "player_1", "player_2", "popularity"))
      .setOutputCol("features")

    assembler.transform(data)
  }
}
