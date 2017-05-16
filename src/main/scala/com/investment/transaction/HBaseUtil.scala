package com.investment.transaction

import org.apache.hadoop.hbase.client.Put
import org.apache.hadoop.hbase.util.Bytes

object HBaseUtil {
  val columnFamily: String = "dri"

  def insertEvaluatedDataIntoHBase(trade: Trade): Put = {
      val put = new Put(Bytes.toBytes(trade.getRowKey))
//      put.add(Bytes.toBytes(columnFamily), Bytes.toBytes("trd_dt"), Bytes.toBytes(trade.getTrd_dt))
//      put.add(Bytes.toBytes(columnFamily), Bytes.toBytes("trd_pst_dt"), Bytes.toBytes(trade.getTrd_pst_dt))
//      put.add(Bytes.toBytes(columnFamily), Bytes.toBytes("brn_i"), Bytes.toBytes(trade.getBrn_i))
//      put.add(Bytes.toBytes(columnFamily), Bytes.toBytes("acc_i"), Bytes.toBytes(trade.getAcc_i))
//      put.add(Bytes.toBytes(columnFamily), Bytes.toBytes("curr_ver"), Bytes.toBytes(trade.getCurr_ver))
//      put.add(Bytes.toBytes(columnFamily), Bytes.toBytes("brn_stat_c"), Bytes.toBytes(trade.getBrn_stat_c))
//      put.add(Bytes.toBytes(columnFamily), Bytes.toBytes("txn_rec_typ"), Bytes.toBytes(trade.getTxn_rec_typ))
//      put.add(Bytes.toBytes(columnFamily), Bytes.toBytes("pw_scty_i"), Bytes.toBytes(trade.getPw_scty_i))
//      put.add(Bytes.toBytes(columnFamily), Bytes.toBytes("pro_clasfn_c"), Bytes.toBytes(trade.getPro_clasfn_c))
      put.add(Bytes.toBytes(columnFamily), Bytes.toBytes("rule_R001"), Bytes.toBytes(trade.getRule_R001))
      put
  }
}