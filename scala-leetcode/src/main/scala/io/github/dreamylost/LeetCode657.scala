package io.github.dreamylost

/**
 * 657. 机器人能否返回原点
 *
 * @author 梦境迷离
 * @since 2020-04-01
 * @version v1.0
 */
object LeetCode657 extends App {

  Console println judgeCircle("UD")

  def judgeCircle(moves: String): Boolean = {
    val cs = moves.toCharArray
    //512 ms
    var i = 0
    var j = 0
    cs.foreach {
      case 'L' => i += 1
      case 'R' => i -= 1
      case 'U' => j += 1
      case 'D' => j -= 1
    }
    j == 0 && i == 0
  }


  def judgeCircle2(moves: String): Boolean = {
    val cs = moves.toCharArray
    //580 ms
    cs.count(_ == 'L') == cs.count(_ == 'R') && cs.count(_ == 'U') == cs.count(_ == 'D')
  }
}
