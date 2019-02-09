package pl.pawmat.recommendations.evaluation

import pl.pawmat.recommendations.model.DeepLearningModelEvaluator
import pl.pawmat.recommendations.utils.DataFileWriter

class DeepLearningModelParametersEvaluator(evaluator: DeepLearningModelEvaluator, dataFileWriter: DataFileWriter) {
  val ResultFileNameNotPreProcessed = "ParametersEvaluation.csv"
  val ResultFileNamePreProcessed = "ParametersEvaluationPreProcessed.csv"
  val DataSizes = Iterable(20, 50, 100, 200, 500, 1000)
  val IntermediateLayers = Iterable(Array[Int](3, 3), Array[Int](3, 4), Array[Int](4, 4), Array[Int](4, 5))

  def evaluateParams(): Unit = {
    dataFileWriter.init(ResultFileNamePreProcessed).writeLine("samples_nr,layers,result")

    try {
      DataSizes.flatMap(size => IntermediateLayers.map(layers => (size, layers)))
        .map((params: (Int, Array[Int])) => {
          (params._1, params._2, evaluator.evaluate(params._2, params._1))
        })
        .toList.sortBy(el => el._3)
        .map(resultWithParams => s"${resultWithParams._1},${resultWithParams._2(0)}:${resultWithParams._2(1)},${resultWithParams._3}")
        .foreach(dataFileWriter.writeLine)
    } finally {
      dataFileWriter.close()
    }
  }
}
