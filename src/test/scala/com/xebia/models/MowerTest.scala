package com.xebia.models

import com.xebia.BaseSpec
import com.xebia.constants.{Move, Orientation}
import com.xebia.exceptions.MoveException


class MowerTest extends BaseSpec {

  "Mower" should {
    val pitch = Pitch(width = 5, height = 5)

    "moveMower" in {

      Given("a mower with parameters 1,1,N")
      val mowerNorth = Mower(x = 1, y = 1, orientation = Orientation.NORTH)

      When("it moves forward")
      val newMowerNorth = mowerNorth.moveMower(pitch, move = Move.FORWARD)

      Then("the new mower should have same parameters except y = y + 1")
      newMowerNorth mustBe Position(x = 1, y = 2)

      Given("a mower with parameters 1,1,S")
      val mowerSouth = Mower(x = 1, y = 1, orientation = Orientation.SOUTH)

      When("it moves forward")
      val newMowerSouth = mowerSouth.moveMower(pitch, move = Move.FORWARD)

      Then("the new mower should have same parameters except y = y - 1")
      newMowerSouth mustBe Position(x = 1, y = 0)

      Given("a mower with parameters 1,1,W")
      val mowerWest = Mower(x = 1, y = 1, orientation = Orientation.WEST)

      When("it moves forward")
      val newMowerWest = mowerWest.moveMower(pitch, move = Move.FORWARD)

      Then("the new mower should have same parameters except x = x - 1")
      newMowerWest mustBe Position(x = 0, y = 1)

      Given("a mower with parameters 1,1,E")
      val mowerEast = Mower(x = 1, y = 1, orientation = Orientation.EAST)

      When("it moves forward")
      val newMowerEast = mowerEast.moveMower(pitch, move = Move.FORWARD)

      Then("the new mower should have same parameters except x = x + 1")
      newMowerEast mustBe Position(x = 2, y = 1)

    }

    "changeOrientation" in {

      Given("a mower with parameters 0,0,S")
      val mowerSouth = Mower(x = 0, y = 0, orientation = Orientation.SOUTH)

      When("it moves forward")
      Then("it should throw an exception")
      intercept[MoveException] {
        mowerSouth.executeCommand(pitch, move = Move.FORWARD)
      }

      Given("a mower with parameters 0,5,N")
      val mowerNorth = Mower(x = 0, y = 5, orientation = Orientation.NORTH)

      When("it moves forward")
      Then("it should throw an exception")
      intercept[MoveException] {
        mowerNorth.executeCommand(pitch, move = Move.FORWARD)
      }

      Given("a mower with parameters 5,0,E")
      val mowerNeast = Mower(x = 5, y = 0, orientation = Orientation.EAST)

      When("it moves forward")
      Then("it should throw an exception")
      intercept[MoveException] {
        mowerNeast.executeCommand(pitch, move = Move.FORWARD)
      }

      Given("a mower with parameters 0,5,W")
      val mowerWest = Mower(x = 0, y = 5, orientation = Orientation.WEST)

      When("it moves forward")
      Then("it should throw an exception")
      intercept[MoveException] {
        mowerWest.executeCommand(pitch, move = Move.FORWARD)
      }

      Given("a mower with any x & y parameters but oriented towards W")
      val mowerWestRight = Mower(x = 2, y = 3, orientation = Orientation.WEST)

      When("it changes to the Right")
      mowerWestRight.executeCommand(pitch, move = Move.RIGHT)

      Then("it should be orientated towards North")
      mowerWestRight.orientation mustBe Orientation.NORTH

      Given("a mower with any x & y parameters but oriented towards N")
      val mowerNorthRight = Mower(x = 2, y = 3, orientation = Orientation.NORTH)

      When("it changes to the Right")
      mowerNorthRight.executeCommand(pitch, move = Move.RIGHT)

      Then("it should be orientated towards East")
      mowerNorthRight.orientation mustBe Orientation.EAST

      Given("a mower with any x & y parameters but oriented towards E")
      val mowerEastRight = Mower(x = 2, y = 3, orientation = Orientation.EAST)

      When("it change to the right")
      mowerEastRight.executeCommand(pitch, move = Move.RIGHT)

      Then("it should be orientated towards South")
      mowerEastRight.orientation mustBe Orientation.SOUTH

      Given("a mower with any x & y parameters but oriented towards S")
      val mowerSouthRight = Mower(x = 2, y = 3, orientation = Orientation.SOUTH)

      When("it changes to the Right")
      mowerSouthRight.executeCommand(pitch, move = Move.RIGHT)

      Then("it should be orientated towards West")
      mowerSouthRight.orientation mustBe Orientation.WEST

      Given("a mower with any x & y parameters but oriented towards W")
      val mowerWestLeft = Mower(x = 0, y = 5, orientation = Orientation.WEST)

      When("it changes to the left")
      mowerWestLeft.executeCommand(pitch, move = Move.LEFT)

      Then("it should be orientated towards South")
      mowerWestLeft.orientation mustBe Orientation.SOUTH

      Given("a mower with any x & y parameters but oriented towards S")
      val mowerSouthLeft = Mower(x = 0, y = 5, orientation = Orientation.SOUTH)

      When("it changes to the left")
      mowerSouthLeft.executeCommand(pitch, move = Move.LEFT)

      Then("it should be orientated towards East")
      mowerSouthLeft.orientation mustBe Orientation.EAST

      Given("a mower with any x & y parameters but oriented towards E")
      val mowerEastLeft = Mower(x = 0, y = 5, orientation = Orientation.EAST)

      When("it changes to the left")
      mowerEastLeft.executeCommand(pitch, move = Move.LEFT)

      Then("it should be orientated towards North")
      mowerEastLeft.orientation mustBe Orientation.NORTH

      Given("a mower with any x & y parameters but oriented towards N")
      val mowerNorthLeft = Mower(x = 0, y = 5, orientation = Orientation.NORTH)

      When("it changes to the left")
      mowerNorthLeft.executeCommand(pitch, move = Move.LEFT)

      Then("it should be orientated towards West")
      mowerNorthLeft.orientation mustBe Orientation.WEST

    }

  }
}
