// $ANTLR 3.5.2 TWS_VARIABLES.g 2015-05-20 10:20:17

package org.jobscheduler.converter.tws.variables ;
import  org.jobscheduler.converter.tws.scheduler.*;
import java.util.LinkedList ;
import java.util.HashMap ;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class TWS_VARIABLESParser extends Parser {
	public static final String[] tokenNames = new String[] {
		"<invalid>", "<EOR>", "<DOWN>", "<UP>", "DATE_LITERAL", "ID", "STRING_LITERAL", 
		"WS", "'DESCRIPTION'", "'END'", "'ISDEFAULT'", "'MEMBERS'", "'VARTABLE'"
	};
	public static final int EOF=-1;
	public static final int T__8=8;
	public static final int T__9=9;
	public static final int T__10=10;
	public static final int T__11=11;
	public static final int T__12=12;
	public static final int DATE_LITERAL=4;
	public static final int ID=5;
	public static final int STRING_LITERAL=6;
	public static final int WS=7;

	// delegates
	public Parser[] getDelegates() {
		return new Parser[] {};
	}

	// delegators


	public TWS_VARIABLESParser(TokenStream input) {
		this(input, new RecognizerSharedState());
	}
	public TWS_VARIABLESParser(TokenStream input, RecognizerSharedState state) {
		super(input, state);
	}

	@Override public String[] getTokenNames() { return TWS_VARIABLESParser.tokenNames; }
	@Override public String getGrammarFileName() { return "TWS_VARIABLES.g"; }



	// $ANTLR start "variables"
	// TWS_VARIABLES.g:20:1: variables returns [HashMap<String,Params> variables] : ( variable )+ ;
	public final HashMap<String,Params> variables() throws RecognitionException {
		HashMap<String,Params> variables = null;


		ParserRuleReturnScope variable1 =null;

		try {
			// TWS_VARIABLES.g:20:53: ( ( variable )+ )
			// TWS_VARIABLES.g:21:3: ( variable )+
			{
			variables = new HashMap<String,Params>() ;
			// TWS_VARIABLES.g:22:2: ( variable )+
			int cnt1=0;
			loop1:
			while (true) {
				int alt1=2;
				int LA1_0 = input.LA(1);
				if ( (LA1_0==12) ) {
					alt1=1;
				}

				switch (alt1) {
				case 1 :
					// TWS_VARIABLES.g:22:3: variable
					{
					pushFollow(FOLLOW_variable_in_variables50);
					variable1=variable();
					state._fsp--;

					variables.put((variable1!=null?((TWS_VARIABLESParser.variable_return)variable1).key:null),(variable1!=null?((TWS_VARIABLESParser.variable_return)variable1).params:null)) ;
					}
					break;

				default :
					if ( cnt1 >= 1 ) break loop1;
					EarlyExitException eee = new EarlyExitException(1, input);
					throw eee;
				}
				cnt1++;
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return variables;
	}
	// $ANTLR end "variables"


	public static class variable_return extends ParserRuleReturnScope {
		public Params params;
		public String key;
	};


	// $ANTLR start "variable"
	// TWS_VARIABLES.g:29:1: variable returns [Params params,String key] : 'VARTABLE' id= ID ( 'DESCRIPTION' STRING_LITERAL )? ( 'ISDEFAULT' )? 'MEMBERS' ( varDefinition )* 'END' ;
	public final TWS_VARIABLESParser.variable_return variable() throws RecognitionException {
		TWS_VARIABLESParser.variable_return retval = new TWS_VARIABLESParser.variable_return();
		retval.start = input.LT(1);

		Token id=null;
		Param varDefinition2 =null;

		try {
			// TWS_VARIABLES.g:29:44: ( 'VARTABLE' id= ID ( 'DESCRIPTION' STRING_LITERAL )? ( 'ISDEFAULT' )? 'MEMBERS' ( varDefinition )* 'END' )
			// TWS_VARIABLES.g:31:1: 'VARTABLE' id= ID ( 'DESCRIPTION' STRING_LITERAL )? ( 'ISDEFAULT' )? 'MEMBERS' ( varDefinition )* 'END'
			{
			retval.params = new Params() ;
			match(input,12,FOLLOW_12_in_variable77); 
			id=(Token)match(input,ID,FOLLOW_ID_in_variable81); 
			retval.key =(id!=null?id.getText():null) ;
			// TWS_VARIABLES.g:33:2: ( 'DESCRIPTION' STRING_LITERAL )?
			int alt2=2;
			int LA2_0 = input.LA(1);
			if ( (LA2_0==8) ) {
				alt2=1;
			}
			switch (alt2) {
				case 1 :
					// TWS_VARIABLES.g:33:2: 'DESCRIPTION' STRING_LITERAL
					{
					match(input,8,FOLLOW_8_in_variable86); 
					match(input,STRING_LITERAL,FOLLOW_STRING_LITERAL_in_variable88); 
					}
					break;

			}

			// TWS_VARIABLES.g:34:1: ( 'ISDEFAULT' )?
			int alt3=2;
			int LA3_0 = input.LA(1);
			if ( (LA3_0==10) ) {
				alt3=1;
			}
			switch (alt3) {
				case 1 :
					// TWS_VARIABLES.g:34:1: 'ISDEFAULT'
					{
					match(input,10,FOLLOW_10_in_variable92); 
					}
					break;

			}

			match(input,11,FOLLOW_11_in_variable95); 
			// TWS_VARIABLES.g:36:2: ( varDefinition )*
			loop4:
			while (true) {
				int alt4=2;
				int LA4_0 = input.LA(1);
				if ( (LA4_0==ID) ) {
					alt4=1;
				}

				switch (alt4) {
				case 1 :
					// TWS_VARIABLES.g:36:2: varDefinition
					{
					pushFollow(FOLLOW_varDefinition_in_variable99);
					varDefinition2=varDefinition();
					state._fsp--;

					retval.params.getParamOrCopyParamsOrInclude().add(varDefinition2) ;
					}
					break;

				default :
					break loop4;
				}
			}

			match(input,9,FOLLOW_9_in_variable105); 
			}

			retval.stop = input.LT(-1);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "variable"



	// $ANTLR start "varDefinition"
	// TWS_VARIABLES.g:40:1: varDefinition returns [Param param] :id= ID val= STRING_LITERAL ;
	public final Param varDefinition() throws RecognitionException {
		Param param = null;


		Token id=null;
		Token val=null;

		try {
			// TWS_VARIABLES.g:40:37: (id= ID val= STRING_LITERAL )
			// TWS_VARIABLES.g:41:1: id= ID val= STRING_LITERAL
			{
			param = new Param() ;
			id=(Token)match(input,ID,FOLLOW_ID_in_varDefinition124); 
			val=(Token)match(input,STRING_LITERAL,FOLLOW_STRING_LITERAL_in_varDefinition128); 
			param.setName((id!=null?id.getText():null)) ;
			param.setValue((val!=null?val.getText():null)) ;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return param;
	}
	// $ANTLR end "varDefinition"

	// Delegated rules



	public static final BitSet FOLLOW_variable_in_variables50 = new BitSet(new long[]{0x0000000000001002L});
	public static final BitSet FOLLOW_12_in_variable77 = new BitSet(new long[]{0x0000000000000020L});
	public static final BitSet FOLLOW_ID_in_variable81 = new BitSet(new long[]{0x0000000000000D00L});
	public static final BitSet FOLLOW_8_in_variable86 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_STRING_LITERAL_in_variable88 = new BitSet(new long[]{0x0000000000000C00L});
	public static final BitSet FOLLOW_10_in_variable92 = new BitSet(new long[]{0x0000000000000800L});
	public static final BitSet FOLLOW_11_in_variable95 = new BitSet(new long[]{0x0000000000000220L});
	public static final BitSet FOLLOW_varDefinition_in_variable99 = new BitSet(new long[]{0x0000000000000220L});
	public static final BitSet FOLLOW_9_in_variable105 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_varDefinition124 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_STRING_LITERAL_in_varDefinition128 = new BitSet(new long[]{0x0000000000000002L});
}
