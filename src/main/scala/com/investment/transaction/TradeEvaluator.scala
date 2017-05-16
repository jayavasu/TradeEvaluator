package com.investment.transaction

import org.apache.spark._
import org.apache.spark.SparkContext._
import org.apache.spark.streaming._
import org.apache.spark.streaming.StreamingContext._
import org.apache.spark.storage.StorageLevel
import org.apache.spark.rdd._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.kie.api.runtime.StatelessKieSession
import java.nio.file.Files
import java.nio.file.Paths
import org.apache.hadoop.hbase.HBaseConfiguration
import com.cloudera.spark.hbase.HBaseContext
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.hbase.client.Get
import org.apache.hadoop.hbase.client.Result
import org.apache.hadoop.hbase.client.Scan
import org.apache.hadoop.hbase.mapreduce.TableInputFormat

object TradeEvaluator {
  final val tableName = "trade"
  final val cfDri = "dri"
  final val cfDriBytes = Bytes.toBytes(cfDri)
  
  def parseTradeRow(result: Result): Trade = {
      val p0 = Bytes.toString(result.getRow())
      val p1 = Bytes.toString(result.getValue(cfDriBytes, Bytes.toBytes("acc_i")))
      val p2 = Bytes.toString(result.getValue(cfDriBytes, Bytes.toBytes("brn_i")))
      val p3 = Bytes.toString(result.getValue(cfDriBytes, Bytes.toBytes("brn_stat_c")))
      val p4 = Bytes.toString(result.getValue(cfDriBytes, Bytes.toBytes("curr_ver")))
      val p5 = Bytes.toString(result.getValue(cfDriBytes, Bytes.toBytes("pro_clasfn_c")))
      val p6 = Bytes.toString(result.getValue(cfDriBytes, Bytes.toBytes("pw_scty_i")))
      val p7 = Bytes.toString(result.getValue(cfDriBytes, Bytes.toBytes("trd_dt")))
      val p8 = Bytes.toString(result.getValue(cfDriBytes, Bytes.toBytes("trd_pst_dt")))
      val p9 = Bytes.toString(result.getValue(cfDriBytes, Bytes.toBytes("txn_rec_typ")))
      new Trade(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9)
    }
  
