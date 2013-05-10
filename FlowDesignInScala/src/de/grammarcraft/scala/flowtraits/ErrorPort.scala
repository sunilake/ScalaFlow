/*
 * Copyright 2013 Denis Kuniss kuniss@grammarcraft.de
 * 
 * This file is part of the Flow-Design Scala Library.
 * The Flow-Design Scala Library is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * The Flow-Design Scala Library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License
 * along with the Flow-Design Scala Library.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.grammarcraft.scala.flowtraits

/**
 * @author denis.kuniss
 *
 */
trait ErrorPort[T] {
  
  private[this] var errorOperations: List[T => Unit] = List()
  
  def bindErrorTo(operation: T => Unit) {
	  errorOperations = operation :: errorOperations
  }
  
  protected def error(exception: T) {
	  if (!errorOperations.isEmpty) {
		  errorOperations.foreach(forward => forward(exception))
	  }
	  else {
		  println("no binding defined for error of " + this)
	  }
  }

}