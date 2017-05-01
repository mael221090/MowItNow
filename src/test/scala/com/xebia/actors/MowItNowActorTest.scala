package com.xebia.actors

import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestActorRef, TestKit, TestProbe}
import com.xebia.constants.Move._
import com.xebia.constants.{Move, Orientation}
import com.xebia.models.{LaunchMowing, _}
import com.xebia.modules.{AkkaModule, ApplicationModule}
import org.scalatest.{BeforeAndAfterAll, GivenWhenThen, MustMatchers, WordSpecLike}

class MowItNowActorTest extends TestKit(ActorSystem("testSystem"))
  with ImplicitSender with WordSpecLike with MustMatchers with GivenWhenThen  with BeforeAndAfterAll {

  implicit val testModule = new ApplicationModule ++ new AkkaModule

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  "A MowItNowActor" must {
    Given("A MowerActor instantiation")
    val actorRef = TestActorRef(new MowItNowActor())

    Given("A pitch, a mower and instructions")
    val pitch = Pitch(5, 5)
    val mower = Mower(0, 0, Orientation.NORTH)
    val instructions = Instructions(moves = List[Move](Move.FORWARD, Move.LEFT))

    "send messages" in {

      When("The actor receives a CommunicatePosition message")
      actorRef ! CommunicatePosition(mower)
      Then("The actor should print a message, that's it")
      expectNoMsg()

      When("The actor receive instruction to start mowing")
      actorRef ! LaunchMowing(mower, instructions, pitch)
      Then("The router must have one routee")
      actorRef.underlyingActor.router.routees.length mustBe 1

    }

  }

}
