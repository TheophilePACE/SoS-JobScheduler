/********************************************************* begin of preamble
**
** Copyright (C) 2003-2012 Software- und Organisations-Service GmbH. 
** All rights reserved.
**
** This file may be used under the terms of either the 
**
**   GNU General Public License version 2.0 (GPL)
**
**   as published by the Free Software Foundation
**   http://www.gnu.org/licenses/gpl-2.0.txt and appearing in the file
**   LICENSE.GPL included in the packaging of this file. 
**
** or the
**  
**   Agreement for Purchase and Licensing
**
**   as offered by Software- und Organisations-Service GmbH
**   in the respective terms of supply that ship with this file.
**
** THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
** IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
** THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
** PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS
** BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
** CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
** SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
** INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
** CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
** ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
** POSSIBILITY OF SUCH DAMAGE.
********************************************************** end of preamble*/
/**
 *
 */
package com.sos.localization;

import java.util.Locale;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.sos.JSHelper.Exceptions.JobSchedulerException;

/**
 * @author JS
 *
 */
public class SOSMsg {

	private static final String	conPropertyExtensionF1				= ".f1";
	public static final String	conEnvironmentVariableSOS_LOCALE	= "SOS_LOCALE";
	public static final String	conPropertyExtensionTOOLTIP			= ".tooltip";
	public static final String	conPropertyExtensionLABEL			= ".label";
	public static int			VerbosityLevel						= 0;

	public enum enuMsgTypes {
		undefined, error, info, fatal, debug, warning, text, trace, label;
	}

	private final String	conClassName			= "SOSMsg";
	protected static Logger	logger					= Logger.getLogger(SOSMsg.class);

	protected String		strMessageCode			= null;
	protected Messages		Messages				= null;
	protected boolean		flgFullMessageReported	= false;
	public static boolean	flgShowFullMessageText	= false;
	protected String		strLastMsgKey			= "";
	protected String		strControlName			= "";
	protected String		strControlParent		= "";

	protected int			intVerbosityLevel		= 0;
	protected enuMsgTypes	enuMsgType				= enuMsgTypes.undefined;
	protected int			curVerbosityLevel		= 0;

	/**
	 *
	 */
	public SOSMsg(final String pstrMessageCode) {
		strMessageCode = pstrMessageCode;
		setMsgType();
	}

	public SOSMsg(final String pstrMessageCode, final int pintVerbosityLevel) {
		this(pstrMessageCode);
		intVerbosityLevel = pintVerbosityLevel;
	}

	public void toLog() {
		Write2Log(this.get());
	}

	private void setMsgType() {
		enuMsgType = enuMsgTypes.undefined;

		if (strMessageCode.indexOf("_E_") != -1) {
			enuMsgType = enuMsgTypes.error;
		}
		else
			if (strMessageCode.indexOf("_F_") != -1) {
				enuMsgType = enuMsgTypes.fatal;
			}
			else
				if (strMessageCode.indexOf("_D_") != -1) {
					enuMsgType = enuMsgTypes.debug;
				}
				else
					if (strMessageCode.indexOf("_I_") != -1) {
						enuMsgType = enuMsgTypes.info;
					}
					else
						if (strMessageCode.indexOf("_W_") != -1) {
							enuMsgType = enuMsgTypes.warning;
						}
						else
							if (strMessageCode.indexOf("_T_") != -1) {
								enuMsgType = enuMsgTypes.text;
							}
							else {
								if (strMessageCode.indexOf("_L_") != -1) {
									enuMsgType = enuMsgTypes.label;
								}
								else {
									if (strMessageCode.indexOf("_R_") != -1) {
										enuMsgType = enuMsgTypes.trace;
									}
								}
							}

	}

	public void toLog(final Object... pstrArgs) {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::params";

		String strT = Messages.getMsg(strMessageCode, pstrArgs);
		strT = getFullMessage(strT);
		Write2Log(strT);

	} // private String params

