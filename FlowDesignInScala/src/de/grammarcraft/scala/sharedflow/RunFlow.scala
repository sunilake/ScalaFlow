package de.grammarcraft.scala.sharedflow


object RunFlow {
  def main(args: Array[String]) {
	  // build
	  println("instantiate flow units...")
	  val reverse = new Reverse
	  val toLower = new ToLower
	  val toUpper = new ToUpper
	  val collector = new Collector(", ")
	  
	  // bind
	  println("bind them...")
	  reverse bindOutputTo toLower.input
	  reverse bindOutputTo toUpper.input
	  toLower bindOutputTo collector.input1
	  toUpper bindOutputTo collector.input2
	  collector bindOutputTo(msg => {
		  println("received '" + msg + "' from " + collector)
		  reverse.stop()
		  toLower.stop()
		  toUpper.stop()
		  collector.stop()
	  })

	  // run
	  println("run them...")
	  reverse.start()
	  toLower.start()
	  toUpper.start()
	  collector.start()
	  val palindrom = "Trug Tim eine so helle Hose nie mit Gurt?"
	  println("send message: " + palindrom)
	  reverse.input(palindrom)

	  println("finished.")
  }

}