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
/*
 * JobSchedulerJavaObject.java
 * Created on 07.09.2007
 * 
 */
package sos.scheduler.misc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.commons.codec.binary.Base64;

import sos.spooler.Variable_set;

/**
 * This class can be used to store Java Objects in Job Scheduler
 * Variables.<br>
 * The objects need to be serializable.<br>
 * Objects can be stored in task, order or global Scheduler variables. Note
 * that global Scheduler variables are not persistent.
 *
 * @author Andreas Liebert 
 */
public class SchedulerJavaObject {

	public static void putObject(Object obj, Variable_set set, String name) throws Exception{
		try{
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(obj);
			oos.close();
			String encoded = new String(Base64.encodeBase64(bos.toByteArray()));
			set.set_var(name, encoded);
		} catch(Exception e){
			throw new Exception("Error occured storing object in variale: "+e);
		}
	}
	
	public static Object getObject(Variable_set set, String name) throws Exception{
		try{
		Object schedulerObject;
		String encoded = set.value(name);
		if(encoded==null || encoded.length()==0) return null;
		byte[] serializedObject = Base64.decodeBase64(encoded.getBytes());
		ByteArrayInputStream bis = new ByteArrayInputStream(serializedObject);
		ObjectInputStream ois = new ObjectInputStream(bis);
		schedulerObject = ois.readObject();
		ois.close();
		return schedulerObject;
		} catch (Exception e){
			throw new Exception("Error occured reading object from variale: "+e);
		}
	}
}