	private void Write2Log(final String pstrLogMsg) {

		switch (enuMsgType) {
			case error:
				logger.error(pstrLogMsg);
				break;

			case fatal:
				logger.fatal(pstrLogMsg);
				throw new JobSchedulerException(pstrLogMsg);

			case warning:
				if (logger.isEnabledFor(Level.WARN)) {
					logger.warn(pstrLogMsg);
				}
				break;

			case debug:
				if (logger.isDebugEnabled() == true) {
					checkVerbosityLevel();
					if (intVerbosityLevel <= curVerbosityLevel) {
						logger.debug(pstrLogMsg);
					}
				}
				break;

			case info:
			case text:
			case undefined:
				logger.info(pstrLogMsg);
				break;

			case trace:
				logger.trace(pstrLogMsg);
				break;

			default:
				logger.info(pstrLogMsg);
				break;
		}
	}

	private String getLabel(final String pstrDefaultValue) {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::label";
		String key = strMessageCode.trim().replaceAll(" ", "");
		try {
			strLastMsgKey = key;
			String msg = Messages.getLabel(key + conPropertyExtensionLABEL);
			if (msg == null) {
				msg = Messages.getLabel(key + ".Label");
				if (msg == null) {
					msg = Messages.getLabel(key.toLowerCase() + conPropertyExtensionLABEL);
					if (msg == null) {
						msg = Messages.getLabel(key.toLowerCase() + ".Label");
						if (msg == null) {
							msg = Messages.getLabel(key);
						}
					}
				}
			}
			String strT = strMessageCode;
			if (msg == null) {
				strT = pstrDefaultValue;
			}
			else {
				strT = msg;
			}
			return strT;
		}
		catch (Exception e) {
			return strMessageCode;
		}
	}

	public String label() {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::label";
		String key = strMessageCode.trim().replaceAll(" ", "");
		return getLabel(key);
	}

	public String caption() {
		return getLabel("");
	}

	public String tooltip() {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::tooltip";
		try {
			strLastMsgKey = strMessageCode;
			String msg = Messages.getTooltip(strMessageCode + conPropertyExtensionTOOLTIP);
			if (msg == null) {
				msg = Messages.getTooltip("tooltip." + strMessageCode.toLowerCase());
				if (msg == null) {
					msg = Messages.getTooltip(strMessageCode.toLowerCase() + conPropertyExtensionTOOLTIP);
					if (msg == null) {
						msg = Messages.getTooltip("tooltip." + strMessageCode);
						if (msg == null) {
							msg = Messages.getTooltip(strMessageCode + ".Tooltip");
							if (msg == null) {
								msg = Messages.getTooltip("Tooltip." + strMessageCode.toLowerCase());
								if (msg == null) {
									msg = Messages.getTooltip(strMessageCode.toLowerCase() + ".Tooltip");
									if (msg == null) {
										msg = Messages.getTooltip("Tooltip." + strMessageCode);
									}
								}
							}
						}
					}
				}
			}
			if (msg != null) {
				if (msg.trim().equals("none")) {
					msg = strMessageCode + ": sorry, no tooltip available yet";
				}
			}
			String strLoc = strControlParent + "." + strControlName;
			if (strLoc.equals(".") == false) {
				msg = strLoc + " -> " + msg;
			}
			return msg;
		}
		catch (Exception e) {
			return strMessageCode;
		}
	}

	public String getF1() {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::getF1";
		String key = strMessageCode.trim().replaceAll(" ", "");
		try {
			String msg = Messages.getLabel(key + ".F1");
			if (msg == null) {
				msg = Messages.getLabel(key.toLowerCase() + ".F1");
				if (msg == null) {
					msg = Messages.getLabel(key + conPropertyExtensionF1);
					if (msg == null) {
						msg = Messages.getLabel(key.toLowerCase() + conPropertyExtensionF1);
						if (msg == null) {
							msg = Messages.getLabel(key);
						}
					}
				}
			}
			return msg != null && !msg.equals("") ? msg : key;
		}
		catch (Exception e) {
			return strMessageCode;
		}
	}

