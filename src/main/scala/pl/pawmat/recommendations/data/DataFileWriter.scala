package pl.pawmat.recommendations.data

import java.io.{BufferedWriter, FileWriter, PrintWriter}

class DataFileWriter {
  var fw: FileWriter = _
  var bw: BufferedWriter = _
  var out: PrintWriter = _

  def init(name: String): DataFileWriter = {
    fw = new FileWriter(name, true)
    bw = new BufferedWriter(fw)
    out = new PrintWriter(bw)

    this
  }

  def writeLine(text: String): Unit = {
    out.println(text)
  }

  def close(): Unit = {
    if (out != null) out.close()
  }
}
