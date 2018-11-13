package pl.pawmat.recommendations.data

import org.scalatest.{FlatSpec, FunSuite, Matchers}

class CustomerDataGeneratorSpec extends FlatSpec with Matchers {

  "A dataset" should "have at least one third records with specified team as first participant" in {
    val dataGenerator = new CustomerDataGenerator()
    val teamId = 3
    dataGenerator.generateOneTeamFanData(teamId, 150)
      .count(betData => betData.teamOneId.equals(3)) should be > 50
  }

  "A dataset" should "have at least one third records with specified team as second participant" in {
    val dataGenerator = new CustomerDataGenerator()
    val teamId = 5
    dataGenerator.generateOneTeamFanData(teamId, 150)
      .count(betData => betData.teamTwoId.equals(3)) should be > 50
  }

  "A dataset" should "have specified size when divisible by 3" in {
    val dataGenerator = new CustomerDataGenerator()
    dataGenerator.generateOneTeamFanData(1, 300).size should be(300)
  }

}
