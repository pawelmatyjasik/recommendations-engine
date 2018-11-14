package pl.pawmat.recommendations.data

class CustomerDataFileCreator(customerDataGenerator: CustomerDataGenerator, dataFileWriter: DataFileWriter) {
  def createOneTeamFanDataFile(): Unit = {
    dataFileWriter.init("CBFan.txt")

    try {
      customerDataGenerator
        .generateOneTeamFanData(1, 150)
        .map(a => s"${a.leagueId},${a.teamOneId},${a.teamTwoId},${a.popularity},${a.didBet}")
        .foreach(dataFileWriter.writeLine)
    } finally {
      dataFileWriter.close()
    }
  }
}
