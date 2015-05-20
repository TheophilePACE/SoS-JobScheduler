// $ANTLR 3.5.2 /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g 2015-04-20 06:29:12
 
package org.jobscheduler.converter.tws.icalendar ; 


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class TWS_ICALENDARLexer extends Lexer {
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
	public static final int T__24=24;
	public static final int T__25=25;
	public static final int T__26=26;
	public static final int T__27=27;
	public static final int T__28=28;
	public static final int T__29=29;
	public static final int T__30=30;
	public static final int T__31=31;
	public static final int COMMENT=4;
	public static final int DATE_LITERAL=5;
	public static final int DAYS_NUMBER=6;
	public static final int ID=7;
	public static final int NUMBER=8;
	public static final int STRING_LITERAL=9;

	// delegates
	// delegators
	public Lexer[] getDelegates() {
		return new Lexer[] {};
	}

	public TWS_ICALENDARLexer() {} 
	public TWS_ICALENDARLexer(CharStream input) {
		this(input, new RecognizerSharedState());
	}
	public TWS_ICALENDARLexer(CharStream input, RecognizerSharedState state) {
		super(input,state);
	}
	@Override public String getGrammarFileName() { return "/home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g"; }

	// $ANTLR start "T__10"
	public final void mT__10() throws RecognitionException {
		try {
			int _type = T__10;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:11:7: ( '+' )
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:11:9: '+'
			{
			match('+'); 
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
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:12:7: ( ',' )
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:12:9: ','
			{
			match(','); 
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
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:13:7: ( '-' )
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:13:9: '-'
			{
			match('-'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__12"

	// $ANTLR start "T__13"
	public final void mT__13() throws RecognitionException {
		try {
			int _type = T__13;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:14:7: ( ';' )
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:14:9: ';'
			{
			match(';'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__13"

	// $ANTLR start "T__14"
	public final void mT__14() throws RecognitionException {
		try {
			int _type = T__14;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:15:7: ( '=' )
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:15:9: '='
			{
			match('='); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__14"

	// $ANTLR start "T__15"
	public final void mT__15() throws RecognitionException {
		try {
			int _type = T__15;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:16:7: ( 'BYDAY' )
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:16:9: 'BYDAY'
			{
			match("BYDAY"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__15"

	// $ANTLR start "T__16"
	public final void mT__16() throws RecognitionException {
		try {
			int _type = T__16;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:17:7: ( 'BYFREEDAY' )
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:17:9: 'BYFREEDAY'
			{
			match("BYFREEDAY"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__16"

	// $ANTLR start "T__17"
	public final void mT__17() throws RecognitionException {
		try {
			int _type = T__17;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:18:7: ( 'BYMONTHDAY' )
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:18:9: 'BYMONTHDAY'
			{
			match("BYMONTHDAY"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__17"

	// $ANTLR start "T__18"
	public final void mT__18() throws RecognitionException {
		try {
			int _type = T__18;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:19:7: ( 'BYWORKDAY' )
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:19:9: 'BYWORKDAY'
			{
			match("BYWORKDAY"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__18"

	// $ANTLR start "T__19"
	public final void mT__19() throws RecognitionException {
		try {
			int _type = T__19;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:20:7: ( 'DAYLY' )
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:20:9: 'DAYLY'
			{
			match("DAYLY"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__19"

	// $ANTLR start "T__20"
	public final void mT__20() throws RecognitionException {
		try {
			int _type = T__20;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:21:7: ( 'FR' )
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:21:9: 'FR'
			{
			match("FR"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__20"

	// $ANTLR start "T__21"
	public final void mT__21() throws RecognitionException {
		try {
			int _type = T__21;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:22:7: ( 'FREQ' )
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:22:9: 'FREQ'
			{
			match("FREQ"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__21"

	// $ANTLR start "T__22"
	public final void mT__22() throws RecognitionException {
		try {
			int _type = T__22;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:23:7: ( 'INTERVAL' )
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:23:9: 'INTERVAL'
			{
			match("INTERVAL"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__22"

	// $ANTLR start "T__23"
	public final void mT__23() throws RecognitionException {
		try {
			int _type = T__23;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:24:7: ( 'MO' )
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:24:9: 'MO'
			{
			match("MO"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__23"

	// $ANTLR start "T__24"
	public final void mT__24() throws RecognitionException {
		try {
			int _type = T__24;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:25:7: ( 'MONTHLY' )
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:25:9: 'MONTHLY'
			{
			match("MONTHLY"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__24"

	// $ANTLR start "T__25"
	public final void mT__25() throws RecognitionException {
		try {
			int _type = T__25;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:26:7: ( 'SA' )
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:26:9: 'SA'
			{
			match("SA"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__25"

	// $ANTLR start "T__26"
	public final void mT__26() throws RecognitionException {
		try {
			int _type = T__26;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:27:7: ( 'SU' )
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:27:9: 'SU'
			{
			match("SU"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__26"

	// $ANTLR start "T__27"
	public final void mT__27() throws RecognitionException {
		try {
			int _type = T__27;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:28:7: ( 'TH' )
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:28:9: 'TH'
			{
			match("TH"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__27"

	// $ANTLR start "T__28"
	public final void mT__28() throws RecognitionException {
		try {
			int _type = T__28;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:29:7: ( 'TU' )
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:29:9: 'TU'
			{
			match("TU"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__28"

	// $ANTLR start "T__29"
	public final void mT__29() throws RecognitionException {
		try {
			int _type = T__29;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:30:7: ( 'WE' )
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:30:9: 'WE'
			{
			match("WE"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__29"

	// $ANTLR start "T__30"
	public final void mT__30() throws RecognitionException {
		try {
			int _type = T__30;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:31:7: ( 'WEEKLY' )
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:31:9: 'WEEKLY'
			{
			match("WEEKLY"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__30"

	// $ANTLR start "T__31"
	public final void mT__31() throws RecognitionException {
		try {
			int _type = T__31;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:32:7: ( 'YEARLY' )
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:32:9: 'YEARLY'
			{
			match("YEARLY"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__31"

	// $ANTLR start "COMMENT"
	public final void mCOMMENT() throws RecognitionException {
		try {
			int _type = COMMENT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:67:9: ( '#' ( . )* ( '\\n' | '\\r' ) )
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:67:11: '#' ( . )* ( '\\n' | '\\r' )
			{
			match('#'); 
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:67:15: ( . )*
			loop1:
			while (true) {
				int alt1=2;
				int LA1_0 = input.LA(1);
				if ( (LA1_0=='\n'||LA1_0=='\r') ) {
					alt1=2;
				}
				else if ( ((LA1_0 >= '\u0000' && LA1_0 <= '\t')||(LA1_0 >= '\u000B' && LA1_0 <= '\f')||(LA1_0 >= '\u000E' && LA1_0 <= '\uFFFF')) ) {
					alt1=1;
				}

				switch (alt1) {
				case 1 :
					// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:67:15: .
					{
					matchAny(); 
					}
					break;

				default :
					break loop1;
				}
			}

			if ( input.LA(1)=='\n'||input.LA(1)=='\r' ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
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
	// $ANTLR end "COMMENT"

	// $ANTLR start "STRING_LITERAL"
	public final void mSTRING_LITERAL() throws RecognitionException {
		try {
			int _type = STRING_LITERAL;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			int c;

			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:70:3: ( '\"' ( '\"' '\"' |c=~ ( '\"' | '\\r' | '\\n' ) )* '\"' )
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:70:5: '\"' ( '\"' '\"' |c=~ ( '\"' | '\\r' | '\\n' ) )* '\"'
			{
			match('\"'); 
			 StringBuilder b = new StringBuilder(); 
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:72:5: ( '\"' '\"' |c=~ ( '\"' | '\\r' | '\\n' ) )*
			loop2:
			while (true) {
				int alt2=3;
				int LA2_0 = input.LA(1);
				if ( (LA2_0=='\"') ) {
					int LA2_1 = input.LA(2);
					if ( (LA2_1=='\"') ) {
						alt2=1;
					}

				}
				else if ( ((LA2_0 >= '\u0000' && LA2_0 <= '\t')||(LA2_0 >= '\u000B' && LA2_0 <= '\f')||(LA2_0 >= '\u000E' && LA2_0 <= '!')||(LA2_0 >= '#' && LA2_0 <= '\uFFFF')) ) {
					alt2=2;
				}

				switch (alt2) {
				case 1 :
					// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:72:7: '\"' '\"'
					{
					match('\"'); 
					match('\"'); 
					 b.appendCodePoint('"');
					}
					break;
				case 2 :
					// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:73:7: c=~ ( '\"' | '\\r' | '\\n' )
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
					break loop2;
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

	// $ANTLR start "ID"
	public final void mID() throws RecognitionException {
		try {
			int _type = ID;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:80:4: ( ( 'A' .. 'Z' | 'a' .. 'z' | '0' .. '9' )+ )
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:80:6: ( 'A' .. 'Z' | 'a' .. 'z' | '0' .. '9' )+
			{
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:80:6: ( 'A' .. 'Z' | 'a' .. 'z' | '0' .. '9' )+
			int cnt3=0;
			loop3:
			while (true) {
				int alt3=2;
				int LA3_0 = input.LA(1);
				if ( ((LA3_0 >= '0' && LA3_0 <= '9')||(LA3_0 >= 'A' && LA3_0 <= 'Z')||(LA3_0 >= 'a' && LA3_0 <= 'z')) ) {
					alt3=1;
				}

				switch (alt3) {
				case 1 :
					// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:
					{
					if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
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

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "ID"

	// $ANTLR start "DAYS_NUMBER"
	public final void mDAYS_NUMBER() throws RecognitionException {
		try {
			int _type = DAYS_NUMBER;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:82:13: ( '+' ( '0' .. '9' )+ ' ' 'DAY' ( 'S' )? )
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:82:15: '+' ( '0' .. '9' )+ ' ' 'DAY' ( 'S' )?
			{
			match('+'); 
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:82:18: ( '0' .. '9' )+
			int cnt4=0;
			loop4:
			while (true) {
				int alt4=2;
				int LA4_0 = input.LA(1);
				if ( ((LA4_0 >= '0' && LA4_0 <= '9')) ) {
					alt4=1;
				}

				switch (alt4) {
				case 1 :
					// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:
					{
					if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
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
					if ( cnt4 >= 1 ) break loop4;
					EarlyExitException eee = new EarlyExitException(4, input);
					throw eee;
				}
				cnt4++;
			}

			match(' '); 
			match("DAY"); 

			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:82:37: ( 'S' )?
			int alt5=2;
			int LA5_0 = input.LA(1);
			if ( (LA5_0=='S') ) {
				alt5=1;
			}
			switch (alt5) {
				case 1 :
					// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:82:38: 'S'
					{
					match('S'); 
					}
					break;

			}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "DAYS_NUMBER"

	// $ANTLR start "NUMBER"
	public final void mNUMBER() throws RecognitionException {
		try {
			int _type = NUMBER;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:84:9: ( ( '0' .. '9' )* )
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:84:11: ( '0' .. '9' )*
			{
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:84:11: ( '0' .. '9' )*
			loop6:
			while (true) {
				int alt6=2;
				int LA6_0 = input.LA(1);
				if ( ((LA6_0 >= '0' && LA6_0 <= '9')) ) {
					alt6=1;
				}

				switch (alt6) {
				case 1 :
					// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:
					{
					if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
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
					break loop6;
				}
			}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "NUMBER"

	// $ANTLR start "DATE_LITERAL"
	public final void mDATE_LITERAL() throws RecognitionException {
		try {
			int _type = DATE_LITERAL;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:85:14: ( ( '0' .. '9' ) ( '0' .. '9' ) '/' ( '0' .. '9' ) ( '0' .. '9' ) '/' ( '0' .. '9' ) ( '0' .. '9' ) ( '0' .. '9' ) ( '0' .. '9' ) )
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:85:16: ( '0' .. '9' ) ( '0' .. '9' ) '/' ( '0' .. '9' ) ( '0' .. '9' ) '/' ( '0' .. '9' ) ( '0' .. '9' ) ( '0' .. '9' ) ( '0' .. '9' )
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

	@Override
	public void mTokens() throws RecognitionException {
		// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:1:8: ( T__10 | T__11 | T__12 | T__13 | T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | T__30 | T__31 | COMMENT | STRING_LITERAL | ID | DAYS_NUMBER | NUMBER | DATE_LITERAL )
		int alt7=28;
		alt7 = dfa7.predict(input);
		switch (alt7) {
			case 1 :
				// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:1:10: T__10
				{
				mT__10(); 

				}
				break;
			case 2 :
				// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:1:16: T__11
				{
				mT__11(); 

				}
				break;
			case 3 :
				// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:1:22: T__12
				{
				mT__12(); 

				}
				break;
			case 4 :
				// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:1:28: T__13
				{
				mT__13(); 

				}
				break;
			case 5 :
				// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:1:34: T__14
				{
				mT__14(); 

				}
				break;
			case 6 :
				// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:1:40: T__15
				{
				mT__15(); 

				}
				break;
			case 7 :
				// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:1:46: T__16
				{
				mT__16(); 

				}
				break;
			case 8 :
				// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:1:52: T__17
				{
				mT__17(); 

				}
				break;
			case 9 :
				// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:1:58: T__18
				{
				mT__18(); 

				}
				break;
			case 10 :
				// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:1:64: T__19
				{
				mT__19(); 

				}
				break;
			case 11 :
				// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:1:70: T__20
				{
				mT__20(); 

				}
				break;
			case 12 :
				// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:1:76: T__21
				{
				mT__21(); 

				}
				break;
			case 13 :
				// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:1:82: T__22
				{
				mT__22(); 

				}
				break;
			case 14 :
				// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:1:88: T__23
				{
				mT__23(); 

				}
				break;
			case 15 :
				// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:1:94: T__24
				{
				mT__24(); 

				}
				break;
			case 16 :
				// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:1:100: T__25
				{
				mT__25(); 

				}
				break;
			case 17 :
				// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:1:106: T__26
				{
				mT__26(); 

				}
				break;
			case 18 :
				// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:1:112: T__27
				{
				mT__27(); 

				}
				break;
			case 19 :
				// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:1:118: T__28
				{
				mT__28(); 

				}
				break;
			case 20 :
				// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:1:124: T__29
				{
				mT__29(); 

				}
				break;
			case 21 :
				// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:1:130: T__30
				{
				mT__30(); 

				}
				break;
			case 22 :
				// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:1:136: T__31
				{
				mT__31(); 

				}
				break;
			case 23 :
				// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:1:142: COMMENT
				{
				mCOMMENT(); 

				}
				break;
			case 24 :
				// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:1:150: STRING_LITERAL
				{
				mSTRING_LITERAL(); 

				}
				break;
			case 25 :
				// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:1:165: ID
				{
				mID(); 

				}
				break;
			case 26 :
				// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:1:168: DAYS_NUMBER
				{
				mDAYS_NUMBER(); 

				}
				break;
			case 27 :
				// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:1:180: NUMBER
				{
				mNUMBER(); 

				}
				break;
			case 28 :
				// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:1:187: DATE_LITERAL
				{
				mDATE_LITERAL(); 

				}
				break;

		}
	}


	protected DFA7 dfa7 = new DFA7(this);
	static final String DFA7_eotS =
		"\1\23\1\24\4\uffff\11\22\2\uffff\1\22\4\uffff\2\22\1\50\1\22\1\53\1\54"+
		"\1\55\1\56\1\57\1\61\10\22\1\uffff\2\22\5\uffff\1\22\1\uffff\1\22\1\uffff"+
		"\6\22\1\104\4\22\1\111\3\22\1\115\1\uffff\4\22\1\uffff\3\22\1\uffff\2"+
		"\22\1\127\1\130\4\22\1\135\2\uffff\3\22\1\141\1\uffff\1\142\1\22\1\144"+
		"\2\uffff\1\145\2\uffff";
	static final String DFA7_eofS =
		"\146\uffff";
	static final String DFA7_minS =
		"\1\42\1\60\4\uffff\1\131\1\101\1\122\1\116\1\117\1\101\1\110\2\105\2\uffff"+
		"\1\60\4\uffff\1\104\1\131\1\60\1\124\6\60\1\101\1\57\1\101\1\122\2\117"+
		"\1\114\1\121\1\uffff\1\105\1\124\5\uffff\1\113\1\uffff\1\122\1\uffff\1"+
		"\60\1\131\1\105\1\116\1\122\1\131\1\60\1\122\1\110\2\114\1\60\1\105\1"+
		"\124\1\113\1\60\1\uffff\1\126\1\114\2\131\1\uffff\1\104\1\110\1\104\1"+
		"\uffff\1\101\1\131\2\60\1\101\1\104\1\101\1\114\1\60\2\uffff\1\131\1\101"+
		"\1\131\1\60\1\uffff\1\60\1\131\1\60\2\uffff\1\60\2\uffff";
	static final String DFA7_maxS =
		"\1\172\1\71\4\uffff\1\131\1\101\1\122\1\116\1\117\2\125\2\105\2\uffff"+
		"\1\71\4\uffff\1\127\1\131\1\172\1\124\6\172\1\101\1\71\1\101\1\122\2\117"+
		"\1\114\1\121\1\uffff\1\105\1\124\5\uffff\1\113\1\uffff\1\122\1\uffff\1"+
		"\71\1\131\1\105\1\116\1\122\1\131\1\172\1\122\1\110\2\114\1\172\1\105"+
		"\1\124\1\113\1\172\1\uffff\1\126\1\114\2\131\1\uffff\1\104\1\110\1\104"+
		"\1\uffff\1\101\1\131\2\172\1\101\1\104\1\101\1\114\1\172\2\uffff\1\131"+
		"\1\101\1\131\1\172\1\uffff\1\172\1\131\1\172\2\uffff\1\172\2\uffff";
	static final String DFA7_acceptS =
		"\2\uffff\1\2\1\3\1\4\1\5\11\uffff\1\27\1\30\1\uffff\1\31\1\33\1\1\1\32"+
		"\22\uffff\1\13\2\uffff\1\16\1\20\1\21\1\22\1\23\1\uffff\1\24\1\uffff\1"+
		"\34\20\uffff\1\14\4\uffff\1\6\3\uffff\1\12\11\uffff\1\25\1\26\4\uffff"+
		"\1\17\3\uffff\1\15\1\7\1\uffff\1\11\1\10";
	static final String DFA7_specialS =
		"\146\uffff}>";
	static final String[] DFA7_transitionS = {
			"\1\20\1\17\7\uffff\1\1\1\2\1\3\2\uffff\12\21\1\uffff\1\4\1\uffff\1\5"+
			"\3\uffff\1\22\1\6\1\22\1\7\1\22\1\10\2\22\1\11\3\22\1\12\5\22\1\13\1"+
			"\14\2\22\1\15\1\22\1\16\1\22\6\uffff\32\22",
			"\12\25",
			"",
			"",
			"",
			"",
			"\1\26",
			"\1\27",
			"\1\30",
			"\1\31",
			"\1\32",
			"\1\33\23\uffff\1\34",
			"\1\35\14\uffff\1\36",
			"\1\37",
			"\1\40",
			"",
			"",
			"\12\41",
			"",
			"",
			"",
			"",
			"\1\42\1\uffff\1\43\6\uffff\1\44\11\uffff\1\45",
			"\1\46",
			"\12\22\7\uffff\4\22\1\47\25\22\6\uffff\32\22",
			"\1\51",
			"\12\22\7\uffff\15\22\1\52\14\22\6\uffff\32\22",
			"\12\22\7\uffff\32\22\6\uffff\32\22",
			"\12\22\7\uffff\32\22\6\uffff\32\22",
			"\12\22\7\uffff\32\22\6\uffff\32\22",
			"\12\22\7\uffff\32\22\6\uffff\32\22",
			"\12\22\7\uffff\4\22\1\60\25\22\6\uffff\32\22",
			"\1\62",
			"\1\63\12\64",
			"\1\65",
			"\1\66",
			"\1\67",
			"\1\70",
			"\1\71",
			"\1\72",
			"",
			"\1\73",
			"\1\74",
			"",
			"",
			"",
			"",
			"",
			"\1\75",
			"",
			"\1\76",
			"",
			"\12\64",
			"\1\77",
			"\1\100",
			"\1\101",
			"\1\102",
			"\1\103",
			"\12\22\7\uffff\32\22\6\uffff\32\22",
			"\1\105",
			"\1\106",
			"\1\107",
			"\1\110",
			"\12\22\7\uffff\32\22\6\uffff\32\22",
			"\1\112",
			"\1\113",
			"\1\114",
			"\12\22\7\uffff\32\22\6\uffff\32\22",
			"",
			"\1\116",
			"\1\117",
			"\1\120",
			"\1\121",
			"",
			"\1\122",
			"\1\123",
			"\1\124",
			"",
			"\1\125",
			"\1\126",
			"\12\22\7\uffff\32\22\6\uffff\32\22",
			"\12\22\7\uffff\32\22\6\uffff\32\22",
			"\1\131",
			"\1\132",
			"\1\133",
			"\1\134",
			"\12\22\7\uffff\32\22\6\uffff\32\22",
			"",
			"",
			"\1\136",
			"\1\137",
			"\1\140",
			"\12\22\7\uffff\32\22\6\uffff\32\22",
			"",
			"\12\22\7\uffff\32\22\6\uffff\32\22",
			"\1\143",
			"\12\22\7\uffff\32\22\6\uffff\32\22",
			"",
			"",
			"\12\22\7\uffff\32\22\6\uffff\32\22",
			"",
			""
	};

	static final short[] DFA7_eot = DFA.unpackEncodedString(DFA7_eotS);
	static final short[] DFA7_eof = DFA.unpackEncodedString(DFA7_eofS);
	static final char[] DFA7_min = DFA.unpackEncodedStringToUnsignedChars(DFA7_minS);
	static final char[] DFA7_max = DFA.unpackEncodedStringToUnsignedChars(DFA7_maxS);
	static final short[] DFA7_accept = DFA.unpackEncodedString(DFA7_acceptS);
	static final short[] DFA7_special = DFA.unpackEncodedString(DFA7_specialS);
	static final short[][] DFA7_transition;

	static {
		int numStates = DFA7_transitionS.length;
		DFA7_transition = new short[numStates][];
		for (int i=0; i<numStates; i++) {
			DFA7_transition[i] = DFA.unpackEncodedString(DFA7_transitionS[i]);
		}
	}

	protected class DFA7 extends DFA {

		public DFA7(BaseRecognizer recognizer) {
			this.recognizer = recognizer;
			this.decisionNumber = 7;
			this.eot = DFA7_eot;
			this.eof = DFA7_eof;
			this.min = DFA7_min;
			this.max = DFA7_max;
			this.accept = DFA7_accept;
			this.special = DFA7_special;
			this.transition = DFA7_transition;
		}
		@Override
		public String getDescription() {
			return "1:1: Tokens : ( T__10 | T__11 | T__12 | T__13 | T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | T__30 | T__31 | COMMENT | STRING_LITERAL | ID | DAYS_NUMBER | NUMBER | DATE_LITERAL );";
		}
	}

}
