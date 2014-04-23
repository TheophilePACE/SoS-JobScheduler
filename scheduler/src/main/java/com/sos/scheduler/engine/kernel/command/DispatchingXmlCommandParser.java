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

import com.google.common.collect.ImmutableMap;
import com.sos.scheduler.engine.kernel.scheduler.SchedulerException;
import org.w3c.dom.Element;
import static java.util.Arrays.asList;


class DispatchingXmlCommandParser implements CommandXmlParser {
    private final ImmutableMap<String,SingleCommandXmlParser> singleCommandParsers;
    private final ImmutableMap<String,PrefixCommandXmlParser> prefixCommandParsers;


    DispatchingXmlCommandParser(Iterable<CommandXmlParser> parsers) {
        ImmutableMap.Builder<String,SingleCommandXmlParser> builder = new ImmutableMap.Builder<String,SingleCommandXmlParser>();
        ImmutableMap.Builder<String,PrefixCommandXmlParser> prefixBuilder = new ImmutableMap.Builder<String,PrefixCommandXmlParser>();
        for (CommandXmlParser p: parsers) {
            if (p instanceof SingleCommandXmlParser) {
                SingleCommandXmlParser pp = (SingleCommandXmlParser)p;
                builder.put(pp.getCommandName(), pp);
            } else
            if (p instanceof PrefixCommandXmlParser) {
                PrefixCommandXmlParser pp = (PrefixCommandXmlParser)p;
                prefixBuilder.put(pp.getCommandPrefix(), pp);
            } else
                throw new SchedulerException("Unknown CommandParser " + p);
        }
        singleCommandParsers = builder.build();
        prefixCommandParsers = prefixBuilder.build();
    }


    DispatchingXmlCommandParser(CommandXmlParser... parsers) {
        this(asList(parsers));
    }

    
    @Override public final Command parse(Element element) {
        return commandParser(element.getNodeName()).parse(element);
    }


    private CommandXmlParser commandParser(String name) {
        CommandXmlParser result = null;
        int t = name.indexOf(PrefixCommandXmlParser.prefixTerminator);
        if (t >= 0)  result = prefixCommandParsers.get(name.substring(0, t + 1));
        if (result == null)  result = singleCommandParsers.get(name);
        if (result == null)  throw new UnknownCommandException("<" + name + ">");
        return result;
    }
}
