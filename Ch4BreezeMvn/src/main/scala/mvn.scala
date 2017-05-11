/*
mvn.scala

Solution to the Breeze exercises for Chapter 4

 */

object Mvn {

  import breeze.linalg._
  import breeze.stats.distributions.Gaussian

  def rmvn(n: Int, mean: DenseVector[Double],
    cov: DenseMatrix[Double]): DenseMatrix[Double] = {
    require(cov.rows == cov.cols)
    require(mean.size == cov.rows)
    val p = mean.size
    val gau = Gaussian(0.0,1.0)
    val z = DenseMatrix.fill(n,p)(gau.draw)
    val ch = cholesky(cov)
    val x = z * ch.t
    x(*,::) + mean
  }

  import breeze.plot._
  def pairs(mat: DenseMatrix[Double]): Figure = {
    val fig = Figure("Scatterplot matrix")
    val p = mat.cols
      (0 until p).foreach{i =>
        (0 until p).foreach {j =>
          val pij = fig.subplot(p,p,i*p+j)
          if (i == j) {
            pij += hist(mat(::,i))
            pij.xlabel = i.toString
          } else {
            pij += plot(mat(::,j),mat(::,i),'.')
            pij.xlabel = j.toString
            pij.ylabel = i.toString
          }
        }
      }
    fig
  }

  def main(args: Array[String]): Unit = {
    println("hello")
    val mat = rmvn(1000,DenseVector(1.0,2.0,0),
      DenseMatrix((4.0,1.0,0.0),(1.0,2.0,0.0),(0.0,0.0,1.0)))
    import breeze.stats._
    println(mean(mat(::,*)).t)
    println(covmat(mat))
    val f = pairs(mat)
    println("goodbye")
  }

}

// eof
