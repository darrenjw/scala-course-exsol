/*
mvn.scala

Solution to the Breeze exercises for Chapter 4

 */

object MC {

  import breeze.linalg._
  import breeze.stats.distributions._
  import breeze.numerics._
  import breeze.plot._

  def main(args: Array[String]): Unit = {
    println("hello")
    // create variable monadically
    val rv = for {
      n <- Poisson(20.0)
      p <- new Beta(4.0,4.0)
      z <- Binomial(n,p)
    } yield z
    // now sample it and analyze empirically
    println(rv.sample(10))
    val n = 10000
    val samp = rv.sample(n)
    println("Empirical mean: "+samp.sum.toDouble/n)
    val fig = Figure("Empirical distribution")
    val p = fig.subplot(1,1,0)
    p += hist(samp)
    p.xlabel = "Value"
    p.ylabel = "Frequency"
    p.title = "Empirical distribution"
    fig.refresh
    println("goodbye")
  }

}

// eof
