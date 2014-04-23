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
package com.sos.jobnet.options;

import com.sos.JSHelper.Annotations.JSOptionClass;
import com.sos.JSHelper.Annotations.JSOptionDefinition;
import com.sos.JSHelper.Exceptions.JSExceptionMandatoryOptionMissing;
import com.sos.JSHelper.Options.JSOptionsClass;
import com.sos.JSHelper.Options.SOSOptionString;
import com.sos.JSHelper.Options.SOSOptionUUID;
import com.sos.jobnet.utils.TimeKeyCalculator;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;

import java.util.HashMap;

@JSOptionClass(name = "JobNetIdCalculatorOptions", description = "Options for calculating different time keys.")
public class JobNetIdCalculatorOptions extends JSOptionsClass {

	private static final long serialVersionUID = -6077566218813335864L;
	private final DateTimeFormatter format = TimeKeyCalculator.FORMAT_YYYYMMDD;
    private final DateTime defaultDateTime = new DateTime();

    public JobNetIdCalculatorOptions() {
        objParentClass = this.getClass();
    }

    public JobNetIdCalculatorOptions(com.sos.JSHelper.Listener.JSListener pobjListener) {
        this();
        this.registerMessageListener(pobjListener);
    }

    //

    public JobNetIdCalculatorOptions(HashMap<String, String> JSSettings) throws Exception {
        this();
        this.setAllOptions(JSSettings);
    }

    /**
     * LOAD_ID
     */
    @JSOptionDefinition(name = "LoadId",
            description = "The time key to calculate.",
            key = "LoadId",
            type = "SOSOptionString",
            mandatory = false)

    public SOSOptionString LoadId = new SOSOptionString(this, getClass().getSimpleName() + ".LoadId", // HashMap-Key
            "", // Titel
            defaultDateTime.toString(format), // InitValue
            defaultDateTime.toString(format), // DefaultValue
            false // isMandatory
    );

    public SOSOptionString getLoadId() {
        return LoadId;
    }

    public void setLoadId(SOSOptionString load_id) {
        this.LoadId = load_id;
    }

    /**
     * Name of the result parameter (it is always an order parameter).
     */
    @JSOptionDefinition(name = "ResultParameterName",
            description = "The name of the result parameter (it is always an order parameter).",
            key = "ResultParameterName",
            type = "SOSOptionString",
            mandatory = false)

    public SOSOptionString ResultParameterName = new SOSOptionString(this, getClass().getSimpleName() + ".ResultParameterName", // HashMap-Key
            "", // Titel
            "", // InitValue
            "", // DefaultValue
            true // isMandatory
    );

    public SOSOptionString  getResultParameterName() {
        return ResultParameterName;
    }

    public void setResultParameterName(SOSOptionString result_parameter_name) {
        this.ResultParameterName = result_parameter_name;
    }

    /**
     * Rule for calculating the new load id.
     */
    @JSOptionDefinition(name = "CalculationRule",
            description = "The rule for calculating the new load id (the + and - sign and digits allowed).",
            key = "CalculationRule",
            type = "SOSOptionString",
            mandatory = false)

    public SOSOptionString CalculationRule = new SOSOptionString(this, getClass().getSimpleName() + ".CalculationRule", // HashMap-Key
            "", // Titel
            "", // InitValue
            "", // DefaultValue
            false // isMandatory
    );

    public SOSOptionString  getCalculationRule() {
        return CalculationRule;
    }

    public void setCalculationRule(SOSOptionString calculation_rule) {
        this.CalculationRule = calculation_rule;
    }

    /**
     * LoadIdDateFormat
     */
    @JSOptionDefinition(name = "LoadIdDateFormat",
            description = "The date pattern for the LoadId (followed the org.joda.time.format.DateTimeFormat).",
            key = "LoadIdDateFormat",
            type = "SOSOptionString",
            mandatory = false)

    public SOSOptionString LoadIdDateFormat = new SOSOptionUUID(this, getClass().getSimpleName() + ".LoadIdDateFormat", // HashMap-Key
            "", // Titel
            "yyyyMMdd", // InitValue
            "yyyyMMdd", // DefaultValue
            false // isMandatory
    );

