package com.xebia.models

sealed trait MowerMessage

case class LaunchMowing(mower: Mower, instructions: Instructions, pitch: Pitch) extends MowerMessage
case class StartInstructions(mower: Mower, instructions: Instructions, pitch: Pitch) extends MowerMessage
case class BroadcastPosition(mower: Mower) extends MowerMessage
case class CommunicatePosition(mower: Mower) extends MowerMessage
case class FinishedMowing() extends MowerMessage