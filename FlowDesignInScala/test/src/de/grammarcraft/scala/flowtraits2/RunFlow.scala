package de.grammarcraft.scala.flowtraits2

import de.grammarcraft.scala.flow.ControlStructures._

object RunFlow {
  def main(args: Array[String]) {
	  // build
	  println("instantiate flow units...")
	  val reverse = new Reverse
	  val normalizer = new Normalize
	  val collector = new Collector(", ")
	  val notConnectedFunctionUnit = new Reverse
	  
	  // bind
	  println("bind them...")
	  reverse -> normalizer
	  normalizer.lower -> collector.lower
	  normalizer.upper -> collector.upper
	  on(collector.output) { msg => 
		  println("received '" + msg + "' from " + collector)
	  }

	  onErrorAt(collector)  {
		  errMsg => println("error received: " + errMsg)
	  }
	  
	  onIntegrationErrorAt(reverse, normalizer, notConnectedFunctionUnit) {
		  errMsg => System.err.println("integration error happened: " + errMsg)
	  }
	  
	  // run
	  println("run them...")
	  val palindrom = "Trug Tim eine so helle Hose nie mit Gurt?"
	  println("send message: " + palindrom)
	  reverse <= palindrom

	  reverse <= palindrom // second call should raise an error message on the Collectors error port

	  notConnectedFunctionUnit <= palindrom // should raise an integration error as function unit is not connected to any one
	  
	  println("finished.")
	  
  }

}