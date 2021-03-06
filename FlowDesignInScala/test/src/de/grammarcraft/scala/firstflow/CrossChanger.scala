package de.grammarcraft.scala.firstflow

import scala.actors.Actor

class CrossChanger(val name: String) extends Actor {
  
  case class Input1(msg: Any)
  case class Input2(msg: Any)
  
  var output1: Any => Unit = null
  var output2: Any => Unit = null
  
  def act() {
    loop {
      react {
        case Input1(msg) => {
        	println(msg + " received as input1 for " + this)
        	processInput1(msg)
        }
        case Input2(msg) => {
        	println(msg + " received as input2 for " + this)
        	processInput2(msg)
        }
        case "STOP" => exit()
        case msg => println("Unknown msg received: " + msg)
      }
    }
  }
  
  def processInput1(msg: Any) {
    println("process " + msg + " as input1 for " + this + " takes 1 second...")
    Thread.sleep(1000) 
    if (output2 == null) {
    	println("no binding defined for output2")
    	return
    }
    output2(msg)
  }

  def processInput2(msg: Any) {
    println("process " + msg + " as input2 for " + this)
    if (output1 == null) {
    	println("no binding defined for output2")
    	return
    }
    output1(msg)
  }

  def input1(msg: Any) {
    this!Input1(msg)
  }

  def input2(msg: Any) {
    this!Input2(msg)
  }
  
  override def toString:String = name

  def stop() {
    this!"STOP"
  }

}
