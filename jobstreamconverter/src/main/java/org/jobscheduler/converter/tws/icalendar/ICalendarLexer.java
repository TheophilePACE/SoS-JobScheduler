// $ANTLR 3.5.2 /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g 2015-04-20 06:29:13
 
package org.jobscheduler.converter.tws.icalendar ; 


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class ICalendarLexer extends Lexer {
	public static final int EOF=-1;
	public static final int T__6=6;
	public static final int T__7=7;
	public static final int T__8=8;
	public static final int T__9=9;
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
	public static final int COMMENT=4;
	public static final int NUMBER=5;

	// delegates
	// delegators
	public Lexer[] getDelegates() {
		return new Lexer[] {};
	}

	public ICalendarLexer() {} 
	public ICalendarLexer(CharStream input) {
		this(input, new RecognizerSharedState());
	}
	public ICalendarLexer(CharStream input, RecognizerSharedState state) {
		super(input,state);
	}
	@Override public String getGrammarFileName() { return "/home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g"; }

	// $ANTLR start "T__6"
	public final void mT__6() throws RecognitionException {
		try {
			int _type = T__6;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:11:6: ( ',' )
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:11:8: ','
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
	// $ANTLR end "T__6"

	// $ANTLR start "T__7"
	public final void mT__7() throws RecognitionException {
		try {
			int _type = T__7;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:12:6: ( '-' )
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:12:8: '-'
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
	// $ANTLR end "T__7"

	// $ANTLR start "T__8"
	public final void mT__8() throws RecognitionException {
		try {
			int _type = T__8;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:13:6: ( ';' )
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:13:8: ';'
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
	// $ANTLR end "T__8"

	// $ANTLR start "T__9"
	public final void mT__9() throws RecognitionException {
		try {
			int _type = T__9;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:14:6: ( '=' )
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:14:8: '='
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
	// $ANTLR end "T__9"

	// $ANTLR start "T__10"
	public final void mT__10() throws RecognitionException {
		try {
			int _type = T__10;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:15:7: ( 'BYDAY' )
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:15:9: 'BYDAY'
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
	// $ANTLR end "T__10"

	// $ANTLR start "T__11"
	public final void mT__11() throws RecognitionException {
		try {
			int _type = T__11;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:16:7: ( 'BYFREEDAY' )
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:16:9: 'BYFREEDAY'
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
	// $ANTLR end "T__11"

	// $ANTLR start "T__12"
	public final void mT__12() throws RecognitionException {
		try {
			int _type = T__12;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:17:7: ( 'BYMONTHDAY' )
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:17:9: 'BYMONTHDAY'
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
	// $ANTLR end "T__12"

	// $ANTLR start "T__13"
	public final void mT__13() throws RecognitionException {
		try {
			int _type = T__13;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:18:7: ( 'BYWORKDAY' )
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:18:9: 'BYWORKDAY'
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
	// $ANTLR end "T__13"

	// $ANTLR start "T__14"
	public final void mT__14() throws RecognitionException {
		try {
			int _type = T__14;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:19:7: ( 'DAILY' )
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:19:9: 'DAILY'
			{
			match("DAILY"); 

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
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:20:7: ( 'FR' )
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:20:9: 'FR'
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
	// $ANTLR end "T__15"

	// $ANTLR start "T__16"
	public final void mT__16() throws RecognitionException {
		try {
			int _type = T__16;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:21:7: ( 'FREQ' )
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:21:9: 'FREQ'
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
	// $ANTLR end "T__16"

	// $ANTLR start "T__17"
	public final void mT__17() throws RecognitionException {
		try {
			int _type = T__17;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:22:7: ( 'INTERVAL' )
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:22:9: 'INTERVAL'
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
	// $ANTLR end "T__17"

	// $ANTLR start "T__18"
	public final void mT__18() throws RecognitionException {
		try {
			int _type = T__18;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:23:7: ( 'MO' )
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:23:9: 'MO'
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
	// $ANTLR end "T__18"

	// $ANTLR start "T__19"
	public final void mT__19() throws RecognitionException {
		try {
			int _type = T__19;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:24:7: ( 'MONTHLY' )
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:24:9: 'MONTHLY'
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
	// $ANTLR end "T__19"

	// $ANTLR start "T__20"
	public final void mT__20() throws RecognitionException {
		try {
			int _type = T__20;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:25:7: ( 'SA' )
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:25:9: 'SA'
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
	// $ANTLR end "T__20"

	// $ANTLR start "T__21"
	public final void mT__21() throws RecognitionException {
		try {
			int _type = T__21;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:26:7: ( 'SU' )
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:26:9: 'SU'
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
	// $ANTLR end "T__21"

	// $ANTLR start "T__22"
	public final void mT__22() throws RecognitionException {
		try {
			int _type = T__22;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:27:7: ( 'TH' )
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:27:9: 'TH'
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
	// $ANTLR end "T__22"

	// $ANTLR start "T__23"
	public final void mT__23() throws RecognitionException {
		try {
			int _type = T__23;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:28:7: ( 'TU' )
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:28:9: 'TU'
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
	// $ANTLR end "T__23"

	// $ANTLR start "T__24"
	public final void mT__24() throws RecognitionException {
		try {
			int _type = T__24;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:29:7: ( 'WE' )
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:29:9: 'WE'
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
	// $ANTLR end "T__24"

	// $ANTLR start "T__25"
	public final void mT__25() throws RecognitionException {
		try {
			int _type = T__25;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:30:7: ( 'WEEKLY' )
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:30:9: 'WEEKLY'
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
	// $ANTLR end "T__25"

	// $ANTLR start "T__26"
	public final void mT__26() throws RecognitionException {
		try {
			int _type = T__26;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:31:7: ( 'YEARLY' )
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:31:9: 'YEARLY'
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
	// $ANTLR end "T__26"

	// $ANTLR start "COMMENT"
	public final void mCOMMENT() throws RecognitionException {
		try {
			int _type = COMMENT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:160:9: ( '#' ( . )* ( '\\n' | '\\r' ) )
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:160:11: '#' ( . )* ( '\\n' | '\\r' )
			{
			match('#'); 
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:160:15: ( . )*
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
					// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:160:15: .
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

	// $ANTLR start "NUMBER"
	public final void mNUMBER() throws RecognitionException {
		try {
			int _type = NUMBER;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:168:9: ( ( '0' .. '9' )+ )
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:168:11: ( '0' .. '9' )+
			{
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:168:11: ( '0' .. '9' )+
			int cnt2=0;
			loop2:
			while (true) {
				int alt2=2;
				int LA2_0 = input.LA(1);
				if ( ((LA2_0 >= '0' && LA2_0 <= '9')) ) {
					alt2=1;
				}

				switch (alt2) {
				case 1 :
					// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:
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
	// $ANTLR end "NUMBER"

	@Override
	public void mTokens() throws RecognitionException {
		// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:1:8: ( T__6 | T__7 | T__8 | T__9 | T__10 | T__11 | T__12 | T__13 | T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | COMMENT | NUMBER )
		int alt3=23;
		switch ( input.LA(1) ) {
		case ',':
			{
			alt3=1;
			}
			break;
		case '-':
			{
			alt3=2;
			}
			break;
		case ';':
			{
			alt3=3;
			}
			break;
		case '=':
			{
			alt3=4;
			}
			break;
		case 'B':
			{
			int LA3_5 = input.LA(2);
			if ( (LA3_5=='Y') ) {
				switch ( input.LA(3) ) {
				case 'D':
					{
					alt3=5;
					}
					break;
				case 'F':
					{
					alt3=6;
					}
					break;
				case 'M':
					{
					alt3=7;
					}
					break;
				case 'W':
					{
					alt3=8;
					}
					break;
				default:
					int nvaeMark = input.mark();
					try {
						for (int nvaeConsume = 0; nvaeConsume < 3 - 1; nvaeConsume++) {
							input.consume();
						}
						NoViableAltException nvae =
							new NoViableAltException("", 3, 16, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}
			}

			else {
				int nvaeMark = input.mark();
				try {
					input.consume();
					NoViableAltException nvae =
						new NoViableAltException("", 3, 5, input);
					throw nvae;
				} finally {
					input.rewind(nvaeMark);
				}
			}

			}
			break;
		case 'D':
			{
			alt3=9;
			}
			break;
		case 'F':
			{
			int LA3_7 = input.LA(2);
			if ( (LA3_7=='R') ) {
				int LA3_17 = input.LA(3);
				if ( (LA3_17=='E') ) {
					alt3=11;
				}

				else {
					alt3=10;
				}

			}

			else {
				int nvaeMark = input.mark();
				try {
					input.consume();
					NoViableAltException nvae =
						new NoViableAltException("", 3, 7, input);
					throw nvae;
				} finally {
					input.rewind(nvaeMark);
				}
			}

			}
			break;
		case 'I':
			{
			alt3=12;
			}
			break;
		case 'M':
			{
			int LA3_9 = input.LA(2);
			if ( (LA3_9=='O') ) {
				int LA3_18 = input.LA(3);
				if ( (LA3_18=='N') ) {
					alt3=14;
				}

				else {
					alt3=13;
				}

			}

			else {
				int nvaeMark = input.mark();
				try {
					input.consume();
					NoViableAltException nvae =
						new NoViableAltException("", 3, 9, input);
					throw nvae;
				} finally {
					input.rewind(nvaeMark);
				}
			}

			}
			break;
		case 'S':
			{
			int LA3_10 = input.LA(2);
			if ( (LA3_10=='A') ) {
				alt3=15;
			}
			else if ( (LA3_10=='U') ) {
				alt3=16;
			}

			else {
				int nvaeMark = input.mark();
				try {
					input.consume();
					NoViableAltException nvae =
						new NoViableAltException("", 3, 10, input);
					throw nvae;
				} finally {
					input.rewind(nvaeMark);
				}
			}

			}
			break;
		case 'T':
			{
			int LA3_11 = input.LA(2);
			if ( (LA3_11=='H') ) {
				alt3=17;
			}
			else if ( (LA3_11=='U') ) {
				alt3=18;
			}

			else {
				int nvaeMark = input.mark();
				try {
					input.consume();
					NoViableAltException nvae =
						new NoViableAltException("", 3, 11, input);
					throw nvae;
				} finally {
					input.rewind(nvaeMark);
				}
			}

			}
			break;
		case 'W':
			{
			int LA3_12 = input.LA(2);
			if ( (LA3_12=='E') ) {
				int LA3_23 = input.LA(3);
				if ( (LA3_23=='E') ) {
					alt3=20;
				}

				else {
					alt3=19;
				}

			}

			else {
				int nvaeMark = input.mark();
				try {
					input.consume();
					NoViableAltException nvae =
						new NoViableAltException("", 3, 12, input);
					throw nvae;
				} finally {
					input.rewind(nvaeMark);
				}
			}

			}
			break;
		case 'Y':
			{
			alt3=21;
			}
			break;
		case '#':
			{
			alt3=22;
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
			alt3=23;
			}
			break;
		default:
			NoViableAltException nvae =
				new NoViableAltException("", 3, 0, input);
			throw nvae;
		}
		switch (alt3) {
			case 1 :
				// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:1:10: T__6
				{
				mT__6(); 

				}
				break;
			case 2 :
				// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:1:15: T__7
				{
				mT__7(); 

				}
				break;
			case 3 :
				// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:1:20: T__8
				{
				mT__8(); 

				}
				break;
			case 4 :
				// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:1:25: T__9
				{
				mT__9(); 

				}
				break;
			case 5 :
				// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:1:30: T__10
				{
				mT__10(); 

				}
				break;
			case 6 :
				// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:1:36: T__11
				{
				mT__11(); 

				}
				break;
			case 7 :
				// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:1:42: T__12
				{
				mT__12(); 

				}
				break;
			case 8 :
				// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:1:48: T__13
				{
				mT__13(); 

				}
				break;
			case 9 :
				// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:1:54: T__14
				{
				mT__14(); 

				}
				break;
			case 10 :
				// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:1:60: T__15
				{
				mT__15(); 

				}
				break;
			case 11 :
				// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:1:66: T__16
				{
				mT__16(); 

				}
				break;
			case 12 :
				// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:1:72: T__17
				{
				mT__17(); 

				}
				break;
			case 13 :
				// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:1:78: T__18
				{
				mT__18(); 

				}
				break;
			case 14 :
				// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:1:84: T__19
				{
				mT__19(); 

				}
				break;
			case 15 :
				// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:1:90: T__20
				{
				mT__20(); 

				}
				break;
			case 16 :
				// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:1:96: T__21
				{
				mT__21(); 

				}
				break;
			case 17 :
				// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:1:102: T__22
				{
				mT__22(); 

				}
				break;
			case 18 :
				// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:1:108: T__23
				{
				mT__23(); 

				}
				break;
			case 19 :
				// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:1:114: T__24
				{
				mT__24(); 

				}
				break;
			case 20 :
				// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:1:120: T__25
				{
				mT__25(); 

				}
				break;
			case 21 :
				// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:1:126: T__26
				{
				mT__26(); 

				}
				break;
			case 22 :
				// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:1:132: COMMENT
				{
				mCOMMENT(); 

				}
				break;
			case 23 :
				// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/ICalendar.g:1:140: NUMBER
				{
				mNUMBER(); 

				}
				break;

		}
	}



}
