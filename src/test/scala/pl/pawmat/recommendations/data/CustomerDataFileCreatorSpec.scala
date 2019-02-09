package pl.pawmat.recommendations.data

import org.scalamock.scalatest.MockFactory
import org.scalatest.{FlatSpec, Matchers}
import pl.pawmat.recommendations.utils.DataFileWriter

class CustomerDataFileCreatorSpec extends FlatSpec with Matchers with MockFactory {

  "A creator" should "write all lines to the file" in {
    var dataGenerator = mock[CustomerDataGenerator]
    var fileWriter = mock[DataFileWriter]

    val fileCreator = new CustomerDataFileCreator(dataGenerator, fileWriter)

    inSequence {
      fileWriter.init _ expects "CustomerBettingHistory.csv"
      dataGenerator.generateOneTeamFanData _ expects(1, 150) returning Seq(
        CustomerBetData(1, 2, 3, 4, 1),
        CustomerBetData(2, 3, 4, 5, 0)
      )
      fileWriter.writeLine _ expects "1,2,3,4,1"
      fileWriter.writeLine _ expects "2,3,4,5,0"
      fileWriter.close _ expects()
    }

    fileCreator.createOneTeamFanDataFile
  }
}
