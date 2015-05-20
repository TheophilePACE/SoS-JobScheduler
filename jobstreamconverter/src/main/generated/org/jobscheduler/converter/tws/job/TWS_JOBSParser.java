// $ANTLR 3.5.2 TWS_JOBS.g 2015-05-20 10:20:15

 
package org.jobscheduler.converter.tws.job;
import  org.jobscheduler.converter.tws.scheduler.* ;
import  org.jobscheduler.converter.tws.* ;
import  org.jobscheduler.converter.tws.scheduler.Job.Description ;
import  org.jobscheduler.converter.tws.scheduler.JobChain.JobChainNode ;
import java.util.HashMap ;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

import org.antlr.runtime.tree.*;


@SuppressWarnings("all")
public class TWS_JOBSParser extends Parser {
	public static final String[] tokenNames = new String[] {
		"<invalid>", "<EOR>", "<DOWN>", "<UP>", "DATE_LITERAL", "FILE_NAME", "ID", 
		"NUMBER", "STRING_LITERAL", "WS", "'$JOBS'", "'ABENDPROMPT'", "'AFTER'", 
		"'CONTINUE'", "'DESCRIPTION'", "'DOCOMMAND'", "'INTERACTIVE'", "'RCCONDSUCC'", 
		"'RECOVERY'", "'RERUN'", "'SCRIPTNAME'", "'STOP'", "'STREAMLOGON'", "'TASKTYPE'"
	};
	public static final int EOF=-1;
	public static final int T__10=10;
	public static final int T__11=11;
	public static final int T__12=12;
	public static final int T__13=13;
	public static final int T__14=14;
	public static final int T__15=15;
	public static final int T__16=16;
	public static final int T__17=17;
	public static final int T__18=18;
	public static final int T__19=19;
	public static final int T__20=20;
	public static final int T__21=21;
	public static final int T__22=22;
	public static final int T__23=23;
	public static final int DATE_LITERAL=4;
	public static final int FILE_NAME=5;
	public static final int ID=6;
	public static final int NUMBER=7;
	public static final int STRING_LITERAL=8;
	public static final int WS=9;

	// delegates
	public Parser[] getDelegates() {
		return new Parser[] {};
	}

	// delegators


	public TWS_JOBSParser(TokenStream input) {
		this(input, new RecognizerSharedState());
	}
	public TWS_JOBSParser(TokenStream input, RecognizerSharedState state) {
		super(input, state);
	}

	protected TreeAdaptor adaptor = new CommonTreeAdaptor();

	public void setTreeAdaptor(TreeAdaptor adaptor) {
		this.adaptor = adaptor;
	}
	public TreeAdaptor getTreeAdaptor() {
		return adaptor;
	}
	@Override public String[] getTokenNames() { return TWS_JOBSParser.tokenNames; }
	@Override public String getGrammarFileName() { return "TWS_JOBS.g"; }


