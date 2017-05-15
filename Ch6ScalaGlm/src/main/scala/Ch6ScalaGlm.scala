/*
scala-glm example
*/

object ScalaGlm {

  def main(args: Array[String]): Unit = {

    import scalaglm.Lm
    import breeze.linalg._
    val y = DenseVector(1.0,2.0,1.0,1.5)
    val X = DenseMatrix((1.0,1.5),(1.5,2.0),(2.0,1.5),(2.0,1.0))
    val lm = Lm(y,X,List("V1","V2"))
    println(lm.coefficients)
    lm.summary

  }

}
