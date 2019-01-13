package pl.pawmat.recommendations.model

import org.apache.spark.ml.classification.MultilayerPerceptronClassifier
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.sql.DataFrame

class DeepLearningClassifier() {
  def buildModel(input: DataFrame, modelDirectoryName: String): Unit = {
    val assembledData = assembleData(input)

    // input layer of size 4 (features), two intermediate of size 5 and 4
    // and output of size 3 (classes)
    val networkLayers = Array[Int](4, 5, 4, 2)

    val trainer = new MultilayerPerceptronClassifier()
      .setLayers(networkLayers)
      .setBlockSize(128)
      .setSeed(1234L)
      .setMaxIter(100)

    val model = trainer.fit(assembledData)

    model.write.overwrite.save(modelDirectoryName)
  }


  private def assembleData(data: DataFrame): DataFrame = {
    val assembler = new VectorAssembler()
      .setInputCols(Array("league_id", "player_1", "player_2", "popularity"))
      .setOutputCol("features")

    assembler.transform(data)
  }
}
