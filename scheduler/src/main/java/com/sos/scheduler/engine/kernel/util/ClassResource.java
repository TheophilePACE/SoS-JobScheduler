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
package com.sos.scheduler.engine.kernel.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

public class ClassResource {
    private final Class<?> clas;
    private final String subPath;


    public ClassResource(Class<?> c, String path) {
        clas = c;
        subPath = path;
    }

    public final InputStream getInputStream() {
        try {
            //clas.getClassLoader().getResourceAsStream(getPath());
            return url().openStream();
        } catch (IOException x) {
            throw new RuntimeException("Java ressource '" + url() + "' is missing", x);
        }
    }

//    private final String getPath() {
//        return clas.getPackage().getName().replace(".", "/") + "/" + subPath;
//    }

    public final URL url() {
        return clas.getResource(subPath);
    }

    public final ImmutableMap<String,String> properties() {
        try {
            Properties properties = new Properties();
            properties.load(getInputStream());
            return Maps.fromProperties(properties);
        } catch (IOException e) { throw new RuntimeException(e); }
    }

    @Override public final String toString() {
        return "Java resource " + url();
    }

    public static URL resourceUrl(Class<?> c, String subPath) {
        return new ClassResource(c, subPath).url();
    }
}
