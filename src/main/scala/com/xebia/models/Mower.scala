package com.xebia.models

import com.typesafe.scalalogging.LazyLogging
import com.xebia.constants.{Move, Orientation}
import com.xebia.constants.Move.Move
import com.xebia.constants.Orientation.Orientation
import com.xebia.constants.OrientationUtils._
import com.xebia.exceptions.MoveException

import scala.util.Try

case class Position(x: Int, y: Int) {
  override def toString() = s"($x, $y)"
}

case class Mower(
                  var position: Position,
                  var orientation: Orientation
                ) extends LazyLogging {

  // mutate the position of the mower
  def nextPosition(newPosition: Position, newOrientation: Orientation) = {
    position = newPosition
    orientation = newOrientation
  }

  // method moving the mower within the pitch
  def moveMower(pitch: Pitch, move: Move): Position = {
    logger.debug(s"Mower at position $position to move forward")
    orientation match {
      case Orientation.NORTH => Position(position.x, position.y + 1)
      case Orientation.SOUTH => Position(position.x, position.y - 1)
      case Orientation.WEST => Position(position.x - 1, position.y)
      case Orientation.EAST => Position(position.x + 1, position.y)
    }
  }

  // method changing the orientation of the mower
  def executeCommand(pitch: Pitch, move: Move) = {
    move match {
      case Move.RIGHT => nextPosition(position, turnRight(orientation))
      case Move.LEFT => nextPosition(position, turnLeft(orientation))
      case Move.FORWARD if pitch.isNextMoveInside(this) =>
        nextPosition(moveMower(pitch, move), orientation)
      case Move.FORWARD if !pitch.isNextMoveInside(this) =>
        throw MoveException("Cannot move forward else it would end up outside the pitch")
    }
  }

  // tell its current position when instructions are done
  def communicate = {
    s"I am currently located at $position orientated towards $orientation"
  }

}

object Mower {
  def apply(x: Int, y: Int, orientation: Orientation): Mower = {
    Mower(
      position = Position(x, y),
      orientation = orientation
    )
  }

  def empty(): Mower = {
    Mower(0, 0, Orientation.NORTH)
  }
}