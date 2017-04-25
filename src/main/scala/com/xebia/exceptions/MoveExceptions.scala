package com.xebia.exceptions

case class MoveConversionException() extends Exception()

case class MoveException(msg: String) extends Exception(msg)