	public static class jobs_return extends ParserRuleReturnScope {
		public HashMap<String,Job> jobs;
		public HashMap<String,JobChainNode> states;
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "jobs"
	// TWS_JOBS.g:25:1: jobs[boolean test] returns [HashMap<String,Job> jobs,HashMap<String,JobChainNode> states ] : '$JOBS' ( jobDefinition[$test] )* ;
	public final TWS_JOBSParser.jobs_return jobs(boolean test) throws RecognitionException {
		TWS_JOBSParser.jobs_return retval = new TWS_JOBSParser.jobs_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token string_literal1=null;
		ParserRuleReturnScope jobDefinition2 =null;

		Object string_literal1_tree=null;

		try {
			// TWS_JOBS.g:25:91: ( '$JOBS' ( jobDefinition[$test] )* )
			// TWS_JOBS.g:26:2: '$JOBS' ( jobDefinition[$test] )*
			{
			root_0 = (Object)adaptor.nil();



			 retval.jobs = new HashMap<String,Job>() ;
			 retval.states = new HashMap<String,JobChainNode>() ;
			 
			string_literal1=(Token)match(input,10,FOLLOW_10_in_jobs63); 
			string_literal1_tree = (Object)adaptor.create(string_literal1);
			adaptor.addChild(root_0, string_literal1_tree);

			// TWS_JOBS.g:32:2: ( jobDefinition[$test] )*
			loop1:
			while (true) {
				int alt1=2;
				int LA1_0 = input.LA(1);
				if ( (LA1_0==ID) ) {
					alt1=1;
				}

				switch (alt1) {
				case 1 :
					// TWS_JOBS.g:32:2: jobDefinition[$test]
					{
					pushFollow(FOLLOW_jobDefinition_in_jobs67);
					jobDefinition2=jobDefinition(test);
					state._fsp--;

					adaptor.addChild(root_0, jobDefinition2.getTree());


					retval.jobs.put((jobDefinition2!=null?((TWS_JOBSParser.jobDefinition_return)jobDefinition2).fullId:null), (jobDefinition2!=null?((TWS_JOBSParser.jobDefinition_return)jobDefinition2).job:null)) ;
					Launcher.states.put((jobDefinition2!=null?((TWS_JOBSParser.jobDefinition_return)jobDefinition2).fullId:null), (jobDefinition2!=null?((TWS_JOBSParser.jobDefinition_return)jobDefinition2).state:null)) ;
					//Launcher.logger.info("putting "+(jobDefinition2!=null?((TWS_JOBSParser.jobDefinition_return)jobDefinition2).fullId:null)) ;
					Launcher.recoveries.put((jobDefinition2!=null?((TWS_JOBSParser.jobDefinition_return)jobDefinition2).fullId:null), (jobDefinition2!=null?((TWS_JOBSParser.jobDefinition_return)jobDefinition2).recovery:null)) ;

					}
					break;

				default :
					break loop1;
				}
			}


			Launcher.logger.info(Launcher.states.size()+" states collected") ;

			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "jobs"


	public static class jobDefinition_return extends ParserRuleReturnScope {
		public String fullId;
		public Job job;
		public JobChainNode state;
		public Recovery recovery;
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "jobDefinition"
	// TWS_JOBS.g:47:1: jobDefinition[boolean test] returns [String fullId,Job job,JobChainNode state,Recovery recovery] :jobId= ID ( ( 'DOCOMMAND' command= STRING_LITERAL ) | ( 'SCRIPTNAME' path= STRING_LITERAL ) ) 'STREAMLOGON' ( ID | STRING_LITERAL ) ( description )? ( tasktype )? ( 'INTERACTIVE' )? ( successCondition )? ( recoveryOptions )? ;
	public final TWS_JOBSParser.jobDefinition_return jobDefinition(boolean test) throws RecognitionException {
		TWS_JOBSParser.jobDefinition_return retval = new TWS_JOBSParser.jobDefinition_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token jobId=null;
		Token command=null;
		Token path=null;
		Token string_literal3=null;
		Token string_literal4=null;
		Token string_literal5=null;
		Token set6=null;
		Token string_literal9=null;
		ParserRuleReturnScope description7 =null;
		ParserRuleReturnScope tasktype8 =null;
		ParserRuleReturnScope successCondition10 =null;
		ParserRuleReturnScope recoveryOptions11 =null;

		Object jobId_tree=null;
		Object command_tree=null;
		Object path_tree=null;
		Object string_literal3_tree=null;
		Object string_literal4_tree=null;
		Object string_literal5_tree=null;
		Object set6_tree=null;
		Object string_literal9_tree=null;

		try {
			// TWS_JOBS.g:47:98: (jobId= ID ( ( 'DOCOMMAND' command= STRING_LITERAL ) | ( 'SCRIPTNAME' path= STRING_LITERAL ) ) 'STREAMLOGON' ( ID | STRING_LITERAL ) ( description )? ( tasktype )? ( 'INTERACTIVE' )? ( successCondition )? ( recoveryOptions )? )
			// TWS_JOBS.g:49:1: jobId= ID ( ( 'DOCOMMAND' command= STRING_LITERAL ) | ( 'SCRIPTNAME' path= STRING_LITERAL ) ) 'STREAMLOGON' ( ID | STRING_LITERAL ) ( description )? ( tasktype )? ( 'INTERACTIVE' )? ( successCondition )? ( recoveryOptions )?
			{
			root_0 = (Object)adaptor.nil();



			retval.job = new Job() ;
			retval.state = new JobChainNode() ;
			retval.job.setOrder("yes") ;

			jobId=(Token)match(input,ID,FOLLOW_ID_in_jobDefinition98); 
			jobId_tree = (Object)adaptor.create(jobId);
			adaptor.addChild(root_0, jobId_tree);


			//a modifier 
			retval.fullId = (jobId!=null?jobId.getText():null) ;
			retval.job.setName( (jobId!=null?jobId.getText():null).split("#")[1]);
			retval.state.setJob((jobId!=null?jobId.getText():null).split("#")[1]) ;
			retval.state.setState((jobId!=null?jobId.getText():null).split("#")[1]) ;  
			//Launcher.logger.info("Parsing of the Job " + (jobId!=null?jobId.getText():null));

			Script script = new Script();
			script.setLanguage("shell") ;

			// TWS_JOBS.g:69:2: ( ( 'DOCOMMAND' command= STRING_LITERAL ) | ( 'SCRIPTNAME' path= STRING_LITERAL ) )
			int alt2=2;
			int LA2_0 = input.LA(1);
			if ( (LA2_0==15) ) {
				alt2=1;
			}
			else if ( (LA2_0==20) ) {
				alt2=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 2, 0, input);
				throw nvae;
			}

			switch (alt2) {
				case 1 :
					// TWS_JOBS.g:71:2: ( 'DOCOMMAND' command= STRING_LITERAL )
					{
					// TWS_JOBS.g:71:2: ( 'DOCOMMAND' command= STRING_LITERAL )
					// TWS_JOBS.g:71:2: 'DOCOMMAND' command= STRING_LITERAL
					{
					string_literal3=(Token)match(input,15,FOLLOW_15_in_jobDefinition111); 
					string_literal3_tree = (Object)adaptor.create(string_literal3);
					adaptor.addChild(root_0, string_literal3_tree);

					command=(Token)match(input,STRING_LITERAL,FOLLOW_STRING_LITERAL_in_jobDefinition115); 
					command_tree = (Object)adaptor.create(command);
					adaptor.addChild(root_0, command_tree);


					if(!test){
					script.getContent().add(Launcher.getString((command!=null?command.getText():null)));
					}else{

					script.getContent().add("sleep 5 #"+Launcher.getString((command!=null?command.getText():null)));
					}

					}

					}
					break;
				case 2 :
					// TWS_JOBS.g:82:2: ( 'SCRIPTNAME' path= STRING_LITERAL )
					{
					// TWS_JOBS.g:82:2: ( 'SCRIPTNAME' path= STRING_LITERAL )
					// TWS_JOBS.g:82:3: 'SCRIPTNAME' path= STRING_LITERAL
					{
					string_literal4=(Token)match(input,20,FOLLOW_20_in_jobDefinition124); 
					string_literal4_tree = (Object)adaptor.create(string_literal4);
					adaptor.addChild(root_0, string_literal4_tree);

					path=(Token)match(input,STRING_LITERAL,FOLLOW_STRING_LITERAL_in_jobDefinition128); 
					path_tree = (Object)adaptor.create(path);
					adaptor.addChild(root_0, path_tree);


					if(!test){
					script.getContent().add(Launcher.getString((path!=null?path.getText():null)));
					}else{
					script.getContent().add("sleep 5  #"+Launcher.getString((path!=null?path.getText():null)));
					}

					}

					}
					break;

			}


			retval.job.setScript(script) ;

			string_literal5=(Token)match(input,22,FOLLOW_22_in_jobDefinition138); 
			string_literal5_tree = (Object)adaptor.create(string_literal5);
			adaptor.addChild(root_0, string_literal5_tree);

			set6=input.LT(1);
			if ( input.LA(1)==ID||input.LA(1)==STRING_LITERAL ) {
				input.consume();
				adaptor.addChild(root_0, (Object)adaptor.create(set6));
				state.errorRecovery=false;
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				throw mse;
			}
			// TWS_JOBS.g:97:2: ( description )?
			int alt3=2;
			int LA3_0 = input.LA(1);
			if ( (LA3_0==14) ) {
				alt3=1;
			}
			switch (alt3) {
				case 1 :
					// TWS_JOBS.g:97:3: description
					{
					pushFollow(FOLLOW_description_in_jobDefinition150);
					description7=description();
					state._fsp--;

					adaptor.addChild(root_0, description7.getTree());

					retval.job.setDescription((description7!=null?((TWS_JOBSParser.description_return)description7).description:null));
					}
					break;

			}

			// TWS_JOBS.g:99:2: ( tasktype )?
			int alt4=2;
			int LA4_0 = input.LA(1);
			if ( (LA4_0==23) ) {
				alt4=1;
			}
			switch (alt4) {
				case 1 :
					// TWS_JOBS.g:99:2: tasktype
					{
					pushFollow(FOLLOW_tasktype_in_jobDefinition159);
					tasktype8=tasktype();
					state._fsp--;

					adaptor.addChild(root_0, tasktype8.getTree());

					}
					break;

			}

			// TWS_JOBS.g:101:2: ( 'INTERACTIVE' )?
			int alt5=2;
			int LA5_0 = input.LA(1);
			if ( (LA5_0==16) ) {
				alt5=1;
			}
			switch (alt5) {
				case 1 :
					// TWS_JOBS.g:101:2: 'INTERACTIVE'
					{
					string_literal9=(Token)match(input,16,FOLLOW_16_in_jobDefinition165); 
					string_literal9_tree = (Object)adaptor.create(string_literal9);
					adaptor.addChild(root_0, string_literal9_tree);

					}
					break;

			}

			// TWS_JOBS.g:103:2: ( successCondition )?
			int alt6=2;
			int LA6_0 = input.LA(1);
			if ( (LA6_0==17) ) {
				alt6=1;
			}
			switch (alt6) {
				case 1 :
					// TWS_JOBS.g:103:2: successCondition
					{
					pushFollow(FOLLOW_successCondition_in_jobDefinition171);
					successCondition10=successCondition();
					state._fsp--;

					adaptor.addChild(root_0, successCondition10.getTree());

					}
					break;

			}

			// TWS_JOBS.g:105:2: ( recoveryOptions )?
			int alt7=2;
			int LA7_0 = input.LA(1);
			if ( (LA7_0==18) ) {
				alt7=1;
			}
			switch (alt7) {
				case 1 :
					// TWS_JOBS.g:105:3: recoveryOptions
					{
					pushFollow(FOLLOW_recoveryOptions_in_jobDefinition179);
					recoveryOptions11=recoveryOptions();
					state._fsp--;

					adaptor.addChild(root_0, recoveryOptions11.getTree());

					(recoveryOptions11!=null?((TWS_JOBSParser.recoveryOptions_return)recoveryOptions11).recovery:null).state= (jobId!=null?jobId.getText():null) ;
					 
					 retval.recovery = (recoveryOptions11!=null?((TWS_JOBSParser.recoveryOptions_return)recoveryOptions11).recovery:null) ;
					 
					}
					break;

			}

			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "jobDefinition"


	public static class recoveryOptions_return extends ParserRuleReturnScope {
		public Recovery recovery;
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "recoveryOptions"
	// TWS_JOBS.g:111:1: recoveryOptions returns [Recovery recovery] : 'RECOVERY' ( ( 'STOP' ) | ( 'RERUN' ) | ( 'CONTINUE' ) ) ( 'AFTER' rec= ID )? ( 'ABENDPROMPT' STRING_LITERAL )? ;
	public final TWS_JOBSParser.recoveryOptions_return recoveryOptions() throws RecognitionException {
		TWS_JOBSParser.recoveryOptions_return retval = new TWS_JOBSParser.recoveryOptions_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token rec=null;
		Token string_literal12=null;
		Token string_literal13=null;
		Token string_literal14=null;
		Token string_literal15=null;
		Token string_literal16=null;
		Token string_literal17=null;
		Token STRING_LITERAL18=null;

		Object rec_tree=null;
		Object string_literal12_tree=null;
		Object string_literal13_tree=null;
		Object string_literal14_tree=null;
		Object string_literal15_tree=null;
		Object string_literal16_tree=null;
		Object string_literal17_tree=null;
		Object STRING_LITERAL18_tree=null;

		try {
			// TWS_JOBS.g:111:44: ( 'RECOVERY' ( ( 'STOP' ) | ( 'RERUN' ) | ( 'CONTINUE' ) ) ( 'AFTER' rec= ID )? ( 'ABENDPROMPT' STRING_LITERAL )? )
			// TWS_JOBS.g:113:2: 'RECOVERY' ( ( 'STOP' ) | ( 'RERUN' ) | ( 'CONTINUE' ) ) ( 'AFTER' rec= ID )? ( 'ABENDPROMPT' STRING_LITERAL )?
			{
			root_0 = (Object)adaptor.nil();



			 retval.recovery = new Recovery() ;
			 
			string_literal12=(Token)match(input,18,FOLLOW_18_in_recoveryOptions198); 
			string_literal12_tree = (Object)adaptor.create(string_literal12);
			adaptor.addChild(root_0, string_literal12_tree);

			// TWS_JOBS.g:117:3: ( ( 'STOP' ) | ( 'RERUN' ) | ( 'CONTINUE' ) )
			int alt8=3;
			switch ( input.LA(1) ) {
			case 21:
				{
				alt8=1;
				}
				break;
			case 19:
				{
				alt8=2;
				}
				break;
			case 13:
				{
				alt8=3;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 8, 0, input);
				throw nvae;
			}
			switch (alt8) {
				case 1 :
					// TWS_JOBS.g:117:4: ( 'STOP' )
					{
					// TWS_JOBS.g:117:4: ( 'STOP' )
					// TWS_JOBS.g:117:5: 'STOP'
					{
					string_literal13=(Token)match(input,21,FOLLOW_21_in_recoveryOptions204); 
					string_literal13_tree = (Object)adaptor.create(string_literal13);
					adaptor.addChild(root_0, string_literal13_tree);

					retval.recovery.option = "stop" ;
					}

					}
					break;
				case 2 :
					// TWS_JOBS.g:119:4: ( 'RERUN' )
					{
					// TWS_JOBS.g:119:4: ( 'RERUN' )
					// TWS_JOBS.g:119:5: 'RERUN'
					{
					string_literal14=(Token)match(input,19,FOLLOW_19_in_recoveryOptions216); 
					string_literal14_tree = (Object)adaptor.create(string_literal14);
					adaptor.addChild(root_0, string_literal14_tree);

					retval.recovery.option = "rerun";
					}

					}
					break;
				case 3 :
					// TWS_JOBS.g:120:4: ( 'CONTINUE' )
					{
					// TWS_JOBS.g:120:4: ( 'CONTINUE' )
					// TWS_JOBS.g:120:5: 'CONTINUE'
					{
					string_literal15=(Token)match(input,13,FOLLOW_13_in_recoveryOptions225); 
					string_literal15_tree = (Object)adaptor.create(string_literal15);
					adaptor.addChild(root_0, string_literal15_tree);

					retval.recovery.option = "continue";
					}

					}
					break;

			}

			// TWS_JOBS.g:122:3: ( 'AFTER' rec= ID )?
			int alt9=2;
			int LA9_0 = input.LA(1);
			if ( (LA9_0==12) ) {
				alt9=1;
			}
			switch (alt9) {
				case 1 :
					// TWS_JOBS.g:122:4: 'AFTER' rec= ID
					{
					string_literal16=(Token)match(input,12,FOLLOW_12_in_recoveryOptions237); 
					string_literal16_tree = (Object)adaptor.create(string_literal16);
					adaptor.addChild(root_0, string_literal16_tree);

					rec=(Token)match(input,ID,FOLLOW_ID_in_recoveryOptions241); 
					rec_tree = (Object)adaptor.create(rec);
					adaptor.addChild(root_0, rec_tree);

					retval.recovery.after = (rec!=null?rec.getText():null) ;
					}
					break;

			}


			  retval.recovery.setRecoveryType() ;
			  
			// TWS_JOBS.g:128:3: ( 'ABENDPROMPT' STRING_LITERAL )?
			int alt10=2;
			int LA10_0 = input.LA(1);
			if ( (LA10_0==11) ) {
				alt10=1;
			}
			switch (alt10) {
				case 1 :
					// TWS_JOBS.g:128:4: 'ABENDPROMPT' STRING_LITERAL
					{
					string_literal17=(Token)match(input,11,FOLLOW_11_in_recoveryOptions262); 
					string_literal17_tree = (Object)adaptor.create(string_literal17);
					adaptor.addChild(root_0, string_literal17_tree);

					STRING_LITERAL18=(Token)match(input,STRING_LITERAL,FOLLOW_STRING_LITERAL_in_recoveryOptions264); 
					STRING_LITERAL18_tree = (Object)adaptor.create(STRING_LITERAL18);
					adaptor.addChild(root_0, STRING_LITERAL18_tree);

					}
					break;

			}

			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "recoveryOptions"


	public static class successCondition_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "successCondition"
	// TWS_JOBS.g:132:1: successCondition : 'RCCONDSUCC' STRING_LITERAL ;
	public final TWS_JOBSParser.successCondition_return successCondition() throws RecognitionException {
		TWS_JOBSParser.successCondition_return retval = new TWS_JOBSParser.successCondition_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token string_literal19=null;
		Token STRING_LITERAL20=null;

		Object string_literal19_tree=null;
		Object STRING_LITERAL20_tree=null;

		try {
			// TWS_JOBS.g:132:18: ( 'RCCONDSUCC' STRING_LITERAL )
			// TWS_JOBS.g:134:1: 'RCCONDSUCC' STRING_LITERAL
			{
			root_0 = (Object)adaptor.nil();


			string_literal19=(Token)match(input,17,FOLLOW_17_in_successCondition277); 
			string_literal19_tree = (Object)adaptor.create(string_literal19);
			adaptor.addChild(root_0, string_literal19_tree);

			STRING_LITERAL20=(Token)match(input,STRING_LITERAL,FOLLOW_STRING_LITERAL_in_successCondition279); 
			STRING_LITERAL20_tree = (Object)adaptor.create(STRING_LITERAL20);
			adaptor.addChild(root_0, STRING_LITERAL20_tree);

			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "successCondition"


	public static class tasktype_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "tasktype"
	// TWS_JOBS.g:140:1: tasktype : 'TASKTYPE' ID ;
	public final TWS_JOBSParser.tasktype_return tasktype() throws RecognitionException {
		TWS_JOBSParser.tasktype_return retval = new TWS_JOBSParser.tasktype_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token string_literal21=null;
		Token ID22=null;

		Object string_literal21_tree=null;
		Object ID22_tree=null;

		try {
			// TWS_JOBS.g:140:10: ( 'TASKTYPE' ID )
			// TWS_JOBS.g:141:1: 'TASKTYPE' ID
			{
			root_0 = (Object)adaptor.nil();


			string_literal21=(Token)match(input,23,FOLLOW_23_in_tasktype291); 
			string_literal21_tree = (Object)adaptor.create(string_literal21);
			adaptor.addChild(root_0, string_literal21_tree);

			ID22=(Token)match(input,ID,FOLLOW_ID_in_tasktype293); 
			ID22_tree = (Object)adaptor.create(ID22);
			adaptor.addChild(root_0, ID22_tree);

			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "tasktype"


	public static class description_return extends ParserRuleReturnScope {
		public Description description;
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "description"
	// TWS_JOBS.g:144:1: description returns [Description description] : 'DESCRIPTION' desc= STRING_LITERAL ;
	public final TWS_JOBSParser.description_return description() throws RecognitionException {
		TWS_JOBSParser.description_return retval = new TWS_JOBSParser.description_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token desc=null;
		Token string_literal23=null;

		Object desc_tree=null;
		Object string_literal23_tree=null;

		try {
			// TWS_JOBS.g:144:47: ( 'DESCRIPTION' desc= STRING_LITERAL )
			// TWS_JOBS.g:145:1: 'DESCRIPTION' desc= STRING_LITERAL
			{
			root_0 = (Object)adaptor.nil();


			string_literal23=(Token)match(input,14,FOLLOW_14_in_description307); 
			string_literal23_tree = (Object)adaptor.create(string_literal23);
			adaptor.addChild(root_0, string_literal23_tree);

			desc=(Token)match(input,STRING_LITERAL,FOLLOW_STRING_LITERAL_in_description311); 
			desc_tree = (Object)adaptor.create(desc);
			adaptor.addChild(root_0, desc_tree);


			retval.description = new Description() ;
			retval.description.getContent().add(Launcher.getString((desc!=null?desc.getText():null))) ;


			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "description"

	// Delegated rules



	public static final BitSet FOLLOW_10_in_jobs63 = new BitSet(new long[]{0x0000000000000042L});
	public static final BitSet FOLLOW_jobDefinition_in_jobs67 = new BitSet(new long[]{0x0000000000000042L});
	public static final BitSet FOLLOW_ID_in_jobDefinition98 = new BitSet(new long[]{0x0000000000108000L});
	public static final BitSet FOLLOW_15_in_jobDefinition111 = new BitSet(new long[]{0x0000000000000100L});
	public static final BitSet FOLLOW_STRING_LITERAL_in_jobDefinition115 = new BitSet(new long[]{0x0000000000400000L});
	public static final BitSet FOLLOW_20_in_jobDefinition124 = new BitSet(new long[]{0x0000000000000100L});
	public static final BitSet FOLLOW_STRING_LITERAL_in_jobDefinition128 = new BitSet(new long[]{0x0000000000400000L});
	public static final BitSet FOLLOW_22_in_jobDefinition138 = new BitSet(new long[]{0x0000000000000140L});
	public static final BitSet FOLLOW_set_in_jobDefinition140 = new BitSet(new long[]{0x0000000000874002L});
	public static final BitSet FOLLOW_description_in_jobDefinition150 = new BitSet(new long[]{0x0000000000870002L});
	public static final BitSet FOLLOW_tasktype_in_jobDefinition159 = new BitSet(new long[]{0x0000000000070002L});
	public static final BitSet FOLLOW_16_in_jobDefinition165 = new BitSet(new long[]{0x0000000000060002L});
	public static final BitSet FOLLOW_successCondition_in_jobDefinition171 = new BitSet(new long[]{0x0000000000040002L});
	public static final BitSet FOLLOW_recoveryOptions_in_jobDefinition179 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_18_in_recoveryOptions198 = new BitSet(new long[]{0x0000000000282000L});
	public static final BitSet FOLLOW_21_in_recoveryOptions204 = new BitSet(new long[]{0x0000000000001802L});
	public static final BitSet FOLLOW_19_in_recoveryOptions216 = new BitSet(new long[]{0x0000000000001802L});
	public static final BitSet FOLLOW_13_in_recoveryOptions225 = new BitSet(new long[]{0x0000000000001802L});
	public static final BitSet FOLLOW_12_in_recoveryOptions237 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_ID_in_recoveryOptions241 = new BitSet(new long[]{0x0000000000000802L});
	public static final BitSet FOLLOW_11_in_recoveryOptions262 = new BitSet(new long[]{0x0000000000000100L});
	public static final BitSet FOLLOW_STRING_LITERAL_in_recoveryOptions264 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_17_in_successCondition277 = new BitSet(new long[]{0x0000000000000100L});
	public static final BitSet FOLLOW_STRING_LITERAL_in_successCondition279 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_23_in_tasktype291 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_ID_in_tasktype293 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_14_in_description307 = new BitSet(new long[]{0x0000000000000100L});
	public static final BitSet FOLLOW_STRING_LITERAL_in_description311 = new BitSet(new long[]{0x0000000000000002L});
}
