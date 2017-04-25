package com.xebia.services

import java.io.File

import com.typesafe.scalalogging.LazyLogging
import com.xebia.constants.MoveUtils
import com.xebia.exceptions.FileParserException
import com.xebia.models.{Instructions, Mower, Pitch}
import com.xebia.constants.OrientationUtils._
import com.xebia.constants.MoveUtils._

import scala.collection.mutable.ListBuffer
import scala.io.Source
import scala.util.Try


class MowerParser extends LazyLogging {

  type MowerProgram = (Pitch, List[Mower])

  def readFile(file: File): Iterator[String] = {
    Source.fromFile(file).getLines()
  }

  def parsePitch(line: String): Pitch = {
    logger.debug(s"Parsing line $line as coordinates")

    val coordinates = line.split(" ")

    if(coordinates.length != 2)
      throw FileParserException("Coordinates must have exactly two variables (2-dimensional space) ")

    try {
      Pitch(
        width = line.split(" ")(0).toInt,
        height = line.split(" ")(1).toInt
      )
    } catch {
      case ex: NumberFormatException => throw FileParserException("Coordinates must be integers")
    }
  }

  def parseMower(line: String): Mower = {
    logger.debug(s"Parsing line $line as mower")

    val mowerConfiguration = line.split(" ")

    if(mowerConfiguration.length != 3)
      throw FileParserException("Mower configuration must have exactly three parameters ")

    try {
      Mower(
        x = mowerConfiguration(0).toInt,
        y = mowerConfiguration(1).toInt,
        orientation = mowerConfiguration(2)
      )
    } catch {
      case ex: NumberFormatException => throw FileParserException("Coordinates must be integers")
    }
  }

  def parseInstructions(line: String): Instructions = {
    logger.debug(s"Parsing line $line as command")

    try {
      val isCommandValid: Boolean = line.toList.map(MoveUtils.checkCommandValidity).reduce(_ && _)
      if(!isCommandValid)
        throw FileParserException("The accepted command to control the mower's direction are: D, A or G")

      Instructions(moves = line.map(charToMove).toList)
    } catch {
      case ex: NumberFormatException => throw FileParserException("Coordinates must be integers")
    }
  }


}
