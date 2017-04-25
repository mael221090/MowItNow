package com.xebia.modules

import akka.actor.ActorSystem
import com.typesafe.scalalogging.LazyLogging
import com.xebia.actors.{MowItNowActor, MowerActor}
import scaldi.Module

class AkkaModule extends Module with LazyLogging {

  bind [ActorSystem] to ActorSystem("MowItNow") destroyWith (_.terminate())

  binding toProvider new MowerActor
  binding toProvider new MowItNowActor

}