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
package sos.util;

import java.util.*;


/*  This class is used to combine the key-value lookup capabilities of a
	Hashtable along with order preserving capabilities of a Vector.
	Iterator on a Set of Hashtable keys, obtained by keySet() or an
	Enumeration obtained by keys() method, both are not guaranteed to
	iterate in the same order as the values were put in.

 	This class behaves like a queue, (FIFO). Objects are returned in the
 	same order they were put in.

 	@author Animesh Srivastava
 */

public class SOSOrderedHashtable extends Hashtable {

    //member variables
    private Vector mSerialOrder;
    private Hashtable mHashtable;


	/** Public Constructor */
	public SOSOrderedHashtable()
	{
		this.mSerialOrder = new Vector();
		this.mHashtable = new Hashtable();
	}


    /** Clears this OrderedHashtable so that it has no keys.
    *
    * @exception UnsupportedOperationException - clear is not supported by
    			the underlying Interface java.util.Map.
	*/
    synchronized public void clear() throws UnsupportedOperationException
    {
        this.mHashtable.clear();
        this.mSerialOrder.clear();
    }


	/** Removes the key (and its corresponding value) from this OrderedHashtable.
	*	Does nothing if key is not in the OrderedHashtable.
	*
	* @param key - the key that needs to be removed.
	* @return the value to which the key had been mapped in this OrderedHashtable,
	*			or null if the key did not have a mapping.
	*/
	synchronized public Object remove(Object key)
	{
		this.mSerialOrder.remove(key);
		return this.mHashtable.remove(key);
	}


	/** Maps the specified key to the specified value in this OrderedHashtable.
	*	Neither the key nor the value can be null. If the key already exists
	*	then the ordering is not changed. If it does not exists then it is added
	*	at the 	end of the OrderedHastable.
	*
	* @param key - the key.
	* @param value - the value.
	* @exception NullPointerException, if the key or value is null.
	* @return the previous value of the specified key in this hashtable, or
	*			null if it did not have one.
	*
	*/
	synchronized public Object put(Object key,Object value) throws NullPointerException
	{
		Object toReturn = this.mHashtable.put(key,value);
		if(toReturn == null)
			this.mSerialOrder.add(key);
		return toReturn;
	}

	/** Returns an Iterator to iterate through the keys of the OrderedHashtable.
	*	Iteration will occur in the same order as the keys were put in into the
	*	OrderedHashtable.
	*
	*   The remove() method of Iterator interface is optional in jdk1.3 and hence
	*	not implemented.
	*/
	public Iterator iterateKeys() {
		return new Enumerator();
	}


	/** Returns an Enumeration to enumerate through the keys of the OrderedHashtable.
	*	Enumeration will occur in the same order as the keys were put in into the
	*	OrderedHashtable.
	*
	*/
	public Enumeration enumerateKeys() {
		return new Enumerator();
	}


	/** Tests if the specified object is a key in this OrderedHashtable */
    public boolean containsKey(Object key)
    {
        return this.mHashtable.containsKey(key);
    }


	/** Returns true if this OrderedHashtable maps one or more keys to this value */
    public boolean containsValue(Object value)
    {
        return this.mHashtable.containsValue(value);
    }

	/** Returns the value to which the specified key is mapped in this OrderedHashtable,
	*	or null if the key is not mapped to any value.
	*/
	public Object get(Object key)
	{
		return this.mHashtable.get(key);
	}

	/** Tests if this OrderedHashtable maps no keys to values. */
    public boolean isEmpty()
    {
        return this.mHashtable.isEmpty();
    }

	/** Returns the number of keys in this OrderedHashtable. 	*/
	public int size()
	{
		return this.mHashtable.size();
	}


    /**
     * Returns the hash code value for this Map.
     */
    public synchronized int hashCode()
    {
		return this.mHashtable.hashCode();
	}


	/** Returns a string representation of the OrderedHashtable. */
	public String toString()
	{
		StringBuffer s = new StringBuffer();
		s.append("{ ");
		Object key=null;
		int i=0;
		while(i<this.mSerialOrder.size()) {
			key = this.mSerialOrder.elementAt(i++);
			s.append(key.toString());
			s.append("=");
			s.append(this.mHashtable.get(key).toString());
			s.append("; ");
		}
		s.append(" }");
		return s.toString();
	}


	//inner class,
    private class Enumerator implements Enumeration, Iterator
    {
		int COUNT = mSerialOrder.size();		//number of elements in the Vector
		int SERIAL = 0;							//keep track of the current element

		public boolean hasMoreElements() {
			return SERIAL < COUNT;
		}

		public Object nextElement() {
			synchronized (SOSOrderedHashtable.this) {
				if((COUNT==0)||(SERIAL==COUNT))
					throw new NoSuchElementException("OrderedHashtable Enumerator");
				return mSerialOrder.elementAt(SERIAL++);
			}
		}

		public boolean hasNext() {
			return hasMoreElements();
		}

		public Object next() {
			return nextElement();
		}

		//optional in jdk1.3
		public void remove() {
		}
    }

}
