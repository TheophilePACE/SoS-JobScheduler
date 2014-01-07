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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * @author Joacim Zschimmer <j@zsch.de>
 * @since 2004-08-10
 * @version 1.0
 *
 * Arguments überprüft die Gültigkeit und Vollständigkeit der Eingabeparameter
 */

public class SOSArguments
{
    static class Argument
    {
        Argument(String value )  { _value = value; }
        
        final String      _value;
        boolean           _read = false;
    };
    
    //-----------------------------------------------------------------------------------------
    
    final Map _arguments = new HashMap();

    //--------------------------------------------------------------------------------Arguments

    /**
     * Konstruktor, der die Eingabeparameter als Zeichenkettenfeld erhält
     */
    public SOSArguments( String argStr ) throws Exception
	{
    	String[] args = (" " + argStr).split(" [-]");
    	for( int i=0; i<args.length; i++) {
            if (args[i] == null || args[i].length() == 0 || args[i].trim().length() == 0) continue;
            String arg = "-" + args[i].trim();
    		// if( !arg.startsWith( "-" ) )  throw new Exception( "SOSArguments(): illegal parameter: " + arg );
            
            int equal = arg.indexOf( "=" );
            if( equal == -1 )  throw new Exception( "SOSArguments(): illegal parameter: " + arg );
            
            //if (arg.charAt(equal+1) == '\'' && arg.charAt(arg.length()-1) == '\'') 
            if ((arg.length() > equal + 1) && arg.charAt(equal+1) == '\'' && arg.charAt(arg.length()-1) == '\'')
            arg = arg.substring(0, equal + 1) + arg.substring(equal+2, arg.length()-1 );
                   
            _arguments.put( arg.substring( 0, equal + 1 ), new Argument( arg.substring( equal + 1 ) ) );   
    	}
	}
    
    /**
     * Konstruktor, der die Eingabeparameter als Zeichenkettenfeld erhält
     */
    public SOSArguments( String[] args ) throws Exception
    {
    	setArguments(args);
    	
        /*for( int i = 0; i < args.length; i++ )
        {
            String arg = args[i];
            if( !arg.startsWith( "-" ) )  throw new Exception( "SOSArguments(): illegal parameter: " + arg );
     
            int equal = arg.indexOf( "=" );
            if( equal == -1 )  throw new Exception( "SOSArguments(): illegal parameter: " + arg );
            
            //if (arg.charAt(equal+1) == '\'' && arg.charAt(arg.length()-1) == '\'') 
            if ((arg.length() > equal + 1) && arg.charAt(equal+1) == '\'' && arg.charAt(arg.length()-1) == '\'')
            arg = arg.substring(0, equal + 1) + arg.substring(equal+2, arg.length()-1 );

            _arguments.put( arg.substring( 0, equal + 1 ), new Argument( arg.substring( equal + 1 ) ) );   
        }*/
    }

    /**
     * Konstruktor, der die Eingabeparameter als Zeichenkettenfeld erhält
     * ignore -> auch parameter ohne "-" und Paare Werte (Name=Wert) kommen durch  
     */
    public SOSArguments( String[] args, boolean ignore ) throws Exception
    {
    	if(!ignore)
    		setArguments(args);
    	else {
    		for( int i = 0; i < args.length; i++ )
    		{
    			String arg = args[i];
    			//if( !arg.startsWith( "-" ) )  throw new Exception( "SOSArguments(): illegal parameter: " + arg );

    			int equal = arg.indexOf( "=" ) > -1 ? arg.indexOf( "=" ) : 0;

    			//if( equal == -1 )  throw new Exception( "SOSArguments(): illegal parameter: " + arg );

    			//if (arg.charAt(equal+1) == '\'' && arg.charAt(arg.length()-1) == '\'') 
    			if ((arg.length() > equal + 1) && arg.charAt(equal+1) == '\'' && arg.charAt(arg.length()-1) == '\'')
    				arg = arg.substring(0, equal + 1) + arg.substring(equal+2, arg.length()-1 );

    			_arguments.put( arg.substring( 0, equal + 1 ), new Argument( arg.substring( equal + 1 ) ) );   
    		}
    	}
    }

    
    //--------------------------------------------------------------------------------as_string
    
