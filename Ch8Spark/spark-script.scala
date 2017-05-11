/*
spark-script.scala

spark-shell --master local[4] < spark-script.scala

 */

import org.apache.spark.ml.linalg._

val df = spark.read.csv("spambase.data")
df.show(5)

val dflr = (df map {row => (
  row.getString(57).toDouble,
  Vectors.dense(row.toSeq.toArray.take(57) map (
    _.asInstanceOf[String].toDouble)))}).
  toDF("label","features").persist
dflr show 5

// start with a basic non-regularised fit
import org.apache.spark.ml.classification._
val lr = new LogisticRegression
lr.explainParams
lr.setElasticNetParam(1.0)
lr.explainParams
val logrfit = lr.fit(dflr)
logrfit.intercept
logrfit.coefficients

// now tune a lasso model
import breeze.linalg.linspace
val lambdas = linspace(-12,4,50).toArray.map(math.exp(_))
import org.apache.spark.ml.tuning._
import org.apache.spark.ml.evaluation._
val paramGrid = new ParamGridBuilder().
  addGrid(lr.regParam,lambdas).
  build()
val cv = new CrossValidator().
  setEstimator(lr).
  setEvaluator(new BinaryClassificationEvaluator).
  setEstimatorParamMaps(paramGrid).
  setNumFolds(8)
val cvMod = cv.fit(dflr)
cvMod.bestModel.extractParamMap
val lambda = cvMod.bestModel.extractParamMap.getOrElse(cvMod.bestModel.getParam("regParam"),0.0).asInstanceOf[Double]

// fit and analyse the optimal model
lr.setRegParam(lambda)
val lasso = lr.fit(dflr)
lasso.intercept
lasso.coefficients // none shrunk to zero...


// eof

