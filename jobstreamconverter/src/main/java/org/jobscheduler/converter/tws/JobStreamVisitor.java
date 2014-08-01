package org.jobscheduler.converter.tws;

import org.jobscheduler.converter.tws.TivoliWorkloadSchedulerJobStreamGrammarParser.InitContext;

public class JobStreamVisitor extends
		TivoliWorkloadSchedulerJobStreamGrammarBaseVisitor<Schedule> {
	
	// Define whitespace rule, toss it out
	@Override
	public Schedule visitInit(InitContext ctx) {
		System.out.println("JobStreamVisitor.visit() :  " + ctx.getText());
		return super.visitInit(ctx);
	}
}
