package pl.pawmat.recommendations.data

import scala.util.Random

class CustomerDataGenerator() {
  private def randomGenerator = new Random()

  def generateOneTeamFanData(teamId: Int, setSize: Int): Seq[CustomerBetData] = {
    val teamAsFirstParticipant = (1 to setSize / 3)
      .map(_ => CustomerBetData(
        randomId(5), teamId, randomId(50), randomId(100), 1))
    val teamAsSecondParticipant = (1 to setSize / 3)
      .map(_ => CustomerBetData(
        randomId(5), randomId(50), teamId, randomId(100), 1))
    val randomTeams = (1 to setSize.-(teamAsSecondParticipant.length).-(teamAsSecondParticipant.length))
      .map(_ =>
        CustomerBetData(randomId(5), randomId(50), randomId(50), randomId(100), 0))

    Random.shuffle(teamAsFirstParticipant ++ teamAsSecondParticipant ++ randomTeams)
  }


  private def randomId(upperLimit: Int): Int = {
    randomGenerator.nextInt(upperLimit + 1) + 1
  }
}
