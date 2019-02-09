package pl.pawmat.recommendations.model

import org.apache.spark.ml.classification.{MultilayerPerceptronClassificationModel, MultilayerPerceptronClassifier, ProbabilisticClassificationModel}
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.sql.{DataFrame, SparkSession}

class DeepLearningClassifier() {
  private val ModelDirectoryName = "neural-network-model"

  def buildModel(input: DataFrame, sparkSession: SparkSession): MultilayerPerceptronClassificationModel = {
    // input layer of size 4 (features), two intermediate of size 5 and 4
    // and output of size 2 (classes)
    val networkLayers = Array[Int](4, 5, 4, 2)

    val trainer = new MultilayerPerceptronClassifier()
      .setLayers(networkLayers)
      .setBlockSize(128)
      .setSeed(1234L)
      .setMaxIter(100)

    val model = trainer.fit(assembleDataFromFile(input))

    model.write.overwrite.save(ModelDirectoryName)

    model
  }

  def predict(input: DataFrame): DataFrame = {
    val model = MultilayerPerceptronClassificationModel.load(ModelDirectoryName)
    model.transform(assembleDataFromFile(input))
      .select("prediction", "probability")
  }

  private def assembleDataFromFile(data: DataFrame): DataFrame = {
    val assembler = new VectorAssembler()
      .setInputCols(Array("league_id", "player_1", "player_2", "popularity"))
      .setOutputCol("features")

    assembler.transform(data)
  }
}
