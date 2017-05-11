/*
meanandsd.scala

Sketch solution to the Chapter 3 exercise

 */

object MeanAndSD {

  import scala.collection.GenSeq

  def meanAndSD(x: GenSeq[Double]): (Double, Double) = {
    val n = x.length
    val sx = x.sum
    val ssx = (x map (xi=>(xi*xi))).sum
    val v = (ssx - sx*sx/n)/(n-1)
    (sx/n, math.sqrt(v))
  }

  def meanAndSD1pass(x: GenSeq[Double]): (Double, Double) = {
    val n = x.length
    val (sx,ssx) = x.foldLeft((0.0,0.0): (Double,Double))(
      (p,xi) => (p._1+xi,p._2+xi*xi)
    )
    val v = (ssx - sx*sx/n)/(n-1)
    (sx/n, math.sqrt(v))
  }

  def meanAndSD1passPar(x: GenSeq[Double]): (Double, Double) = {
    val n = x.length
    val (sx,ssx) = x.aggregate((0.0,0.0): (Double,Double))(
      (p,xi) => (p._1+xi,p._2+xi*xi),
      (p1,p2) => (p1._1+p2._1, p1._2+p2._2)
    )
    val v = (ssx - sx*sx/n)/(n-1)
    (sx/n, math.sqrt(v))
  }

  def time[A](f: => A) = {
    val s = System.nanoTime
    val ret = f
    println("time: " + (System.nanoTime - s) / 1e6 + "ms")
    ret
  }


  def main(args: Array[String]): Unit = {
    println("hello")
    println(meanAndSD(Vector(1,4,7)))
    println(meanAndSD1pass(Vector(1,4,7)))
    println(meanAndSD1passPar(Vector(1,4,7)))
    val x = Vector.fill(10000000)(math.random)
    val xp = x.par
    time { println(meanAndSD(x)) }
    time { println(meanAndSD(xp)) }
    time { println(meanAndSD1pass(x)) }
    time { println(meanAndSD1pass(xp)) }
    time { println(meanAndSD1passPar(x)) }
    time { println(meanAndSD1passPar(xp)) }
    println("goodbye")
  }

}


// eof

