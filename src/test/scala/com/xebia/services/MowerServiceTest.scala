package com.xebia.services

import java.io.File

import com.xebia.BaseSpec
import com.xebia.constants.Orientation
import com.xebia.exceptions.FileParserException
import com.xebia.models.Position

class MowerServiceTest extends BaseSpec {

  "MowerServiceTest" should {

    val mowerService = inject [MowerService]

    "run properly mowers" in {

      val file = new File(getClass.getResource("/instructions.txt").getPath)
      val finalMowers = mowerService.runMowers(file)

      finalMowers.size mustBe 2
      finalMowers(0).position mustBe Position(1, 3)
      finalMowers(0).orientation mustBe Orientation.NORTH

      finalMowers(1).position mustBe Position(5, 1)
      finalMowers(1).orientation mustBe Orientation.EAST

    }

    "catch exception if not enough line to parse mower + instructions" in {
      val file = new File(getClass.getResource("/instructions-even.txt").getPath)
      intercept[FileParserException] {
        mowerService.runMowers(file)
      }
    }

    "run properly even if going outside" in {
      val file = new File(getClass.getResource("/instructions-outside.txt").getPath)
      val finalMowers = mowerService.runMowers(file)

      finalMowers.size mustBe 1
      finalMowers(0).position mustBe Position(1, 2)
      finalMowers(0).orientation mustBe Orientation.NORTH
    }

  }
}
