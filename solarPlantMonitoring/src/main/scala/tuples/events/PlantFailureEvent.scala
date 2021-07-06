package tuples.events

import tuples.SolarPlant

/**
 * Encapsulates the failure event of a solar plant at a particular time
 **/
case class PlantFailureEvent(failure:String, plant:SolarPlant, timestamp:Double)
