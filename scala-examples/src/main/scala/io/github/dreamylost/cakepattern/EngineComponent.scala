/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost.cakepattern

import io.github.dreamylost.cakepattern.FuelType.FuelType

/**
 * @author 梦境迷离
 * @version 1.0,2020/6/10
 */
trait EngineComponent {

  trait Engine {

    private var running = false

    def start(): Unit = {
      println("Engine start")
    }

    def stop(): Unit = {
      println("Engine stop")
    }

    def isRunning: Boolean = running

    def fuelType: FuelType
  }

  val engine: Engine

  protected class DieselEngine extends Engine {
    override val fuelType = FuelType.Diesel
  }

}

object FuelType extends Enumeration {
  type FuelType = Value
  val Diesel = Value
}

//需要混入了EngineComponent的才能使用CarComponent
trait CarComponent {
  this: EngineComponent =>

  trait Car {
    def drive(): Unit

    def park(): Unit
  }

  val car: Car

  protected class HondaCar extends Car {
    override def drive() = {
      engine.start()
      println("Vroom vroom")
    }

    override def park() = {
      println("HondaCar")
    }
  }

}
