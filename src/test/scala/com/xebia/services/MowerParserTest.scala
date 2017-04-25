package com.xebia.services

import java.io.File

import com.xebia.BaseSpec
import com.xebia.constants.{Move, Orientation, OrientationUtils}
import com.xebia.exceptions.FileParserException

import scala.io.Source

class MowerParserTest extends BaseSpec {

  val service = new MowerParser()

  "FileReaderTest" should {

    "parse pitch if 2 integers separated with spaces" in {
      val pitch = service.parsePitch("5 5")

      pitch.width mustBe 5
      pitch.height mustBe 5
    }

    "not parse pitch with non integers as coordinates" in {
      intercept[FileParserException]{
        service.parsePitch("toto 5")
      }
    }

    "not parse pitch if not exactly two digits are given" in {
      intercept[FileParserException]{
        service.parsePitch("toto 5 4")
      }
    }

    "parse mower configuration if 2 integers and one orientation given with spaces" in {
      val mower = service.parseMower("5 5 N")

      mower.position.x mustBe 5
      mower.position.y mustBe 5
      mower.orientation mustBe Orientation.NORTH
    }

    "not parse mower parameters if not exactly 3 parameters are given" in {
      intercept[FileParserException]{
        service.parseMower("toto 5")
      }
    }

    "parse correctly mower commands" in {
      val mowerCommands = service.parseInstructions("GAGAGAGAA")

      mowerCommands.moves.size mustBe 9
      mowerCommands.moves(0) mustBe Move.LEFT
      mowerCommands.moves(1) mustBe Move.FORWARD
      mowerCommands.moves(2) mustBe Move.LEFT
      mowerCommands.moves(3) mustBe Move.FORWARD
      mowerCommands.moves(4) mustBe Move.LEFT
      mowerCommands.moves(5) mustBe Move.FORWARD
      mowerCommands.moves(6) mustBe Move.LEFT
      mowerCommands.moves(7) mustBe Move.FORWARD
      mowerCommands.moves(8) mustBe Move.FORWARD
    }

    "not parse mower commands if anything else than D,A,G given" in {
      intercept[FileParserException]{
        service.parseInstructions("GAGAGAGAA0")
      }
    }

  }
}
