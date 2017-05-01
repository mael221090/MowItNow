package com.xebia.actors

import akka.actor.{ActorRef, ActorSystem}
import akka.testkit.{ImplicitSender, TestActorRef, TestKit, TestProbe}
import com.xebia.constants.Move._
import com.xebia.constants.{Move, Orientation}
import com.xebia.models.{LaunchMowing, _}
import com.xebia.modules.{AkkaModule, ApplicationModule}
import org.scalatest.{BeforeAndAfterAll, GivenWhenThen, MustMatchers, WordSpecLike}
import scaldi.Module
import scaldi.akka.AkkaInjectable

import scala.concurrent.duration._

class MowItNowActorTest extends TestKit(ActorSystem("testSystem"))
  with ImplicitSender with WordSpecLike with MustMatchers with GivenWhenThen  with BeforeAndAfterAll {

  def akkaTestModule = new Module {
    bind [ActorRef] identifiedBy 'mowerActorRef to {
      AkkaInjectable.injectActorRef[MowerActor]
    }
    bind[ActorRef] identifiedBy 'mowerActorRef to inject[TestProbe]('probeForB).ref
  }

  implicit val testModule = new ApplicationModule ++ new AkkaModule ++ akkaTestModule

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  "A MowItNowActor" must {
    Given("A MowerActor instantiation")
    val actorRef = TestActorRef(new MowItNowActor())
    val mowerActor = TestProbe()

    Given("A pitch, a mower and instructions")
    val pitch = Pitch(5, 5)
    val mower = Mower(0, 0, Orientation.NORTH)
    val instructions = Instructions(moves = List[Move](Move.FORWARD, Move.LEFT))

    "send messages" in {

      When("The actor receives a CommunicatePosition message")
      actorRef ! CommunicatePosition(mower)
      Then("The actor should print a message, that's it")
      expectNoMsg()

      actorRef ! LaunchMowing(mower, instructions, pitch)
      actorRef.underlyingActor.router.routees.length mustBe 1

      //mowerActor.expectMsg(2 seconds, StartInstructions)

      actorRef ! FinishedMowing()
      //expectMsgType[PoisonPill]
      //actorRef.underlyingActor.router.routees.length mustBe 0

    }

  }

}
