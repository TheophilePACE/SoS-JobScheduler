// $ANTLR 3.5.2 /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g 2015-04-20 06:29:11

package org.jobscheduler.converter.tws.icalendar ;




import org.antlr.runtime.*;

import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

import org.antlr.runtime.tree.*;
import org.jobscheduler.converter.tws.scheduler.*;


@SuppressWarnings("all")
public class TWS_ICALENDARParser extends Parser {
	public static final String[] tokenNames = new String[] {
		"<invalid>", "<EOR>", "<DOWN>", "<UP>", "COMMENT", "DATE_LITERAL", "DAYS_NUMBER", 
		"ID", "NUMBER", "STRING_LITERAL", "'+'", "','", "'-'", "';'", "'='", "'BYDAY'", 
		"'BYFREEDAY'", "'BYMONTHDAY'", "'BYWORKDAY'", "'DAYLY'", "'FR'", "'FREQ'", 
		"'INTERVAL'", "'MO'", "'MONTHLY'", "'SA'", "'SU'", "'TH'", "'TU'", "'WE'", 
		"'WEEKLY'", "'YEARLY'"
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
	public Parser[] getDelegates() {
		return new Parser[] {};
	}

	// delegators


	public TWS_ICALENDARParser(TokenStream input) {
		this(input, new RecognizerSharedState());
	}
	public TWS_ICALENDARParser(TokenStream input, RecognizerSharedState state) {
		super(input, state);
	}

	protected TreeAdaptor adaptor = new CommonTreeAdaptor();

	public void setTreeAdaptor(TreeAdaptor adaptor) {
		this.adaptor = adaptor;
	}
	public TreeAdaptor getTreeAdaptor() {
		return adaptor;
	}
	@Override public String[] getTokenNames() { return TWS_ICALENDARParser.tokenNames; }
	@Override public String getGrammarFileName() { return "/home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g"; }


	public static class icalendar_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "icalendar"
	// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:18:1: icalendar : 'FREQ' '=' ( ( 'DAYLY' ) | ( 'WEEKLY' ) | 'MONTHLY' | 'YEARLY' ) ( ';' 'INTERVAL' '=' ( '-' )? NUMBER )? ( ';' ( 'BYFREEDAY' | 'BYWORKDAY' | 'BYDAY' '=' weekdayList | 'BYMONTHDAY' '=' monthdayList ) )? ;
	public final TWS_ICALENDARParser.icalendar_return icalendar() throws RecognitionException {
		TWS_ICALENDARParser.icalendar_return retval = new TWS_ICALENDARParser.icalendar_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token string_literal1=null;
		Token char_literal2=null;
		Token string_literal3=null;
		Token string_literal4=null;
		Token string_literal5=null;
		Token string_literal6=null;
		Token char_literal7=null;
		Token string_literal8=null;
		Token char_literal9=null;
		Token char_literal10=null;
		Token NUMBER11=null;
		Token char_literal12=null;
		Token string_literal13=null;
		Token string_literal14=null;
		Token string_literal15=null;
		Token char_literal16=null;
		Token string_literal18=null;
		Token char_literal19=null;
		ParserRuleReturnScope weekdayList17 =null;
		ParserRuleReturnScope monthdayList20 =null;

		Object string_literal1_tree=null;
		Object char_literal2_tree=null;
		Object string_literal3_tree=null;
		Object string_literal4_tree=null;
		Object string_literal5_tree=null;
		Object string_literal6_tree=null;
		Object char_literal7_tree=null;
		Object string_literal8_tree=null;
		Object char_literal9_tree=null;
		Object char_literal10_tree=null;
		Object NUMBER11_tree=null;
		Object char_literal12_tree=null;
		Object string_literal13_tree=null;
		Object string_literal14_tree=null;
		Object string_literal15_tree=null;
		Object char_literal16_tree=null;
		Object string_literal18_tree=null;
		Object char_literal19_tree=null;

		try {
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:18:11: ( 'FREQ' '=' ( ( 'DAYLY' ) | ( 'WEEKLY' ) | 'MONTHLY' | 'YEARLY' ) ( ';' 'INTERVAL' '=' ( '-' )? NUMBER )? ( ';' ( 'BYFREEDAY' | 'BYWORKDAY' | 'BYDAY' '=' weekdayList | 'BYMONTHDAY' '=' monthdayList ) )? )
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:19:1: 'FREQ' '=' ( ( 'DAYLY' ) | ( 'WEEKLY' ) | 'MONTHLY' | 'YEARLY' ) ( ';' 'INTERVAL' '=' ( '-' )? NUMBER )? ( ';' ( 'BYFREEDAY' | 'BYWORKDAY' | 'BYDAY' '=' weekdayList | 'BYMONTHDAY' '=' monthdayList ) )?
			{
			root_0 = (Object)adaptor.nil();



			TWSICalendar calendar = new TWSICalendar() ;

			string_literal1=(Token)match(input,21,FOLLOW_21_in_icalendar52); 
			string_literal1_tree = (Object)adaptor.create(string_literal1);
			adaptor.addChild(root_0, string_literal1_tree);

			char_literal2=(Token)match(input,14,FOLLOW_14_in_icalendar54); 
			char_literal2_tree = (Object)adaptor.create(char_literal2);
			adaptor.addChild(root_0, char_literal2_tree);

			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:24:13: ( ( 'DAYLY' ) | ( 'WEEKLY' ) | 'MONTHLY' | 'YEARLY' )
			int alt1=4;
			switch ( input.LA(1) ) {
			case 19:
				{
				alt1=1;
				}
				break;
			case 30:
				{
				alt1=2;
				}
				break;
			case 24:
				{
				alt1=3;
				}
				break;
			case 31:
				{
				alt1=4;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 1, 0, input);
				throw nvae;
			}
			switch (alt1) {
				case 1 :
					// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:24:14: ( 'DAYLY' )
					{
					// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:24:14: ( 'DAYLY' )
					// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:24:15: 'DAYLY'
					{
					string_literal3=(Token)match(input,19,FOLLOW_19_in_icalendar59); 
					string_literal3_tree = (Object)adaptor.create(string_literal3);
					adaptor.addChild(root_0, string_literal3_tree);


					Period period = new Period() ;
					calendar.period = period ; 

					}

					}
					break;
				case 2 :
					// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:31:2: ( 'WEEKLY' )
					{
					// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:31:2: ( 'WEEKLY' )
					// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:31:3: 'WEEKLY'
					{
					string_literal4=(Token)match(input,30,FOLLOW_30_in_icalendar68); 
					string_literal4_tree = (Object)adaptor.create(string_literal4);
					adaptor.addChild(root_0, string_literal4_tree);


					Weekdays wds = new Weekdays() ;


					}

					}
					break;
				case 3 :
					// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:38:2: 'MONTHLY'
					{
					string_literal5=(Token)match(input,24,FOLLOW_24_in_icalendar76); 
					string_literal5_tree = (Object)adaptor.create(string_literal5);
					adaptor.addChild(root_0, string_literal5_tree);

					}
					break;
				case 4 :
					// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:40:2: 'YEARLY'
					{
					string_literal6=(Token)match(input,31,FOLLOW_31_in_icalendar80); 
					string_literal6_tree = (Object)adaptor.create(string_literal6);
					adaptor.addChild(root_0, string_literal6_tree);

					}
					break;

			}

			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:44:2: ( ';' 'INTERVAL' '=' ( '-' )? NUMBER )?
			int alt3=2;
			int LA3_0 = input.LA(1);
			if ( (LA3_0==13) ) {
				int LA3_1 = input.LA(2);
				if ( (LA3_1==22) ) {
					alt3=1;
				}
			}
			switch (alt3) {
				case 1 :
					// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:44:3: ';' 'INTERVAL' '=' ( '-' )? NUMBER
					{
					char_literal7=(Token)match(input,13,FOLLOW_13_in_icalendar89); 
					char_literal7_tree = (Object)adaptor.create(char_literal7);
					adaptor.addChild(root_0, char_literal7_tree);

					string_literal8=(Token)match(input,22,FOLLOW_22_in_icalendar90); 
					string_literal8_tree = (Object)adaptor.create(string_literal8);
					adaptor.addChild(root_0, string_literal8_tree);

					char_literal9=(Token)match(input,14,FOLLOW_14_in_icalendar92); 
					char_literal9_tree = (Object)adaptor.create(char_literal9);
					adaptor.addChild(root_0, char_literal9_tree);

					// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:44:21: ( '-' )?
					int alt2=2;
					int LA2_0 = input.LA(1);
					if ( (LA2_0==12) ) {
						alt2=1;
					}
					switch (alt2) {
						case 1 :
							// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:44:22: '-'
							{
							char_literal10=(Token)match(input,12,FOLLOW_12_in_icalendar95); 
							char_literal10_tree = (Object)adaptor.create(char_literal10);
							adaptor.addChild(root_0, char_literal10_tree);

							}
							break;

					}

					NUMBER11=(Token)match(input,NUMBER,FOLLOW_NUMBER_in_icalendar99); 
					NUMBER11_tree = (Object)adaptor.create(NUMBER11);
					adaptor.addChild(root_0, NUMBER11_tree);

					}
					break;

			}

			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:46:2: ( ';' ( 'BYFREEDAY' | 'BYWORKDAY' | 'BYDAY' '=' weekdayList | 'BYMONTHDAY' '=' monthdayList ) )?
			int alt5=2;
			int LA5_0 = input.LA(1);
			if ( (LA5_0==13) ) {
				alt5=1;
			}
			switch (alt5) {
				case 1 :
					// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:46:3: ';' ( 'BYFREEDAY' | 'BYWORKDAY' | 'BYDAY' '=' weekdayList | 'BYMONTHDAY' '=' monthdayList )
					{
					char_literal12=(Token)match(input,13,FOLLOW_13_in_icalendar106); 
					char_literal12_tree = (Object)adaptor.create(char_literal12);
					adaptor.addChild(root_0, char_literal12_tree);

					// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:46:7: ( 'BYFREEDAY' | 'BYWORKDAY' | 'BYDAY' '=' weekdayList | 'BYMONTHDAY' '=' monthdayList )
					int alt4=4;
					switch ( input.LA(1) ) {
					case 16:
						{
						alt4=1;
						}
						break;
					case 18:
						{
						alt4=2;
						}
						break;
					case 15:
						{
						alt4=3;
						}
						break;
					case 17:
						{
						alt4=4;
						}
						break;
					default:
						NoViableAltException nvae =
							new NoViableAltException("", 4, 0, input);
						throw nvae;
					}
					switch (alt4) {
						case 1 :
							// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:46:8: 'BYFREEDAY'
							{
							string_literal13=(Token)match(input,16,FOLLOW_16_in_icalendar109); 
							string_literal13_tree = (Object)adaptor.create(string_literal13);
							adaptor.addChild(root_0, string_literal13_tree);

							}
							break;
						case 2 :
							// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:46:20: 'BYWORKDAY'
							{
							string_literal14=(Token)match(input,18,FOLLOW_18_in_icalendar111); 
							string_literal14_tree = (Object)adaptor.create(string_literal14);
							adaptor.addChild(root_0, string_literal14_tree);

							}
							break;
						case 3 :
							// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:46:32: 'BYDAY' '=' weekdayList
							{
							string_literal15=(Token)match(input,15,FOLLOW_15_in_icalendar113); 
							string_literal15_tree = (Object)adaptor.create(string_literal15);
							adaptor.addChild(root_0, string_literal15_tree);

							char_literal16=(Token)match(input,14,FOLLOW_14_in_icalendar115); 
							char_literal16_tree = (Object)adaptor.create(char_literal16);
							adaptor.addChild(root_0, char_literal16_tree);

							pushFollow(FOLLOW_weekdayList_in_icalendar117);
							weekdayList17=weekdayList();
							state._fsp--;

							adaptor.addChild(root_0, weekdayList17.getTree());

							}
							break;
						case 4 :
							// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:47:1: 'BYMONTHDAY' '=' monthdayList
							{
							string_literal18=(Token)match(input,17,FOLLOW_17_in_icalendar120); 
							string_literal18_tree = (Object)adaptor.create(string_literal18);
							adaptor.addChild(root_0, string_literal18_tree);

							char_literal19=(Token)match(input,14,FOLLOW_14_in_icalendar122); 
							char_literal19_tree = (Object)adaptor.create(char_literal19);
							adaptor.addChild(root_0, char_literal19_tree);

							pushFollow(FOLLOW_monthdayList_in_icalendar124);
							monthdayList20=monthdayList();
							state._fsp--;

							adaptor.addChild(root_0, monthdayList20.getTree());

							}
							break;

					}

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
	// $ANTLR end "icalendar"


	public static class weekdayList_return extends ParserRuleReturnScope {
		public Weekdays weekDays;
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "weekdayList"
	// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:51:2: weekdayList returns [Weekdays weekDays] : ( 'SU' )? ( ',' 'MO' )? ( ',' 'TU' )? ( ',' 'WE' )? ( ',' 'TH' )? ( ',' 'FR' )? ( ',' 'SA' )? ;
	public final TWS_ICALENDARParser.weekdayList_return weekdayList() throws RecognitionException {
		TWS_ICALENDARParser.weekdayList_return retval = new TWS_ICALENDARParser.weekdayList_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token string_literal21=null;
		Token char_literal22=null;
		Token string_literal23=null;
		Token char_literal24=null;
		Token string_literal25=null;
		Token char_literal26=null;
		Token string_literal27=null;
		Token char_literal28=null;
		Token string_literal29=null;
		Token char_literal30=null;
		Token string_literal31=null;
		Token char_literal32=null;
		Token string_literal33=null;

		Object string_literal21_tree=null;
		Object char_literal22_tree=null;
		Object string_literal23_tree=null;
		Object char_literal24_tree=null;
		Object string_literal25_tree=null;
		Object char_literal26_tree=null;
		Object string_literal27_tree=null;
		Object char_literal28_tree=null;
		Object string_literal29_tree=null;
		Object char_literal30_tree=null;
		Object string_literal31_tree=null;
		Object char_literal32_tree=null;
		Object string_literal33_tree=null;

		try {
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:51:41: ( ( 'SU' )? ( ',' 'MO' )? ( ',' 'TU' )? ( ',' 'WE' )? ( ',' 'TH' )? ( ',' 'FR' )? ( ',' 'SA' )? )
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:52:2: ( 'SU' )? ( ',' 'MO' )? ( ',' 'TU' )? ( ',' 'WE' )? ( ',' 'TH' )? ( ',' 'FR' )? ( ',' 'SA' )?
			{
			root_0 = (Object)adaptor.nil();



			 retval.weekDays = new Weekdays() ;
			 
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:57:2: ( 'SU' )?
			int alt6=2;
			int LA6_0 = input.LA(1);
			if ( (LA6_0==26) ) {
				alt6=1;
			}
			switch (alt6) {
				case 1 :
					// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:57:3: 'SU'
					{
					string_literal21=(Token)match(input,26,FOLLOW_26_in_weekdayList151); 
					string_literal21_tree = (Object)adaptor.create(string_literal21);
					adaptor.addChild(root_0, string_literal21_tree);

					Calendar.addDay(retval.weekDays,"7");
					}
					break;

			}

			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:57:44: ( ',' 'MO' )?
			int alt7=2;
			int LA7_0 = input.LA(1);
			if ( (LA7_0==11) ) {
				int LA7_1 = input.LA(2);
				if ( (LA7_1==23) ) {
					alt7=1;
				}
			}
			switch (alt7) {
				case 1 :
					// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:57:45: ',' 'MO'
					{
					char_literal22=(Token)match(input,11,FOLLOW_11_in_weekdayList158); 
					char_literal22_tree = (Object)adaptor.create(char_literal22);
					adaptor.addChild(root_0, char_literal22_tree);

					string_literal23=(Token)match(input,23,FOLLOW_23_in_weekdayList160); 
					string_literal23_tree = (Object)adaptor.create(string_literal23);
					adaptor.addChild(root_0, string_literal23_tree);

					Calendar.addDay(retval.weekDays,"1");
					}
					break;

			}

			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:57:90: ( ',' 'TU' )?
			int alt8=2;
			int LA8_0 = input.LA(1);
			if ( (LA8_0==11) ) {
				int LA8_1 = input.LA(2);
				if ( (LA8_1==28) ) {
					alt8=1;
				}
			}
			switch (alt8) {
				case 1 :
					// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:57:91: ',' 'TU'
					{
					char_literal24=(Token)match(input,11,FOLLOW_11_in_weekdayList167); 
					char_literal24_tree = (Object)adaptor.create(char_literal24);
					adaptor.addChild(root_0, char_literal24_tree);

					string_literal25=(Token)match(input,28,FOLLOW_28_in_weekdayList169); 
					string_literal25_tree = (Object)adaptor.create(string_literal25);
					adaptor.addChild(root_0, string_literal25_tree);

					Calendar.addDay(retval.weekDays,"2");
					}
					break;

			}

			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:57:136: ( ',' 'WE' )?
			int alt9=2;
			int LA9_0 = input.LA(1);
			if ( (LA9_0==11) ) {
				int LA9_1 = input.LA(2);
				if ( (LA9_1==29) ) {
					alt9=1;
				}
			}
			switch (alt9) {
				case 1 :
					// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:57:137: ',' 'WE'
					{
					char_literal26=(Token)match(input,11,FOLLOW_11_in_weekdayList176); 
					char_literal26_tree = (Object)adaptor.create(char_literal26);
					adaptor.addChild(root_0, char_literal26_tree);

					string_literal27=(Token)match(input,29,FOLLOW_29_in_weekdayList178); 
					string_literal27_tree = (Object)adaptor.create(string_literal27);
					adaptor.addChild(root_0, string_literal27_tree);

					Calendar.addDay(retval.weekDays,"3");
					}
					break;

			}

			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:57:182: ( ',' 'TH' )?
			int alt10=2;
			int LA10_0 = input.LA(1);
			if ( (LA10_0==11) ) {
				int LA10_1 = input.LA(2);
				if ( (LA10_1==27) ) {
					alt10=1;
				}
			}
			switch (alt10) {
				case 1 :
					// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:57:183: ',' 'TH'
					{
					char_literal28=(Token)match(input,11,FOLLOW_11_in_weekdayList185); 
					char_literal28_tree = (Object)adaptor.create(char_literal28);
					adaptor.addChild(root_0, char_literal28_tree);

					string_literal29=(Token)match(input,27,FOLLOW_27_in_weekdayList187); 
					string_literal29_tree = (Object)adaptor.create(string_literal29);
					adaptor.addChild(root_0, string_literal29_tree);

					Calendar.addDay(retval.weekDays,"4");
					}
					break;

			}

			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:57:228: ( ',' 'FR' )?
			int alt11=2;
			int LA11_0 = input.LA(1);
			if ( (LA11_0==11) ) {
				int LA11_1 = input.LA(2);
				if ( (LA11_1==20) ) {
					alt11=1;
				}
			}
			switch (alt11) {
				case 1 :
					// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:57:229: ',' 'FR'
					{
					char_literal30=(Token)match(input,11,FOLLOW_11_in_weekdayList194); 
					char_literal30_tree = (Object)adaptor.create(char_literal30);
					adaptor.addChild(root_0, char_literal30_tree);

					string_literal31=(Token)match(input,20,FOLLOW_20_in_weekdayList196); 
					string_literal31_tree = (Object)adaptor.create(string_literal31);
					adaptor.addChild(root_0, string_literal31_tree);

					Calendar.addDay(retval.weekDays,"5");
					}
					break;

			}

			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:57:275: ( ',' 'SA' )?
			int alt12=2;
			int LA12_0 = input.LA(1);
			if ( (LA12_0==11) ) {
				alt12=1;
			}
			switch (alt12) {
				case 1 :
					// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:57:276: ',' 'SA'
					{
					char_literal32=(Token)match(input,11,FOLLOW_11_in_weekdayList204); 
					char_literal32_tree = (Object)adaptor.create(char_literal32);
					adaptor.addChild(root_0, char_literal32_tree);

					string_literal33=(Token)match(input,25,FOLLOW_25_in_weekdayList206); 
					string_literal33_tree = (Object)adaptor.create(string_literal33);
					adaptor.addChild(root_0, string_literal33_tree);

					Calendar.addDay(retval.weekDays,"6");
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
	// $ANTLR end "weekdayList"


	public static class monthdayList_return extends ParserRuleReturnScope {
		public Monthdays monthDays;
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "monthdayList"
	// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:60:2: monthdayList returns [Monthdays monthDays] : ( '+' NUMBER )? ( '-' NUMBER )? ( NUMBER )? ;
	public final TWS_ICALENDARParser.monthdayList_return monthdayList() throws RecognitionException {
		TWS_ICALENDARParser.monthdayList_return retval = new TWS_ICALENDARParser.monthdayList_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token char_literal34=null;
		Token NUMBER35=null;
		Token char_literal36=null;
		Token NUMBER37=null;
		Token NUMBER38=null;

		Object char_literal34_tree=null;
		Object NUMBER35_tree=null;
		Object char_literal36_tree=null;
		Object NUMBER37_tree=null;
		Object NUMBER38_tree=null;

		try {
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:60:44: ( ( '+' NUMBER )? ( '-' NUMBER )? ( NUMBER )? )
			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:62:2: ( '+' NUMBER )? ( '-' NUMBER )? ( NUMBER )?
			{
			root_0 = (Object)adaptor.nil();


			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:62:2: ( '+' NUMBER )?
			int alt13=2;
			int LA13_0 = input.LA(1);
			if ( (LA13_0==10) ) {
				alt13=1;
			}
			switch (alt13) {
				case 1 :
					// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:62:3: '+' NUMBER
					{
					char_literal34=(Token)match(input,10,FOLLOW_10_in_monthdayList229); 
					char_literal34_tree = (Object)adaptor.create(char_literal34);
					adaptor.addChild(root_0, char_literal34_tree);

					NUMBER35=(Token)match(input,NUMBER,FOLLOW_NUMBER_in_monthdayList231); 
					NUMBER35_tree = (Object)adaptor.create(NUMBER35);
					adaptor.addChild(root_0, NUMBER35_tree);


					}
					break;

			}

			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:62:19: ( '-' NUMBER )?
			int alt14=2;
			int LA14_0 = input.LA(1);
			if ( (LA14_0==12) ) {
				alt14=1;
			}
			switch (alt14) {
				case 1 :
					// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:62:20: '-' NUMBER
					{
					char_literal36=(Token)match(input,12,FOLLOW_12_in_monthdayList238); 
					char_literal36_tree = (Object)adaptor.create(char_literal36);
					adaptor.addChild(root_0, char_literal36_tree);

					NUMBER37=(Token)match(input,NUMBER,FOLLOW_NUMBER_in_monthdayList240); 
					NUMBER37_tree = (Object)adaptor.create(NUMBER37);
					adaptor.addChild(root_0, NUMBER37_tree);


					}
					break;

			}

			// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:62:36: ( NUMBER )?
			int alt15=2;
			int LA15_0 = input.LA(1);
			if ( (LA15_0==NUMBER) ) {
				alt15=1;
			}
			switch (alt15) {
				case 1 :
					// /home/cloud/Desktop/git/jstech/migration/af.jstech.migration/src/af/jstech/migration/icalendar/TWS_ICALENDAR.g:62:37: NUMBER
					{
					NUMBER38=(Token)match(input,NUMBER,FOLLOW_NUMBER_in_monthdayList247); 
					NUMBER38_tree = (Object)adaptor.create(NUMBER38);
					adaptor.addChild(root_0, NUMBER38_tree);


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
	// $ANTLR end "monthdayList"

	// Delegated rules



	public static final BitSet FOLLOW_21_in_icalendar52 = new BitSet(new long[]{0x0000000000004000L});
	public static final BitSet FOLLOW_14_in_icalendar54 = new BitSet(new long[]{0x00000000C1080000L});
	public static final BitSet FOLLOW_19_in_icalendar59 = new BitSet(new long[]{0x0000000000002002L});
	public static final BitSet FOLLOW_30_in_icalendar68 = new BitSet(new long[]{0x0000000000002002L});
	public static final BitSet FOLLOW_24_in_icalendar76 = new BitSet(new long[]{0x0000000000002002L});
	public static final BitSet FOLLOW_31_in_icalendar80 = new BitSet(new long[]{0x0000000000002002L});
	public static final BitSet FOLLOW_13_in_icalendar89 = new BitSet(new long[]{0x0000000000400000L});
	public static final BitSet FOLLOW_22_in_icalendar90 = new BitSet(new long[]{0x0000000000004000L});
	public static final BitSet FOLLOW_14_in_icalendar92 = new BitSet(new long[]{0x0000000000001100L});
	public static final BitSet FOLLOW_12_in_icalendar95 = new BitSet(new long[]{0x0000000000000100L});
	public static final BitSet FOLLOW_NUMBER_in_icalendar99 = new BitSet(new long[]{0x0000000000002002L});
	public static final BitSet FOLLOW_13_in_icalendar106 = new BitSet(new long[]{0x0000000000078000L});
	public static final BitSet FOLLOW_16_in_icalendar109 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_18_in_icalendar111 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_15_in_icalendar113 = new BitSet(new long[]{0x0000000000004000L});
	public static final BitSet FOLLOW_14_in_icalendar115 = new BitSet(new long[]{0x0000000004000800L});
	public static final BitSet FOLLOW_weekdayList_in_icalendar117 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_17_in_icalendar120 = new BitSet(new long[]{0x0000000000004000L});
	public static final BitSet FOLLOW_14_in_icalendar122 = new BitSet(new long[]{0x0000000000001500L});
	public static final BitSet FOLLOW_monthdayList_in_icalendar124 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_26_in_weekdayList151 = new BitSet(new long[]{0x0000000000000802L});
	public static final BitSet FOLLOW_11_in_weekdayList158 = new BitSet(new long[]{0x0000000000800000L});
	public static final BitSet FOLLOW_23_in_weekdayList160 = new BitSet(new long[]{0x0000000000000802L});
	public static final BitSet FOLLOW_11_in_weekdayList167 = new BitSet(new long[]{0x0000000010000000L});
	public static final BitSet FOLLOW_28_in_weekdayList169 = new BitSet(new long[]{0x0000000000000802L});
	public static final BitSet FOLLOW_11_in_weekdayList176 = new BitSet(new long[]{0x0000000020000000L});
	public static final BitSet FOLLOW_29_in_weekdayList178 = new BitSet(new long[]{0x0000000000000802L});
	public static final BitSet FOLLOW_11_in_weekdayList185 = new BitSet(new long[]{0x0000000008000000L});
	public static final BitSet FOLLOW_27_in_weekdayList187 = new BitSet(new long[]{0x0000000000000802L});
	public static final BitSet FOLLOW_11_in_weekdayList194 = new BitSet(new long[]{0x0000000000100000L});
	public static final BitSet FOLLOW_20_in_weekdayList196 = new BitSet(new long[]{0x0000000000000802L});
	public static final BitSet FOLLOW_11_in_weekdayList204 = new BitSet(new long[]{0x0000000002000000L});
	public static final BitSet FOLLOW_25_in_weekdayList206 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_10_in_monthdayList229 = new BitSet(new long[]{0x0000000000000100L});
	public static final BitSet FOLLOW_NUMBER_in_monthdayList231 = new BitSet(new long[]{0x0000000000001102L});
	public static final BitSet FOLLOW_12_in_monthdayList238 = new BitSet(new long[]{0x0000000000000100L});
	public static final BitSet FOLLOW_NUMBER_in_monthdayList240 = new BitSet(new long[]{0x0000000000000102L});
	public static final BitSet FOLLOW_NUMBER_in_monthdayList247 = new BitSet(new long[]{0x0000000000000002L});
}
