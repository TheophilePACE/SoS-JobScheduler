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
//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3-
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2011.01.17 at 03:00:56 PM MEZ
//


package com.sos.scheduler.model.objects;

import java.math.BigInteger;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for process_class complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="process_class">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="name" type="{}Name" />
 *       &lt;attribute name="max_processes" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *       &lt;attribute name="remote_scheduler" type="{}String" />
 *       &lt;attribute name="spooler_id" type="{}Name" />
 *       &lt;attribute name="replace" type="{}Yes_no" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "process_class")
@XmlRootElement(name = "process_class")

public class ProcessClass extends JSObjBase {

    @XmlAttribute(name = "name")
    protected String name;
    @XmlAttribute(name = "max_processes")
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger maxProcesses;
    @XmlAttribute(name = "remote_scheduler")
    protected String remoteScheduler;
    @XmlAttribute(name = "spooler_id")
    protected String spoolerId;
    @XmlAttribute(name = "replace")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String replace;

    /**
     * Gets the value of the name property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setName(final String value) {
        name = value;
    }

    /**
     * Gets the value of the maxProcesses property.
     *
     * @return
     *     possible object is
     *     {@link BigInteger }
     *
     */
    public BigInteger getMaxProcesses() {
        return maxProcesses;
    }

    /**
     * Sets the value of the maxProcesses property.
     *
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *
     */
    public void setMaxProcesses(final BigInteger value) {
        maxProcesses = value;
    }

    /**
     * Gets the value of the remoteScheduler property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getRemoteScheduler() {
        return remoteScheduler;
    }

    /**
     * Sets the value of the remoteScheduler property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setRemoteScheduler(final String value) {
        remoteScheduler = value;
    }

    /**
     * Gets the value of the spoolerId property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getSpoolerId() {
        return spoolerId;
    }

    /**
     * Sets the value of the spoolerId property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setSpoolerId(final String value) {
        spoolerId = value;
    }

    /**
     * Gets the value of the replace property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getReplace() {
        return replace;
    }

    /**
     * Sets the value of the replace property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setReplace(final String value) {
        replace = value;
    }

}