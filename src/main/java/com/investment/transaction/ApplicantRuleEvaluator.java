package com.investment.transaction;
import java.io.File;

import org.kie.api.runtime.StatelessKieSession;

public class ApplicantRuleEvaluator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		KieServices kieServices = KieServices.Factory.get();
//		KieContainer kContainer = kieServices.getKieClasspathContainer();
		
//		StatelessKieSession kSession = kContainer.newStatelessKieSession();
//		File path = new File("D:\\workspace\\transaction\\src\\main\\resources\\applicant.drl");
//		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
//	    kbuilder.add(ResourceFactory.newFileResource(path), ResourceType.DRL);
		
		StatelessKieSession ksession = KieSessionFactory.getKieSession("src/main/resources/applicant.drl");
		Applicant applicant = new Applicant( "Mr John Smith", 16 );
//		assertTrue( applicant.isValid() );
		System.out.println("Applicant is valid >>>> "+applicant.isValid());
		System.out.println("Applicant is  >>>> "+applicant.getName());
		ksession.execute( applicant );
//		assertFalse( applicant.isValid() );
		System.out.println("Applicant is invalid >>>> "+applicant.isValid());

	}

}
