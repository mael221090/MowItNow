package com.xebia.constants

import com.xebia.constants.Orientation.Orientation
import com.xebia.exceptions.OrientationConversionException

object Orientation extends Enumeration {
  type Orientation = Value

  val NORTH = Value("N")
  val WEST = Value("W")
  val EAST = Value("E")
  val SOUTH = Value("S")
}

object OrientationUtils {

  implicit def stringToOrientation(s: String) = s match {
    case "N" => Orientation.NORTH
    case "E" => Orientation.EAST
    case "W" => Orientation.WEST
    case "S" => Orientation.SOUTH
    case _ => throw OrientationConversionException()
  }

  def turnRight(orientation: Orientation) = {
    orientation match {
      case Orientation.NORTH => Orientation.EAST
      case Orientation.SOUTH => Orientation.WEST
      case Orientation.WEST => Orientation.NORTH
      case Orientation.EAST => Orientation.SOUTH
    }
  }

  def turnLeft(orientation: Orientation) = {
    orientation match {
      case Orientation.NORTH => Orientation.WEST
      case Orientation.SOUTH => Orientation.EAST
      case Orientation.WEST => Orientation.SOUTH
      case Orientation.EAST => Orientation.NORTH
    }
  }

}
