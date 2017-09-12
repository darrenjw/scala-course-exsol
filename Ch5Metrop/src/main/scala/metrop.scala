/*
metrop.scala

Solution the second MC exercise for Chapter 5

 */

object Metrop {

  import breeze.linalg._
  import breeze.stats.distributions._
  //import breeze.numerics._
  import breeze.plot._
  import scala.collection.GenSeq
  import breeze.stats.meanAndVariance

  def mcmcSummary(dv: DenseVector[Double]): Figure = {
    val len = dv.length
    val mav = meanAndVariance(dv)
    val mean = mav.mean
    val variance = mav.variance
    println(s"Iters=$len, Mean=$mean, variance=$variance")
    val f = Figure("MCMC Summary")
    f.height = 1000
    f.width = 1200
    val p0 = f.subplot(1, 2, 0)
    p0 += plot(linspace(1, len, len), dv)
    p0.xlabel = "Iteration"
    p0.ylabel = "Value"
    p0.title = "Trace plot"
    val p1 = f.subplot(1, 2, 1)
    p1 += hist(dv, 100)
    p1.xlabel = "Value"
    p1.title = "Marginal density"
    f
  }

  def time[A](f: => A) = {
    val s = System.nanoTime
    val ret = f
    println("time: "+(System.nanoTime-s)/1e6+"ms")
    ret 
  }

  def ll(x: GenSeq[Double])(mean: Double,stdev: Double): Double = {
    val gau = Gaussian(mean,stdev)
    x map (gau.logPdf) reduce (_+_)
  }

  def main(args: Array[String]): Unit = {
    println("hello")
    val x = Gaussian(10.0,2.0).sample(10000)
    val llx = ll(x.par) _
    def llv(s: DenseVector[Double]): Double = llx(s(0),s(1))
    val cov = DenseMatrix.eye[Double](2)*0.001
    val mcmc = MarkovChain.
      metropolisHastings(DenseVector(1.0,1.0),
        (x: DenseVector[Double]) =>
        MultivariateGaussian(x,cov))(
        x => llv(x)).
      steps
    val out = time { mcmc.
      drop(2000).
      take(10000).
      toArray }
    val mean = DenseVector(out map (_(0)))
    mcmcSummary(mean)
    val stdev = DenseVector(out map (_(1)))
    mcmcSummary(stdev)
    val f = Figure()
    val pl = f.subplot(1,1,0)
    pl += plot(mean,stdev,'.')
    println("goodbye")
  }

}



// eof
