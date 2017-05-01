package com.xebia.actors

import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestActorRef, TestKit}
import com.xebia.constants.Move.Move
import com.xebia.constants.{Move, Orientation}
import com.xebia.models._
import org.scalatest._

class MowerActorTest extends TestKit(ActorSystem("testSystem"))
  with ImplicitSender with WordSpecLike with MustMatchers with GivenWhenThen  with BeforeAndAfterAll {

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  "A mower actor" must {
    Given("A MowerActor instantiation")
    val actorRef = TestActorRef[MowerActor]

    Given("A pitch, a mower and instructions")
    val pitch = Pitch(5, 5)
    val mower = Mower(0, 0, Orientation.NORTH)
    val instructions = Instructions(moves = List[Move](Move.FORWARD, Move.LEFT))

    "send a start instructions message" in {

      When("Sending the instructions to the actor")
      actorRef ! StartInstructions(mower, instructions, pitch)

      Then("The actor's internal state should contain the mower and an empty list of other mowers")
      actorRef.underlyingActor.mowerInternalState must equal(mower)
      actorRef.underlyingActor.mowersList must equal(List())

      Then("The actor should send a message to communicate the mower's position once done mowing")
      expectMsg(CommunicatePosition(mower))

      Given("A second mower within the pitch")
      val mower2 = mower.copy(position = Position(1, 2))

      When("We broadcase the mowers' position to the actor")
      actorRef ! CommunicatePosition(mower)
      actorRef ! CommunicatePosition(mower2)

      Then("The mower's list inside the actor should contain mower2")
      actorRef.underlyingActor.mowersList must equal(List(mower2))

    }
  }

}
