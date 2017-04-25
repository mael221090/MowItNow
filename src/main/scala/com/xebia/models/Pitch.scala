package com.xebia.models

import com.xebia.constants.Orientation

case class Pitch(
                  width: Int,
                  height: Int
                ) {

  // method checking if next mower's movement would end up been outside the pitch
  def isNextMoveInside(mower: Mower): Boolean = {
    mower.orientation match {
      case Orientation.NORTH => mower.position.y + 1 <= height
      case Orientation.SOUTH => mower.position.y - 1 >= 0
      case Orientation.WEST => mower.position.x - 1 >= 0
      case Orientation.EAST => mower.position.x + 1 <= width
    }
  }

}
