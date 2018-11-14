package pl.pawmat.recommendations.data

import org.scalamock.scalatest.MockFactory
import org.scalatest.{FlatSpec, Matchers}

class CustomerDataFileCreatorSpec extends FlatSpec with Matchers with MockFactory {

  "A creator" should "write all lines to the file" in {
    var dataGenerator = mock[CustomerDataGenerator]
    var fileWriter = mock[DataFileWriter]

    val fileCreator = new CustomerDataFileCreator(dataGenerator, fileWriter)

    inSequence {
      fileWriter.init _ expects "CBFan.txt"
      dataGenerator.generateOneTeamFanData _ expects(1, 150) returning Seq(
        CustomerBetData(1, 2, 3, 4, didBet = true),
        CustomerBetData(2, 3, 4, 5, didBet = false)
      )
      fileWriter.writeLine _ expects "1,2,3,4,true"
      fileWriter.writeLine _ expects "2,3,4,5,false"
      fileWriter.close _ expects()
    }

    fileCreator.createOneTeamFanDataFile
  }
}
