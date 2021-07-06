package tuples

import tuples.events.EnergyTrafficEvent

/**
 * Plant failures can be either electrical or mechanical
 */
object PlantFailure extends Enumeration {
  val Electrical, Mechanical  = Value
}

