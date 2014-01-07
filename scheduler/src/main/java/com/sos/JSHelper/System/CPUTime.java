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
package com.sos.JSHelper.System;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public class CPUTime   {
	@SuppressWarnings("unused")
	private final String conClassName = "CPUTime";
	
	@SuppressWarnings("unused")
	private long starttime, stoptime, timeused;
	private ThreadMXBean tb = null;
	
	public CPUTime ()
	{
/** ---------------------------------------------------------------------------
<method type="smcw" version="1.0">
<name></name>
<title>CPUTime</title>
<description>
<para>
CPUTime
</para>
</description>
<params>
</params>
<keywords>
	<keyword>CPUTime</keyword>
</keywords>
<categories>
<category>SystemManagement</category>
</categories>
</method>
---------------------------------------------------------------------------- */
		tb = ManagementFactory.getThreadMXBean();
		StartTimer();
		
	} // public CPUTime
	
	public void StartTimer () {
		starttime = tb.getCurrentThreadCpuTime();
	}
	
	public void StopTimer () {
		stoptime = tb.getCurrentThreadCpuTime();
	}
	
	public long getStopTime () {
		return stoptime;
	}
	
	public long TimeUsed () {
		long lngTimeDiff = 0;
		long lngT = 0;
		
		lngT = tb.getCurrentThreadCpuTime();
		
		lngTimeDiff = lngT - starttime;
		
		return lngTimeDiff;
		
		
	}
	
	public String toString () {
		String strT;
		
		strT = String.format("CPU-time used %1$8.4f ms", (double) this.TimeUsed()/1000000);
		return strT;
	}
} // public class CPUTime
