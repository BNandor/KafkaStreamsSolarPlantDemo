package tuples.events

import tuples.SolarPlant

/**
 * Encapsulates the weather condition event at a solar plant at a particular time
 **/
case class WeatherInformationEvent(lumenPerSqFoot:Double, celsius:Double, humidityGramPerCubicMeter:Double, plant:SolarPlant, timestamp:Double)