	public String get() {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::get";
		String strT = "";

		if (flgFullMessageReported == false) {
			strT = getFullMessage();
		}
		else {
			strT = Messages.getMsg(strMessageCode);
		}
		return strT;

	} // private String get

	public String get(final Exception pobjEx) {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::get";
		String strT = "";
		String strM = pobjEx.getLocalizedMessage();
		if (flgFullMessageReported == false) {
			strT = getFullMessage();
		}
		else {
			strT = Messages.getMsg(strMessageCode, strM);
		}
		return strT;

	} // private String get

	public String getFullMessage() {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::getFullMessage";

		String strMsgText = Messages.getMsg(strMessageCode);
		String strT = getFullMessage(strMsgText);
		return strT;
	} // private String get

	private String getFullMessage(final String pstrMsgText) {
		String strT = pstrMsgText;
		if (flgShowFullMessageText == true && flgFullMessageReported == false) {

			switch (enuMsgType) {
				case error:
				case fatal:
				case warning:
					String strMsgAction = Messages.getLabel(strMessageCode + ".action");
					String strMsgDesc = Messages.getLabel(strMessageCode + ".description");
					if (strMsgDesc == null) {
						strMsgDesc = "*** no detailed description available ***";
					}
					String strMsgReason = Messages.getLabel(strMessageCode + ".reason");
					if (strMsgReason == null) {
						strMsgReason = "*** no detailed explanation available ***";
					}

					strT = pstrMsgText + "\n\n" + "DESCRIPTION" + "\n" + strMsgDesc + "\n\n" + "REASON" + "\n" + strMsgReason;
					flgFullMessageReported = true;
					break;

				default:
					break;
			}
		}
		return strT;
	}

	/**
	 * returns the message belonging to the message code and accepts variable number of parameter
	 * @param pstrArgs
	 * @return
	 */
	public String get(final Object... pstrArgs) {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::get";

		String strT = Messages.getMsg(strMessageCode, pstrArgs);
		strT = getFullMessage(strT);
		return strT;

	} // private String get

	public String params_(final Object... pstrArgs) {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::params";

		return Messages.getMsg(strMessageCode, pstrArgs);

	} // private String params

	public String params(final Object... pstrArgs) {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::params";

		String msg = Messages.getLabel(strMessageCode + conPropertyExtensionLABEL);
		if (msg == null) {
			msg = Messages.getLabel(strMessageCode.toLowerCase() + conPropertyExtensionLABEL);
		}
		if (msg != null) {
			try {
				msg = String.format(msg, pstrArgs);
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			msg = Messages.getMsg(strMessageCode, pstrArgs);
		}

		return msg;

	} // public String params

	public String params(final String[] pstrArgs) {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::params";

		String msg = Messages.getLabel(strMessageCode + conPropertyExtensionLABEL);
		if (msg == null) {
			msg = Messages.getLabel(strMessageCode.toLowerCase() + conPropertyExtensionLABEL);
		}

		String strT = "";
		for (String string : pstrArgs) {
			strT += " " + string;
		}
		if (msg != null) {
			msg = String.format(msg, strT);
		}
		else {
			msg = Messages.getMsg(strMessageCode, strT);
		}

		return msg;

	} // private String params

	public String paramsNoKey(final Object... pstrArgs) {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::paramsNoKey";

		return String.format(Messages.getLabel(strMessageCode), pstrArgs);

	} // private String paramsNoKey

	/**
	 * set the resourcebundlename (e.g. the .properties).
	 *
	 * should be called in the constructor of the project specific msg class.
	 *
	 * @param pstrResourceBundleName
	 */
	protected void setMessageResource(final String pstrResourceBundleName) {
		String strSOSLocale = System.getenv(conEnvironmentVariableSOS_LOCALE);
		if (strSOSLocale == null) {
			Messages = new Messages(pstrResourceBundleName, Locale.getDefault());
		}
		else {
			Messages = new Messages(pstrResourceBundleName, new Locale(strSOSLocale));
		}
	}

	protected void checkVerbosityLevel() {

	}
}
