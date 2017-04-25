package com.xebia.services

import java.io.File

import com.typesafe.scalalogging.LazyLogging
import com.xebia.exceptions.{FileParserException, MoveException}
import com.xebia.models.Mower
import scaldi.{Injectable, Injector}

import scala.collection.mutable.ListBuffer
import scala.io.Source
import scala.util.{Failure, Try}

class MowerService(mowerParser: MowerParser)(implicit inj: Injector) extends LazyLogging with Injectable {

  // method executing the program
  def executeProgram(filename: String) = {
    try {
      runMowers(new File(getClass.getResource(filename).getPath))
    } catch {
      case ex: FileParserException => logger.error("Error while reading file, please ensure the format is correct", ex)
      case ex: Exception => logger.error("Error while reading file, please ensure the file exist in resources")
    }

  }

  def runMowers(file: File): List[Mower] = {
    var mowers = new ListBuffer[Mower]()
    val lines = Source.fromFile(file).getLines
    val pitch = mowerParser.parsePitch(lines.next())

    while(lines.hasNext) {

      val mower = mowerParser.parseMower(lines.next())
      if(lines.hasNext) {
        val instructions = mowerParser.parseInstructions(lines.next())

        instructions.moves.map(instruction => Try(mower.executeCommand(pitch, instruction)))

        println(mower.communicate)

        mowers += mower
      } else {
        throw new FileParserException("Not enough lines to parse a mower and its instructions")
      }
    }

    mowers.toList
  }


}
