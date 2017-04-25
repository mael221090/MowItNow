package com.xebia.modules

import com.xebia.services.{MowerParser, MowerService}
import scaldi.Module

class ApplicationModule extends Module {

  binding to new MowerParser
  binding to injected[MowerService]

}
