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
package com.sos.jitl.eventing;
/**
 *
 */
/**
 * @author KB


*
 */


// TODO <start_job> started auch jobs, die gestopped oder suspended sind. Abhilfe: start-job ausprogrammieren
// TODO Events im PostProcessing löschen komt zu spät. Im actions.xml sofort ein Rename auf die Events und dann auch die renamed events löschen
// TODO Process class erzeugen mit "30"
// TODO Events ohne Vorgänger werden im postprocessing nicht gelöscht. sollten aber.
// TODO Für die externen Events jeweils einen Job schreiben, der die setzt.
// TODO state-text für shell-scripte über eine Datei im monitor holen und dann setzen
// TODO JS Objete als Job, JC und Order erzeugen.
// TODO Jobs generieren als SSH-Jobs
// TODO JOC -> knopf, um eine Task-Queue komplett zu löschen

// TODO eine "echte" Event-Queue implementieren. Im Moment sind die "Events" nur reine Statuse.
// TODO im actions.xml Variable erlauben (z.B. die LOADID). Dann läuft ein event-handler z.B. für eine bestimmte LoadID
// in der Order ein Tooken mitgeben was dann als ID für die Variable verwendet werden kann (ähnlich ODAT z.B.).
// woher bekommt das Tooken seinen Wert? evtl. doch mit Job,JC und Order arbeiten