    public String as_string( String option )
    throws
        Exception
    {
        return as_string( option, null );
    }
    
    //--------------------------------------------------------------------------------as_string
    
    public String as_string( String option, String default_ )
    throws
        Exception
    {
        Argument argument = (Argument)_arguments.get( option );
        
        if( argument == null )
        {
            if( default_ == null )  throw new Exception( "SOSArguments.as_string(): parameter is missing: " + option);
            return default_;
        }
        
        argument._read = true;
        return argument._value;
    }

    //-----------------------------------------------------------------------------------as_int
    
    public int as_int( String option )
    throws
        Exception
    {
        String result = as_string( option );
        
        try
        {
            return Integer.parseInt( result );
        }
        catch( Exception x )
        {
            throw new Exception( "SOSArguments.as_int(): invalid parameter [" + option + "]: " + x.getMessage() );
        }
    }

    //-----------------------------------------------------------------------------------as_int
    
    public int as_int( String option, int default_ )
    throws
        Exception
    {
        String result = as_string( option, "" );
        if( result.equals( "" ) )  return default_;
    
        return as_int( option );
    }
    
    //-----------------------------------------------------------------------------------as_bool
    
    public boolean as_bool( String option )
    throws
        Exception
    {
        String result = as_string( option );
        
        try
        {
        	boolean ok = false;
        	boolean rc = false;
        	
        	if (!ok && result.equalsIgnoreCase("true")) { ok = true; rc = true; }
        	if (!ok && result.equalsIgnoreCase("1"))    { ok = true; rc = true; }

        	if (!ok && result.equalsIgnoreCase("false")) { ok = true; rc = false; }
        	if (!ok && result.equalsIgnoreCase("0"))     { ok = true; rc = false; }

        	if (!ok) throw new Exception("none of the values true|false was given");
        		
        	return rc;
        }
        catch( Exception x )
        {
            throw new Exception( "SOSArguments.as_bool(): invalid parameter [" + option + "]: " + x.getMessage() );
        }
    }

    //-----------------------------------------------------------------------------------as_bool
    
    public boolean as_bool( String option, boolean default_ )
    throws
        Exception
    {
        String result = as_string( option, "" );
        if( result.equals( "" ) )  return default_;
    
        return as_bool( option );
    }
    
    //---------------------------------------------------------------------------check_all_used

    public void check_all_used()
    throws
        Exception
    {
        for( Iterator it = _arguments.entrySet().iterator(); it.hasNext(); )
        {
            Map.Entry entry    = (Map.Entry)it.next();
            String    option   = (String)  entry.getKey();
            Argument  argument = (Argument)entry.getValue();
            
            if( !argument._read )  throw new Exception( "SOSArguments.check_all_used(): no parameter given for option: " + option );
        }
    }

    private void setArguments(String[] args) throws Exception{

    	for( int i = 0; i < args.length; i++ )
    	{
    		String arg = args[i];
    		if( !arg.startsWith( "-" ) )  throw new Exception( "SOSArguments(): illegal parameter: " + arg );

    		int equal = arg.indexOf( "=" );
    		if( equal == -1 )  throw new Exception( "SOSArguments(): illegal parameter: " + arg );

    		//if (arg.charAt(equal+1) == '\'' && arg.charAt(arg.length()-1) == '\'') 
    		if ((arg.length() > equal + 1) && arg.charAt(equal+1) == '\'' && arg.charAt(arg.length()-1) == '\'')
    			arg = arg.substring(0, equal + 1) + arg.substring(equal+2, arg.length()-1 );

    		_arguments.put( arg.substring( 0, equal + 1 ), new Argument( arg.substring( equal + 1 ) ) );   
    	}
    }

    
    public static void main(String[] args) {

    	try { 

    		SOSArguments arguments = new SOSArguments(args, true);

    		java.util.Iterator keys = arguments._arguments.keySet().iterator();
			while(keys.hasNext()) {
				Object key = keys.next();
				System.out.println(key +  arguments.as_string(key.toString()));					 
			}

    	} catch(Exception e) {
    		System.err.println(e.toString());
    	}

    }

	/**
	 * @return the _arguments
	 */
	public Map get_arguments() {
		return _arguments;
	} 
}

