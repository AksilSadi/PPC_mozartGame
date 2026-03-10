package upmc.akka.ppc

import akka.actor._

object Provider {

  val table1: Map[String, Map[String, Int]] = Map(
    "2" -> Map("A" -> 96, "B" -> 22, "C" -> 141, "D" -> 41, "E" -> 105, "F" -> 122, "G" -> 11, "H" -> 30),
    "3" -> Map("A" -> 32, "B" -> 6, "C" -> 128, "D" -> 63, "E" -> 146, "F" -> 46, "G" -> 134, "H" -> 81),
    "4" -> Map("A" -> 69, "B" -> 95, "C" -> 158, "D" -> 13, "E" -> 153, "F" -> 55, "G" -> 110, "H" -> 24),
    "5" -> Map("A" -> 40, "B" -> 17, "C" -> 113, "D" -> 85, "E" -> 161, "F" -> 2, "G" -> 159, "H" -> 100),
    "6" -> Map("A" -> 148, "B" -> 74, "C" -> 163, "D" -> 45, "E" -> 80, "F" -> 97, "G" -> 36, "H" -> 107),
    "7" -> Map("A" -> 104, "B" -> 157, "C" -> 27, "D" -> 167, "E" -> 154, "F" -> 68, "G" -> 118, "H" -> 91),
    "8" -> Map("A" -> 152, "B" -> 60, "C" -> 171, "D" -> 53, "E" -> 99, "F" -> 133, "G" -> 21, "H" -> 127),
    "9" -> Map("A" -> 119, "B" -> 84, "C" -> 114, "D" -> 50, "E" -> 140, "F" -> 86, "G" -> 169, "H" -> 94),
    "10" -> Map("A" -> 98, "B" -> 142, "C" -> 42, "D" -> 156, "E" -> 75, "F" -> 129, "G" -> 62, "H" -> 123),
    "11" -> Map("A" -> 3, "B" -> 87, "C" -> 165, "D" -> 61, "E" -> 135, "F" -> 47, "G" -> 147, "H" -> 33),
    "12" -> Map("A" -> 54, "B" -> 130, "C" -> 10, "D" -> 103, "E" -> 28, "F" -> 37, "G" -> 106, "H" -> 5)
  )

  val table2: Map[String, Map[String, Int]] = Map(
    "2" -> Map("A" -> 70, "B" -> 121, "C" -> 26, "D" -> 9, "E" -> 112, "F" -> 49, "G" -> 109, "H" -> 14),
    "3" -> Map("A" -> 117, "B" -> 39, "C" -> 126, "D" -> 56, "E" -> 174, "F" -> 18, "G" -> 116, "H" -> 83),
    "4" -> Map("A" -> 66, "B" -> 139, "C" -> 15, "D" -> 132, "E" -> 73, "F" -> 58, "G" -> 145, "H" -> 79),
    "5" -> Map("A" -> 90, "B" -> 176, "C" -> 7, "D" -> 34, "E" -> 67, "F" -> 160, "G" -> 52, "H" -> 170),
    "6" -> Map("A" -> 25, "B" -> 143, "C" -> 64, "D" -> 125, "E" -> 76, "F" -> 136, "G" -> 1, "H" -> 93),
    "7" -> Map("A" -> 138, "B" -> 71, "C" -> 150, "D" -> 29, "E" -> 101, "F" -> 162, "G" -> 23, "H" -> 151),
    "8" -> Map("A" -> 16, "B" -> 155, "C" -> 57, "D" -> 175, "E" -> 43, "F" -> 168, "G" -> 89, "H" -> 172),
    "9" -> Map("A" -> 120, "B" -> 88, "C" -> 48, "D" -> 166, "E" -> 51, "F" -> 115, "G" -> 72, "H" -> 111),
    "10" -> Map("A" -> 65, "B" -> 77, "C" -> 19, "D" -> 82, "E" -> 137, "F" -> 38, "G" -> 149, "H" -> 8),
    "11" -> Map("A" -> 102, "B" -> 4, "C" -> 31, "D" -> 164, "E" -> 144, "F" -> 59, "G" -> 173, "H" -> 78),
    "12" -> Map("A" -> 35, "B" -> 20, "C" -> 108, "D" -> 92, "E" -> 12, "F" -> 124, "G" -> 44, "H" -> 131)
  )

  val measureToAlpha: Map[Int, String] = Map(
    1 -> "A",
    2 -> "B",
    3 -> "C",
    4 -> "D",
    5 -> "E",
    6 -> "F",
    7 -> "G",
    8 -> "H"
  )

}

object ProviderActor {
  case class GetMeasure(result: Int, compteur: Int)
}

class ProviderActor() extends Actor {

  import ConductorActor.Result
  import DataBaseActor._
  import Provider._
  import ProviderActor._

  private val database = context.actorSelection("/user/DataBase")
  private val conductor = context.actorSelection("/user/Conductor")

  def receive = {
    case GetMeasure(result, compteur) => {
      val colonne = if (compteur % 8 == 0) 8 else compteur % 8
      val alpha = measureToAlpha(colonne)
      val numeroMesure =
        if (compteur <= 8) table1(result.toString)(alpha)
        else table2(result.toString)(alpha)

      database ! GetStoredMeasure(numeroMesure)
    }

    case MeasureResult(measure) => {
      conductor ! Result(measure)
    }
  }

}
