/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost.cakepattern

/**
  * @author 梦境迷离
  * @version 1.0,2020/6/10
  */
object AppSpec extends App {

  APP.car.drive()
  APP.car.park()
}

object APP extends CarComponent with EngineComponent {
  override val car: APP.Car = new HondaCar() //实例化时使用了engine
  override val engine: APP.Engine = new DieselEngine()

}