    public SOSOptionString getLoadIdDateFormat() {
        return LoadIdDateFormat;
    }

    public void setLoadIdDateFormat(SOSOptionString format) {
        this.LoadIdDateFormat = format;
    }

    /**
     * ResultDateFormat
     */
    @JSOptionDefinition(name = "ResultDateFormat",
            description = "The date pattern for the result field (followed the org.joda.time.format.DateTimeFormat).",
            key = "ResultDateFormat",
            type = "SOSOptionString",
            mandatory = false)

    public SOSOptionString ResultDateFormat = new SOSOptionUUID(this, getClass().getSimpleName() + ".ResultDateFormat", // HashMap-Key
            "", // Titel
            "", // InitValue
            "", // DefaultValue
            false // isMandatory
    );

    public SOSOptionString getResultDateFormat() {
        return ResultDateFormat;
    }

    public void setResultDateFormat(SOSOptionString format) {
        this.ResultDateFormat = format;
    }

    /**
     * LoadIdDurationType
     */
    @JSOptionDefinition(name = "LoadIdDurationType",
            description = "The duration type corresponding to LoadIdDateFormat (followed the org.joda.time.DurationFieldType).",
            key = "LoadIdDurationType",
            type = "SOSOptionString",
            mandatory = false)

    public SOSOptionString LoadIdDurationType = new SOSOptionUUID(this, getClass().getSimpleName() + ".LoadIdDurationType", // HashMap-Key
            "", // Titel
            "days", // InitValue
            "days", // DefaultValue
            false // isMandatory
    );

    public SOSOptionString getLoadIdDurationType() {
        return LoadIdDurationType;
    }

    public void setLoadIdDurationType(SOSOptionString type) {
        this.LoadIdDurationType = type;
    }

    /**
     * \brief setAllOptions - uebernimmt die Optionenwerte aus der HashMap
     * 
     * \details In der als Parameter anzugebenden HashMap sind Schluessel (Name)
     * und Wert der jeweiligen Option als Paar angegeben. Ein Beispiel fuer den
     * Aufbau einer solchen HashMap findet sich in der Beschreibung dieser
     * Klasse (\ref TestData "setJobSchedulerSSHJobOptions"). In dieser Routine
     * werden die Schluessel analysiert und, falls gefunden, werden die
     * dazugehoerigen Werte den Properties dieser Klasse zugewiesen.
     *
     * Nicht bekannte Schluessel werden ignoriert.
     *
     * \see JSOptionsClass::getOrderRecord
     *
     * @param pobjJSSettings
     * @throws Exception
     */
    public void setAllOptions(HashMap<String, String> pobjJSSettings) throws Exception {
        @SuppressWarnings("unused")
        final String conMethodName = getClass().getName() + "::setAllOptions";
        flgSetAllOptions = true;
        objSettings = pobjJSSettings;
        super.Settings(objSettings);
        super.setAllOptions(pobjJSSettings);
        flgSetAllOptions = false;
    } // public void setAllOptions (HashMap <String, String> JSSettings)

    /**
     * \brief CheckMandatory - prueft alle Muss-Optionen auf Werte
     *
     * \details
     * @throws Exception
     *
     * @throws Exception
     * - wird ausgeloest, wenn eine mandatory-Option keinen Wert hat
     */
    @Override
    public void CheckMandatory() throws JSExceptionMandatoryOptionMissing //
            , Exception {
        try {
            super.CheckMandatory();
        }
        catch (Exception e) {
            throw new JSExceptionMandatoryOptionMissing(e.toString());
        }
    } // public void CheckMandatory ()

    /**
     *
     * \brief CommandLineArgs - uernehmen der Options/Settings aus der Kommandozeile
     *
     * \details Die in der Kommandozeile beim Starten der Applikation
     * angegebenen Parameter werden hier in die HashMap uebertragen und danach
     * den Optionen als Wert zugewiesen.
     *
     * \return void
     *
     * @param pstrArgs
     * @throws Exception
     */
    @Override
    public void CommandLineArgs(String[] pstrArgs) throws Exception {
        super.CommandLineArgs(pstrArgs);
        this.setAllOptions(super.objSettings);
    }

}