  def main(args: Array[String]) {
      if (args.length < 2) {
        System.err.println("Usage: TradeEvaluator <rulesFileName> <zk-quorum-for-hbase>")
        System.exit(1)
      }

      val rulesFileName = args(0)
      val zkString = args(1)
  
      //check if the rules file exists
      if (!Files.exists(Paths.get(rulesFileName))) {
        println("Error: Rules file [" + rulesFileName + "] is missing")
        return
      }
  
      //setup spark. Toggle below two lines for local v/s yarn
      val sparkConf = new SparkConf().setAppName("Transaction Rules Evaluation")
      //  val sparkConf = new SparkConf().setMaster("local[2]").setAppName("Sepsis Rules Streaming Evaluation")
     
      val sc = new SparkContext(sparkConf)
            
      //hbase config
      val hbaseConf = HBaseConfiguration.create()
      hbaseConf.set("hbase.zookeeper.quorum", zkString)
      hbaseConf.set(TableInputFormat.INPUT_TABLE, tableName)
    // scan dri column family
      hbaseConf.set(TableInputFormat.SCAN_COLUMNS, "dri")
      val hbaseContext = new HBaseContext(sc, hbaseConf)
      val tradeTable = "trade"
      
//      val scan = new Scan()     
      
      // Load an RDD of rowkey, result(ImmutableBytesWritable, Result) tuples from the table
    val hBaseRDD = sc.newAPIHadoopRDD(hbaseConf, classOf[TableInputFormat],
      classOf[org.apache.hadoop.hbase.io.ImmutableBytesWritable],
      classOf[org.apache.hadoop.hbase.client.Result])

    // transform (ImmutableBytesWritable, Result) tuples into an RDD of Results
    val resultRDD = hBaseRDD.map(tuple => tuple._2)
    
    // transform RDD of Results into an RDD of SensorRow objects 
    val tradeRDD = resultRDD.map(parseTradeRow)   
    println("resultRDD COUNT >>>>>>>>>>>>>>>>>>>> "+ tradeRDD.count())
    
    //setup rules executor
    val rulesExecutor = new RulesExecutor(rulesFileName)  
      
         
    val evaluatedTrade = tradeRDD.mapPartitions( incomingEvents => { rulesExecutor.evalRules(incomingEvents) } )
    
    //store the evaluation results in hbase
    hbaseContext.bulkPut[Trade](evaluatedTrade, tradeTable, HBaseUtil.insertEvaluatedDataIntoHBase, true)
    
/*    tradeRDD.collect.foreach( x => {      
      println(x.getRowKey()+","+x.getAcc_i+","+x.getBrn_i+","+x.getRule_R001) 
      })  */
            
//      val evaluatedTrade = sc.parallelize(evaluatedTradeList)
      
      //setup a transaction RDD. (Get data from HBase)
      /*var tradeScanRDD = hbaseContext.hbaseScanRDD(tradeTable, scan)
  
      println(" --- abc" + tradeScanRDD.count())
      tradeScanRDD.collect().foreach(v => println(Bytes.toString(v._1)))

      val tradeRDD = tradeScanRDD.collect.foreach {
        v => val rowKey = Bytes.toString(v._1) 
             val colList = v._2.iterator()
             val b = new StringBuilder
             val trade = new Trade()
             b.append("(" )
             while (colList.hasNext()) {
                val kv = colList.next()
                val q = Bytes.toString(kv._2)
                val values = Bytes.toString(kv._3) 
                b.append(q + "," + values + ",") 
                trade.set
              }
        b.append("," + rowKey + ")")
        println("OUTPUT >>>>>>>>>>>>>>>>>>>>>>>>>>>>> "+b.toString)        
      }*/    
      
//      val inter = tradeFinalRDD.map(incomingEvents => { incomingEvents.getRowKey+ " , "+ incomingEvents.getAcc_i })
//      
//      println("**********************************")
//      inter.collect.foreach(println)
//      
       /*tradeRDD.collect.foreach { rdd => 
        //evaluate all the rules
        val evaluatedTrade = rulesExecutor.evalRules(rdd)         
       }*/
//        val evaluatedTrade = tradeRDD.mapPartitions(incomingEvents => rulesExecutor.evalRules(incomingEvents))
    
        //store the evaluation results in hbase
//        hbaseContext.bulkPut[Trade](evaluatedTrade, tradeTable, HBaseUtil.insertEvaluatedDataIntoHBase, true)

  }
  
  //  case class Trade(rowkey: String, acc_i: String, brn_i: String, brn_stat_c: String, curr_ver: String, pro_clasfn_c: String, pw_scty_i: String, trd_dt: String, trd_pst_dt: String, txn_rec_typ: String)
  
  /*object Trade extends Serializable{
    def parseTradeRow(result: Result): Trade = {
      val p0 = Bytes.toString(result.getRow())
      val p1 = Bytes.toString(result.getValue(cfDriBytes, Bytes.toBytes("acc_i")))
      val p2 = Bytes.toString(result.getValue(cfDriBytes, Bytes.toBytes("brn_i")))
      val p3 = Bytes.toString(result.getValue(cfDriBytes, Bytes.toBytes("brn_stat_c")))
      val p4 = Bytes.toString(result.getValue(cfDriBytes, Bytes.toBytes("curr_ver")))
      val p5 = Bytes.toString(result.getValue(cfDriBytes, Bytes.toBytes("pro_clasfn_c")))
      val p6 = Bytes.toString(result.getValue(cfDriBytes, Bytes.toBytes("pw_scty_i")))
      val p7 = Bytes.toString(result.getValue(cfDriBytes, Bytes.toBytes("trd_dt")))
      val p8 = Bytes.toString(result.getValue(cfDriBytes, Bytes.toBytes("trd_pst_dt")))
      val p9 = Bytes.toString(result.getValue(cfDriBytes, Bytes.toBytes("txn_rec_typ")))
      Trade(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9)
    }
  }*/
}