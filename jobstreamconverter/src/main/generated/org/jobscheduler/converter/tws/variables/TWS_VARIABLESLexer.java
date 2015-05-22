// $ANTLR 3.5.2 TWS_VARIABLES.g 2015-05-20 10:20:17

package org.jobscheduler.converter.tws.variables ;




import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class TWS_VARIABLESLexer extends Lexer {
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
	// delegators
	public Lexer[] getDelegates() {
		return new Lexer[] {};
	}

	public TWS_VARIABLESLexer() {} 
	public TWS_VARIABLESLexer(CharStream input) {
		this(input, new RecognizerSharedState());
	}
	public TWS_VARIABLESLexer(CharStream input, RecognizerSharedState state) {
		super(input,state);
	}
	@Override public String getGrammarFileName() { return "TWS_VARIABLES.g"; }

	// $ANTLR start "T__8"
	public final void mT__8() throws RecognitionException {
		try {
			int _type = T__8;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// TWS_VARIABLES.g:13:6: ( 'DESCRIPTION' )
			// TWS_VARIABLES.g:13:8: 'DESCRIPTION'
			{
			match("DESCRIPTION"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__8"

	// $ANTLR start "T__9"
	public final void mT__9() throws RecognitionException {
		try {
			int _type = T__9;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// TWS_VARIABLES.g:14:6: ( 'END' )
			// TWS_VARIABLES.g:14:8: 'END'
			{
			match("END"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__9"

	// $ANTLR start "T__10"
	public final void mT__10() throws RecognitionException {
		try {
			int _type = T__10;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// TWS_VARIABLES.g:15:7: ( 'ISDEFAULT' )
			// TWS_VARIABLES.g:15:9: 'ISDEFAULT'
			{
			match("ISDEFAULT"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__10"

	// $ANTLR start "T__11"
	public final void mT__11() throws RecognitionException {
		try {
			int _type = T__11;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// TWS_VARIABLES.g:16:7: ( 'MEMBERS' )
			// TWS_VARIABLES.g:16:9: 'MEMBERS'
			{
			match("MEMBERS"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__11"

	// $ANTLR start "T__12"
	public final void mT__12() throws RecognitionException {
		try {
			int _type = T__12;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// TWS_VARIABLES.g:17:7: ( 'VARTABLE' )
			// TWS_VARIABLES.g:17:9: 'VARTABLE'
			{
			match("VARTABLE"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__12"

	// $ANTLR start "STRING_LITERAL"
	public final void mSTRING_LITERAL() throws RecognitionException {
		try {
			int _type = STRING_LITERAL;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			int c;

			// TWS_VARIABLES.g:51:3: ( '\"' (c=~ ( '\"' | '\\r' | '\\n' ) )* '\"' )
			// TWS_VARIABLES.g:51:5: '\"' (c=~ ( '\"' | '\\r' | '\\n' ) )* '\"'
			{
			match('\"'); 
			 StringBuilder b = new StringBuilder(); 
			// TWS_VARIABLES.g:53:5: (c=~ ( '\"' | '\\r' | '\\n' ) )*
			loop1:
			while (true) {
				int alt1=2;
				int LA1_0 = input.LA(1);
				if ( ((LA1_0 >= '\u0000' && LA1_0 <= '\t')||(LA1_0 >= '\u000B' && LA1_0 <= '\f')||(LA1_0 >= '\u000E' && LA1_0 <= '!')||(LA1_0 >= '#' && LA1_0 <= '\uFFFF')) ) {
					alt1=1;
				}

				switch (alt1) {
				case 1 :
					// TWS_VARIABLES.g:53:7: c=~ ( '\"' | '\\r' | '\\n' )
					{
					c= input.LA(1);
					if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '\t')||(input.LA(1) >= '\u000B' && input.LA(1) <= '\f')||(input.LA(1) >= '\u000E' && input.LA(1) <= '!')||(input.LA(1) >= '#' && input.LA(1) <= '\uFFFF') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					 b.appendCodePoint(c);
					}
					break;

				default :
					break loop1;
				}
			}

			match('\"'); 
			 setText(b.toString()); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "STRING_LITERAL"

	// $ANTLR start "DATE_LITERAL"
	public final void mDATE_LITERAL() throws RecognitionException {
		try {
			int _type = DATE_LITERAL;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// TWS_VARIABLES.g:58:14: ( ( '0' .. '9' ) ( '0' .. '9' ) '/' ( '0' .. '9' ) ( '0' .. '9' ) '/' ( '0' .. '9' ) ( '0' .. '9' ) ( '0' .. '9' ) ( '0' .. '9' ) )
			// TWS_VARIABLES.g:58:16: ( '0' .. '9' ) ( '0' .. '9' ) '/' ( '0' .. '9' ) ( '0' .. '9' ) '/' ( '0' .. '9' ) ( '0' .. '9' ) ( '0' .. '9' ) ( '0' .. '9' )
			{
			if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			match('/'); 
			if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			match('/'); 
			if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "DATE_LITERAL"

	// $ANTLR start "ID"
	public final void mID() throws RecognitionException {
		try {
			int _type = ID;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// TWS_VARIABLES.g:61:4: ( ( 'A' .. 'Z' | 'a' .. 'z' | '0' .. '9' | '-' | '_' | '^' )+ )
			// TWS_VARIABLES.g:61:6: ( 'A' .. 'Z' | 'a' .. 'z' | '0' .. '9' | '-' | '_' | '^' )+
			{
			// TWS_VARIABLES.g:61:6: ( 'A' .. 'Z' | 'a' .. 'z' | '0' .. '9' | '-' | '_' | '^' )+
			int cnt2=0;
			loop2:
			while (true) {
				int alt2=2;
				int LA2_0 = input.LA(1);
				if ( (LA2_0=='-'||(LA2_0 >= '0' && LA2_0 <= '9')||(LA2_0 >= 'A' && LA2_0 <= 'Z')||(LA2_0 >= '^' && LA2_0 <= '_')||(LA2_0 >= 'a' && LA2_0 <= 'z')) ) {
					alt2=1;
				}

				switch (alt2) {
				case 1 :
					// TWS_VARIABLES.g:
					{
					if ( input.LA(1)=='-'||(input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||(input.LA(1) >= '^' && input.LA(1) <= '_')||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					if ( cnt2 >= 1 ) break loop2;
					EarlyExitException eee = new EarlyExitException(2, input);
					throw eee;
				}
				cnt2++;
			}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "ID"

	// $ANTLR start "WS"
	public final void mWS() throws RecognitionException {
		try {
			int _type = WS;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// TWS_VARIABLES.g:62:4: ( ( ' ' | '\\t' | '\\r' | '\\n' )+ )
			// TWS_VARIABLES.g:62:6: ( ' ' | '\\t' | '\\r' | '\\n' )+
			{
			// TWS_VARIABLES.g:62:6: ( ' ' | '\\t' | '\\r' | '\\n' )+
			int cnt3=0;
			loop3:
			while (true) {
				int alt3=2;
				int LA3_0 = input.LA(1);
				if ( ((LA3_0 >= '\t' && LA3_0 <= '\n')||LA3_0=='\r'||LA3_0==' ') ) {
					alt3=1;
				}

				switch (alt3) {
				case 1 :
					// TWS_VARIABLES.g:
					{
					if ( (input.LA(1) >= '\t' && input.LA(1) <= '\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					if ( cnt3 >= 1 ) break loop3;
					EarlyExitException eee = new EarlyExitException(3, input);
					throw eee;
				}
				cnt3++;
			}

			 _channel = HIDDEN; 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "WS"

	@Override
	public void mTokens() throws RecognitionException {
		// TWS_VARIABLES.g:1:8: ( T__8 | T__9 | T__10 | T__11 | T__12 | STRING_LITERAL | DATE_LITERAL | ID | WS )
		int alt4=9;
		switch ( input.LA(1) ) {
		case 'D':
			{
			int LA4_1 = input.LA(2);
			if ( (LA4_1=='E') ) {
				int LA4_10 = input.LA(3);
				if ( (LA4_10=='S') ) {
					int LA4_16 = input.LA(4);
					if ( (LA4_16=='C') ) {
						int LA4_22 = input.LA(5);
						if ( (LA4_22=='R') ) {
							int LA4_27 = input.LA(6);
							if ( (LA4_27=='I') ) {
								int LA4_31 = input.LA(7);
								if ( (LA4_31=='P') ) {
									int LA4_35 = input.LA(8);
									if ( (LA4_35=='T') ) {
										int LA4_39 = input.LA(9);
										if ( (LA4_39=='I') ) {
											int LA4_43 = input.LA(10);
											if ( (LA4_43=='O') ) {
												int LA4_46 = input.LA(11);
												if ( (LA4_46=='N') ) {
													int LA4_48 = input.LA(12);
													if ( (LA4_48=='-'||(LA4_48 >= '0' && LA4_48 <= '9')||(LA4_48 >= 'A' && LA4_48 <= 'Z')||(LA4_48 >= '^' && LA4_48 <= '_')||(LA4_48 >= 'a' && LA4_48 <= 'z')) ) {
														alt4=8;
													}

													else {
														alt4=1;
													}

												}

												else {
													alt4=8;
												}

											}

											else {
												alt4=8;
											}

										}

										else {
											alt4=8;
										}

									}

									else {
										alt4=8;
									}

								}

								else {
									alt4=8;
								}

							}

							else {
								alt4=8;
							}

						}

						else {
							alt4=8;
						}

					}

					else {
						alt4=8;
					}

				}

				else {
					alt4=8;
				}

			}

			else {
				alt4=8;
			}

			}
			break;
		case 'E':
			{
			int LA4_2 = input.LA(2);
			if ( (LA4_2=='N') ) {
				int LA4_11 = input.LA(3);
				if ( (LA4_11=='D') ) {
					int LA4_17 = input.LA(4);
					if ( (LA4_17=='-'||(LA4_17 >= '0' && LA4_17 <= '9')||(LA4_17 >= 'A' && LA4_17 <= 'Z')||(LA4_17 >= '^' && LA4_17 <= '_')||(LA4_17 >= 'a' && LA4_17 <= 'z')) ) {
						alt4=8;
					}

					else {
						alt4=2;
					}

				}

				else {
					alt4=8;
				}

			}

			else {
				alt4=8;
			}

			}
			break;
		case 'I':
			{
			int LA4_3 = input.LA(2);
			if ( (LA4_3=='S') ) {
				int LA4_12 = input.LA(3);
				if ( (LA4_12=='D') ) {
					int LA4_18 = input.LA(4);
					if ( (LA4_18=='E') ) {
						int LA4_24 = input.LA(5);
						if ( (LA4_24=='F') ) {
							int LA4_28 = input.LA(6);
							if ( (LA4_28=='A') ) {
								int LA4_32 = input.LA(7);
								if ( (LA4_32=='U') ) {
									int LA4_36 = input.LA(8);
									if ( (LA4_36=='L') ) {
										int LA4_40 = input.LA(9);
										if ( (LA4_40=='T') ) {
											int LA4_44 = input.LA(10);
											if ( (LA4_44=='-'||(LA4_44 >= '0' && LA4_44 <= '9')||(LA4_44 >= 'A' && LA4_44 <= 'Z')||(LA4_44 >= '^' && LA4_44 <= '_')||(LA4_44 >= 'a' && LA4_44 <= 'z')) ) {
												alt4=8;
											}

											else {
												alt4=3;
											}

										}

										else {
											alt4=8;
										}

									}

									else {
										alt4=8;
									}

								}

								else {
									alt4=8;
								}

							}

							else {
								alt4=8;
							}

						}

						else {
							alt4=8;
						}

					}

					else {
						alt4=8;
					}

				}

				else {
					alt4=8;
				}

			}

			else {
				alt4=8;
			}

			}
			break;
		case 'M':
			{
			int LA4_4 = input.LA(2);
			if ( (LA4_4=='E') ) {
				int LA4_13 = input.LA(3);
				if ( (LA4_13=='M') ) {
					int LA4_19 = input.LA(4);
					if ( (LA4_19=='B') ) {
						int LA4_25 = input.LA(5);
						if ( (LA4_25=='E') ) {
							int LA4_29 = input.LA(6);
							if ( (LA4_29=='R') ) {
								int LA4_33 = input.LA(7);
								if ( (LA4_33=='S') ) {
									int LA4_37 = input.LA(8);
									if ( (LA4_37=='-'||(LA4_37 >= '0' && LA4_37 <= '9')||(LA4_37 >= 'A' && LA4_37 <= 'Z')||(LA4_37 >= '^' && LA4_37 <= '_')||(LA4_37 >= 'a' && LA4_37 <= 'z')) ) {
										alt4=8;
									}

									else {
										alt4=4;
									}

								}

								else {
									alt4=8;
								}

							}

							else {
								alt4=8;
							}

						}

						else {
							alt4=8;
						}

					}

					else {
						alt4=8;
					}

				}

				else {
					alt4=8;
				}

			}

			else {
				alt4=8;
			}

			}
			break;
		case 'V':
			{
			int LA4_5 = input.LA(2);
			if ( (LA4_5=='A') ) {
				int LA4_14 = input.LA(3);
				if ( (LA4_14=='R') ) {
					int LA4_20 = input.LA(4);
					if ( (LA4_20=='T') ) {
						int LA4_26 = input.LA(5);
						if ( (LA4_26=='A') ) {
							int LA4_30 = input.LA(6);
							if ( (LA4_30=='B') ) {
								int LA4_34 = input.LA(7);
								if ( (LA4_34=='L') ) {
									int LA4_38 = input.LA(8);
									if ( (LA4_38=='E') ) {
										int LA4_42 = input.LA(9);
										if ( (LA4_42=='-'||(LA4_42 >= '0' && LA4_42 <= '9')||(LA4_42 >= 'A' && LA4_42 <= 'Z')||(LA4_42 >= '^' && LA4_42 <= '_')||(LA4_42 >= 'a' && LA4_42 <= 'z')) ) {
											alt4=8;
										}

										else {
											alt4=5;
										}

									}

									else {
										alt4=8;
									}

								}

								else {
									alt4=8;
								}

							}

							else {
								alt4=8;
							}

						}

						else {
							alt4=8;
						}

					}

					else {
						alt4=8;
					}

				}

				else {
					alt4=8;
				}

			}

			else {
				alt4=8;
			}

			}
			break;
		case '\"':
			{
			alt4=6;
			}
			break;
		case '0':
		case '1':
		case '2':
		case '3':
		case '4':
		case '5':
		case '6':
		case '7':
		case '8':
		case '9':
			{
			int LA4_7 = input.LA(2);
			if ( ((LA4_7 >= '0' && LA4_7 <= '9')) ) {
				int LA4_15 = input.LA(3);
				if ( (LA4_15=='/') ) {
					alt4=7;
				}

				else {
					alt4=8;
				}

			}

			else {
				alt4=8;
			}

			}
			break;
		case '-':
		case 'A':
		case 'B':
		case 'C':
		case 'F':
		case 'G':
		case 'H':
		case 'J':
		case 'K':
		case 'L':
		case 'N':
		case 'O':
		case 'P':
		case 'Q':
		case 'R':
		case 'S':
		case 'T':
		case 'U':
		case 'W':
		case 'X':
		case 'Y':
		case 'Z':
		case '^':
		case '_':
		case 'a':
		case 'b':
		case 'c':
		case 'd':
		case 'e':
		case 'f':
		case 'g':
		case 'h':
		case 'i':
		case 'j':
		case 'k':
		case 'l':
		case 'm':
		case 'n':
		case 'o':
		case 'p':
		case 'q':
		case 'r':
		case 's':
		case 't':
		case 'u':
		case 'v':
		case 'w':
		case 'x':
		case 'y':
		case 'z':
			{
			alt4=8;
			}
			break;
		case '\t':
		case '\n':
		case '\r':
		case ' ':
			{
			alt4=9;
			}
			break;
		default:
			NoViableAltException nvae =
				new NoViableAltException("", 4, 0, input);
			throw nvae;
		}
		switch (alt4) {
			case 1 :
				// TWS_VARIABLES.g:1:10: T__8
				{
				mT__8(); 

				}
				break;
			case 2 :
				// TWS_VARIABLES.g:1:15: T__9
				{
				mT__9(); 

				}
				break;
			case 3 :
				// TWS_VARIABLES.g:1:20: T__10
				{
				mT__10(); 

				}
				break;
			case 4 :
				// TWS_VARIABLES.g:1:26: T__11
				{
				mT__11(); 

				}
				break;
			case 5 :
				// TWS_VARIABLES.g:1:32: T__12
				{
				mT__12(); 

				}
				break;
			case 6 :
				// TWS_VARIABLES.g:1:38: STRING_LITERAL
				{
				mSTRING_LITERAL(); 

				}
				break;
			case 7 :
				// TWS_VARIABLES.g:1:53: DATE_LITERAL
				{
				mDATE_LITERAL(); 

				}
				break;
			case 8 :
				// TWS_VARIABLES.g:1:66: ID
				{
				mID(); 

				}
				break;
			case 9 :
				// TWS_VARIABLES.g:1:69: WS
				{
				mWS(); 

				}
				break;

		}
	}



}
