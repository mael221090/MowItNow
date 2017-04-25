package com.xebia.constants

import com.xebia.constants.Move.Move
import com.xebia.exceptions.MoveConversionException

object Move extends Enumeration {
  type Move = Value
  val RIGHT, LEFT, FORWARD = Value
}

object MoveUtils {

  implicit def charToMove(s: Char): Move = s match {
    case 'D' => Move.RIGHT
    case 'G' => Move.LEFT
    case 'A' => Move.FORWARD
    case _ => throw MoveConversionException()
  }

  implicit def moveToChar(move: Move): Char = move match {
    case Move.RIGHT => 'D'
    case Move.LEFT => 'G'
    case Move.FORWARD => 'A'
    case _ => throw MoveConversionException()
  }

  // check if char from file is valid (among D, A or G)
  def checkCommandValidity(s: Char): Boolean = {
    s == 'D' || s == 'A' || s == 'G'
  }

}

