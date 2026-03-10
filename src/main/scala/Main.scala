package upmc.akka.ppc

import akka.actor.{Props,  Actor,  ActorRef,  ActorSystem}

object Concert extends App {
  println("starting Mozart's game")
  val system = ActorSystem("MozartGame")
  val provider = system.actorOf(Props[ProviderActor], "Provider")
  val database = system.actorOf(Props[DataBaseActor], "DataBase")
  val player = system.actorOf(Props[PlayerActor], "Player")
  val conductor = system.actorOf(Props[ConductorActor], "Conductor")
  conductor ! "StartGame"
 }
