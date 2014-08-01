package org.jobscheduler.converter.tws;

import java.io.IOException;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class JobStreamCompiler {

	public static void main(String[] args) {
		JobStreamCompiler compiler = new JobStreamCompiler();
		try {
			compiler.compile(args[0]);
			System.out.println("Compile file " + args[0] + " correctly");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void compile(String jobStreamFile) throws IOException {
		try {
			// lexer splits input into tokens
			ANTLRFileStream input = new ANTLRFileStream(jobStreamFile);
			TokenStream tokens = new CommonTokenStream(
					new TivoliWorkloadSchedulerJobStreamGrammarLexer(input));

			// parser generates abstract syntax tree
			TivoliWorkloadSchedulerJobStreamGrammarParser parser = new TivoliWorkloadSchedulerJobStreamGrammarParser(
					tokens);

			ParseTree tree = parser.init();
			JobStreamVisitor visitor = new JobStreamVisitor();
			visitor.visit(tree);
		} catch (RecognitionException e) {
			throw new IllegalStateException(
					"Recognition exception is never thrown, only declared.");
		}
	}
}
