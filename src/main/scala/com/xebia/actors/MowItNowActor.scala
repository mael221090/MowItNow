package com.xebia.actors

import akka.actor.{Actor, ActorLogging, PoisonPill, Terminated}
import akka.routing._
import scaldi.Injector
import scaldi.akka.AkkaInjectable

class MowItNowActor(implicit inj: Injector) extends Actor with ActorLogging with AkkaInjectable {

  val mowerActor = injectActorProps[MowerActor]

  // create an internal router: used for broadcasting a mower's position to all the routees
  var router: Router = {
    val routees: Vector[ActorRefRoutee] = Vector.empty[ActorRefRoutee]
    Router(BroadcastRoutingLogic(), routees)
  }

  override def preStart(): Unit = log.info("MowItNowActor actor started")

  override def receive: Receive = {
    // ask a new actor to mowe the pitch
    case LaunchMowing(mower, instructions, pitch) => {
      log.info("Master actor has received order to start mowing")
      // add this worker actor in the router's routees in order to be able to broadcast message later
      val mowerWorker = context.actorOf(mowerActor)
      context watch mowerWorker
      // append this worker (routee) to the router
      router = router.addRoutee(mowerWorker)

      mowerWorker ! StartInstructions(mower, instructions, pitch)
    }

    // when receiving this message, the master would print the mower's position
    case CommunicatePosition(mower) =>
      println(s"Mower finished mowing the pitch and is now positioned at ${mower.position} towards ${mower.orientation}")

    // when receiving this message from a worker (mower actor), the master will broadcast this mower's position
    // to all the workers
    case BroadcastPosition(mower) =>
      router.route(CommunicatePosition(mower), sender())

    // ask the mower actors to terminate
    case FinishedMowing() =>
      log.info("Mowing the pitch is finished, sending PoisonPill to all the children")
      router.route(Broadcast(PoisonPill), sender())

    case Terminated(actor) =>
      router.removeRoutee(actor)

  }

  override def postStop(): Unit = log.info("MowItNowActor actor stopped")

}
