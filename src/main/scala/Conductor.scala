package upmc.akka.ppc

import akka.actor._
import DataBaseActor.Measure
import scala.concurrent.duration._

object Conductor {

  def jeterDeuxDes(): (Int, Int) = {
    val de1 = scala.util.Random.nextInt(6) + 1
    val de2 = scala.util.Random.nextInt(6) + 1
    (de1, de2)
  }

}

object ConductorActor {
  case class Result(value: Measure)
}

class ConductorActor() extends Actor {

  import ConductorActor._
  import ProviderActor.GetMeasure

  private val provider = context.actorSelection("/user/Provider")
  private val player = context.actorSelection("/user/Player")

  var compteurGeneral: Int = 1
  var compteur: Int = 1

  def receive = {
    case "StartGame" => {
      val (de1, de2) = Conductor.jeterDeuxDes()
      provider ! GetMeasure(de1 + de2, compteur)
    }
    case Result(value) => {
      println(s"Measure $compteurGeneral est jouee")
      player ! value
      compteur += 1
      compteurGeneral += 1
      if (compteur > 16) {
        compteur = 1
      }
      context.system.scheduler.scheduleOnce(1800.milliseconds, self, "StartGame")(context.dispatcher)
    }
  }

}
