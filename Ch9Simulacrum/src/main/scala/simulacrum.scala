/*
simulacrum.scala


 */


import simulacrum._

object Simulacrum {

  @typeclass trait CsvRow[T] {
    def toCsv(row: T): String
  }

  import CsvRow.ops._

  def printRows[T: CsvRow](it: Iterable[T]): Unit =
    it.foreach(row => println(row.toCsv))

  case class MyState(x: Int, y: Double)

  implicit val myStateCsvRow = new CsvRow[MyState] {
    def toCsv(row: MyState) = row.x.toString+","+row.y
  }

  implicit val vectorDoubleCsvRow =
    new CsvRow[Vector[Double]] {
      def toCsv(row: Vector[Double]) = row.mkString(",")
    }

  @typeclass trait Thinnable[F[_]] {
    def thin[T](f: F[T], th: Int): F[T]
  }

  import Thinnable.ops._

  implicit val streamThinnable: Thinnable[Stream] =
    new Thinnable[Stream] {
      def thin[T](s: Stream[T],th: Int): Stream[T] = {
        val ss = s.drop(th-1)
        if (ss.isEmpty) Stream.empty else
                                       ss.head #:: thin(ss.tail, th)
      }
    }


  def main(args: Array[String]): Unit = {
    println("hello")
    println(MyState(1,2.0).toCsv)
    printRows(List(MyState(1,2.0),MyState(2,3.0)))
    println(Vector(1.0,2.0,3.0).toCsv)
    printRows(List(Vector(1.0,2.0),Vector(4.0,5.0),
      Vector(3.0,3.0)))
    Stream.iterate(0)(_ + 1).
      drop(10).
      thin(2).
      take(5).
      toArray.
      foreach(println)
    println("goodbye")
  }


}


/* eof */

