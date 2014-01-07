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

class DispatchingResultXmlizer implements ResultXmlizer {
    private final ImmutableMap<Class<?>,ResultXmlizer> resultXmlizers;

    DispatchingResultXmlizer(Iterable<ResultXmlizer> xmlizers) {
        resultXmlizers = mapOfXmlizers(xmlizers);
    }

    private static ImmutableMap<Class<?>,ResultXmlizer> mapOfXmlizers(Iterable<ResultXmlizer> rx) {
        ImmutableMap.Builder<Class<?>,ResultXmlizer> b = new ImmutableMap.Builder<Class<?>,ResultXmlizer>();
        for (ResultXmlizer r: rx) b.put(r.getResultClass(), r);
        return b.build();
    }

    @Override public final Class<? extends Result> getResultClass() {
        return Result.class;   // Dummy
    }

    @Override public final Element toElement(Result r) {
        return resultXmlizer(r.getClass()).toElement(r);
    }

    private ResultXmlizer resultXmlizer(Class<?> clas) {
        ResultXmlizer result = resultXmlizers.get(clas);
        if (result == null)  throw new SchedulerException("No " + ResultXmlizer.class.getName() + " for " + clas.getName());
        return result;
    }
}
