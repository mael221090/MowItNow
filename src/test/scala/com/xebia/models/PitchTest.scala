package com.xebia.models

import com.xebia.BaseSpec
import com.xebia.constants.Orientation

class PitchTest extends BaseSpec {

  "IsNextMoveOutside" should {
    val pitch = Pitch(width = 5, height = 5)

    "be true if move towards north is inside the pitch" in {
      val mower = Mower(x = 2, y = 2, orientation = Orientation.NORTH)
      pitch.isNextMoveInside(mower) mustBe true
    }

    "be false if move towards north is outside the pitch" in {
      val mower = Mower(x = 2, y = 5, orientation = Orientation.NORTH)
      pitch.isNextMoveInside(mower) mustBe false
    }

    "be false if move towards south is outside the pitch" in {
      val mower = Mower(x = 0, y = 0, orientation = Orientation.SOUTH)
      pitch.isNextMoveInside(mower) mustBe false
    }

    "be false if move towards west is outside the pitch" in {
      val mower = Mower(x = 0, y = 2, orientation = Orientation.WEST)
      pitch.isNextMoveInside(mower) mustBe false
    }

    "be false if move towards east is outside the pitch" in {
      val mower = Mower(x = 5, y = 2, orientation = Orientation.EAST)
      pitch.isNextMoveInside(mower) mustBe false
    }

  }

}
