package pl.pawmat.recommendations.data

private[data] class CustomerDataFileCreator(customerDataGenerator: CustomerDataGenerator, dataFileWriter: DataFileWriter) {
  val DataFileName = "CustomerBettingHistory.csv"

  def createOneTeamFanDataFile(): Unit = {
    dataFileWriter.init(DataFileName)

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
