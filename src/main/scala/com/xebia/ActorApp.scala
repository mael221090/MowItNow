package com.xebia

import java.io.File

import akka.actor.ActorSystem
import com.typesafe.scalalogging.LazyLogging
import com.xebia.actors.MowItNowActor
import com.xebia.exceptions.FileParserException
import com.xebia.models.{BroadcastPosition, FinishedMowing, LaunchMowing}
import com.xebia.modules.{AkkaModule, ApplicationModule}
import com.xebia.services.MowerParser
import scaldi.akka.AkkaInjectable

import scala.io.Source

/*
 * App using Actor Model with Akka
 */
object ActorApp extends App with AkkaInjectable with LazyLogging {

  logger.info("Actor App started ... ")

  implicit val appModule = new ApplicationModule :: new AkkaModule

  implicit val system = inject [ActorSystem]

  val mowItNowActor = injectActorRef[MowItNowActor]

  val mowerParser = inject[MowerParser]

  try {
    executeProgram()
  } catch {
    case ex: FileParserException => logger.error("Error while reading file, please ensure the format is correct", ex)
    case ex: Exception => logger.error("Error while reading file", ex)
  }

  sys.addShutdownHook { logger.info("Actor system shutdown gracefully"); system.terminate() }

  private def executeProgram() = {
    val file = new File(getClass.getResource("/instructions.txt").getPath)
    val lines = Source.fromFile(file).getLines
    val pitch = mowerParser.parsePitch(lines.next())

    while (lines.hasNext) {

      val mower = mowerParser.parseMower(lines.next())
      if (lines.hasNext) {
        val instructions = mowerParser.parseInstructions(lines.next())

        mowItNowActor ! LaunchMowing(mower, instructions, pitch)

        mowItNowActor ! BroadcastPosition(mower)

      } else {
        throw new FileParserException("Not enough lines to parse a mower and its instructions")
      }
    }

    mowItNowActor ! FinishedMowing()
  }

}
