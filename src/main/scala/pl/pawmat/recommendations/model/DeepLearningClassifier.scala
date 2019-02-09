package pl.pawmat.recommendations.model

import org.apache.spark.ml.classification.{MultilayerPerceptronClassificationModel, MultilayerPerceptronClassifier}
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.sql.{DataFrame, Dataset}

class DeepLearningClassifier() {
  private val ModelDirectoryName = "neural-network-model"

  def buildModel(input: DataFrame, intermediateLayers: Array[Int] = Array[Int](5, 4)): MultilayerPerceptronClassificationModel = {
    val networkLayers = Array[Int](4) ++ intermediateLayers ++ Array[Int](2)

    val trainer = new MultilayerPerceptronClassifier()
      .setLayers(networkLayers)
      .setStepSize(0.02)
      .setBlockSize(128)
      .setSeed(1234L)
      .setMaxIter(100)

    val model = trainer.fit(assembleData(input))

    model.write.overwrite.save(ModelDirectoryName)

    model
  }

  def transform(model: MultilayerPerceptronClassificationModel, data: DataFrame) = {
    model.transform(assembleData(data))
  }

  def predict(input: DataFrame): DataFrame = {
    val model = MultilayerPerceptronClassificationModel.load(ModelDirectoryName)
    model.transform(assembleData(input))
      .select("prediction", "probability")
  }

  private def assembleData(data: DataFrame): DataFrame = {
    val assembler = new VectorAssembler()
      .setInputCols(Array("leagueId", "teamOneId", "teamTwoId", "popularity"))
      .setOutputCol("features")

    assembler.transform(data)
  }
}
