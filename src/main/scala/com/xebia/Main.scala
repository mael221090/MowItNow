package com.xebia

import com.typesafe.scalalogging.LazyLogging
import com.xebia.modules.ApplicationModule
import com.xebia.services.MowerService
import scaldi.Injectable

/*
 * Simple app mowing the pitch
 */
object Main extends App with Injectable with LazyLogging {

  logger.info("Main App started ... ")

  implicit val appModule = new ApplicationModule

  val mowerService = inject [MowerService]

  mowerService.executeProgram("/instructions.txt")

}
