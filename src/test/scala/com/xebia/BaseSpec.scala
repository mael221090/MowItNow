package com.xebia

import com.xebia.modules.ApplicationModule
import org.scalatest._
import scaldi.Injectable

trait BaseSpec extends MustMatchers with WordSpecLike with GivenWhenThen with Injectable {

  implicit val testModule = new ApplicationModule


}
