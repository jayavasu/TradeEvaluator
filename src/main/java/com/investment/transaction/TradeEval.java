package com.investment.transaction;

import org.kie.api.runtime.StatelessKieSession;

public class TradeEval {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StatelessKieSession ksession = KieSessionFactory.getKieSession("src/main/resources/tradeEval.drl");
		Trade trade = new Trade("", 
				"acc_i", 
				"AA", 
				"A", 
				"Y", 
				"MFAA", 
				"99",
				"trd_dt",
				"trd_pst_dt",
				"910");
		System.out.println("Trade rule not applied >>>> "+trade.getRule_R001());
		ksession.execute( trade );
		System.out.println("Trade rule applied >>>> "+trade.getRule_R001());
	}

}
