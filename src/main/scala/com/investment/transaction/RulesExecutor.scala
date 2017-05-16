package com.investment.transaction

import com.investment.transaction.KieSessionFactory;

/*
 * Class to execute the rules
 */
class RulesExecutor (drlFileName : String)  extends Serializable {
  //evaluate all the rules and send the result back to 
  def evalRules (incomingEvents : Iterator[Trade]) : Iterator[Trade] = {
      val ksession = KieSessionFactory.getKieSession(drlFileName)
      val trades = incomingEvents.map(trade => {
        ksession.execute(trade)
        trade          
      })
              
      trades        
  }
}