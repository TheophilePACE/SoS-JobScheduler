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
/* $Id: Cmd.java,v 1.6 2004/01/23 09:19:42 jz Exp $
 * 
 * Created on 20.10.2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

package sos.ftphistory.sql;

import sos.connection.SOSConnection;
import sos.util.SOSLogger;



/**
 * @author joacim
 *
 */


//import java.sql.*;


public abstract class Cmd
{

    String          table_name = "(table_name fehlt)";
    public  SOSConnection conn;
    public boolean withQuote=false;
     


    Cmd(SOSConnection conn_,SOSLogger logger_)
    {
        if( conn_       == null )  throw new RuntimeException( getClass().getName() + ": connection nicht gesetzt" );
        if( conn_.connection == null )  throw new RuntimeException( getClass().getName() + ": Datenbank nicht verbunden" );
        conn=conn_;
        
        
    }



    public void set_table_name( String name )
    {
        table_name = name;
    }


    abstract String make_cmd_() throws Exception;
    

    
    public String make_cmd()  throws Exception
    {
        return conn.normalizeStatement( make_cmd_() );
    }



    public static String quoted( String value )
    {
        return "'" + value.toString().replaceAll( "'", "''" ) + "'";
    }


    
   
}
