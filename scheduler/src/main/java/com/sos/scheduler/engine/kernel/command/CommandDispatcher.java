/**
 * Copyright (C) 2014 BigLoupe http://bigloupe.github.io/SoS-JobScheduler/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */
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
package com.sos.scheduler.engine.kernel.command;

import com.sos.scheduler.engine.kernel.scheduler.SchedulerException;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

import static com.sos.scheduler.engine.common.xml.XmlUtils.loadXml;
import static com.sos.scheduler.engine.common.xml.XmlUtils.toXml;


public class CommandDispatcher {
    private final DispatchingCommandExecutor executor;
    private final DispatchingXmlCommandParser parser;
    private final DispatchingResultXmlizer resultXmlizer;


//    public CommandDispatcher(Iterable<CommandSuite> suites) {
//        CommandSuite combinedSuite = CommandSuite.of(suites);
//        executor = new DispatchingCommandExecutor(combinedSuite.getExecutors());
//        parser = new DispatchingXmlCommandParser(combinedSuite.getParsers());
//        resultXmlizer = new DispatchingResultXmlizer(combinedSuite.getResultXmlizer());
//    }


    public CommandDispatcher(Iterable<CommandHandler> handlers) {
        List<CommandExecutor> executors = new ArrayList<CommandExecutor>();
        List<CommandXmlParser> parsers = new ArrayList<CommandXmlParser>();
        List<ResultXmlizer> xmlizers = new ArrayList<ResultXmlizer>();
        
        for (CommandHandler handler: handlers) {
            if (handler instanceof CommandExecutor)
                executors.add((CommandExecutor)handler);
            else
            if (handler instanceof CommandXmlParser)
                parsers.add((CommandXmlParser)handler);
            else
            if (handler instanceof ResultXmlizer)
                xmlizers.add((ResultXmlizer)handler);
            else
                throw new SchedulerException("CommandDispatcher " + handler);
        }
        executor = new DispatchingCommandExecutor(executors);
        parser = new DispatchingXmlCommandParser(parsers);
        resultXmlizer = new DispatchingResultXmlizer(xmlizers);
    }


    public final String executeXml(String xml) {
        Command command = parse(loadXml(xml).getDocumentElement());
        Result result = execute(command);
        return toXml(elementOfResult(result));
    }


    public final Result execute(Command c) {
        return executor.execute(c);
    }


    public final Command parse(Element e) {
        return parser.parse(e);
    }


    public final Element elementOfResult(Result r) {
        return resultXmlizer.toElement(r);
    }
}
