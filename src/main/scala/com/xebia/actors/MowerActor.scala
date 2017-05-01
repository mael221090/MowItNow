package com.xebia.actors

import akka.actor.{Actor, ActorLogging}
import com.xebia.models.{CommunicatePosition, Mower, StartInstructions}

import scala.collection.mutable.ListBuffer

/*
 * Actor responsible for mowing the pitch and communicating its position once it is done
 */
class MowerActor extends Actor with ActorLogging {

  // internal list state to store the other mowers present in the field: gives perspective like not hurting the other mowers
  var mowersList = new ListBuffer[Mower]()
  // store in state the mower reference
  var mowerInternalState = Mower.empty()

  override def preStart(): Unit = log.info("Mower actor started")

  override def receive: Receive = {
    // when receiving this message, mowe the pitch and send the position to the master
    case StartInstructions(mower, instructions, pitch) => {
      log.info(s"Mower at initial ${mower.position} is asked to start mowing")

      // store the mower in state
      mowerInternalState = mower
      //execute instructions to mowe the pitch
      instructions.moves.map(mower.executeCommand(pitch, _))

      // send back position to master
      sender ! CommunicatePosition(mower)
    }
      // when receiving this message, append the mower to the internal list of this actor only if it is not the internal mower
    case CommunicatePosition(mower) =>
      if(mower != mowerInternalState) {
        mowersList += mower
        log.info(s"Mower actor ${this.self.path} has received mower's position ${mower.position} towards ${mower.orientation}")
      }
  }

  override def postStop(): Unit = log.info("Mower actor stopped")
}
