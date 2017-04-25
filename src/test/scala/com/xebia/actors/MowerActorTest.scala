package com.xebia.actors

import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestActorRef, TestKit}
import com.xebia.constants.Move.Move
import com.xebia.constants.{Move, Orientation}
import com.xebia.models.{Instructions, Mower, Pitch, Position}
import org.scalatest._

class MowerActorTest extends TestKit(ActorSystem("testSystem"))
  with ImplicitSender with WordSpecLike with MustMatchers with GivenWhenThen  with BeforeAndAfterAll {

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  "A mower actor" must {
    // Creation of a MowerActor reference
    val actorRef = TestActorRef[MowerActor]

    val pitch = Pitch(5, 5)
    val mower = Mower(0, 0, Orientation.NORTH)
    val instructions = Instructions(moves = List[Move](Move.FORWARD, Move.LEFT))

    "send a start instructions message" in {

      // This call is synchronous. The actor receive() method will be called in the current thread
      actorRef ! StartInstructions(mower, instructions, pitch)

      // With actorRef.underlyingActor, we can access the SimpleActor instance created by Akka
      actorRef.underlyingActor.mowerInternalState must equal(mower)
      actorRef.underlyingActor.mowersList must equal(List())

      expectMsg(CommunicatePosition(mower))

      val mower2 = mower.copy(position = Position(1, 2))

      actorRef ! CommunicatePosition(mower)
      actorRef ! CommunicatePosition(mower2)

      actorRef.underlyingActor.mowersList must equal(List(mower2))

    }
  }

}
