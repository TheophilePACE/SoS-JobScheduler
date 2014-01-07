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
package sos.scheduler.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class DbmsOutput {
/*
 * our instance variables. It is always best to 
 * use callable or prepared statements and prepare (parse)
 * them once per program execution, rather then one per 
 * execution in the program.  The cost of reparsing is 
 * very high.  Also -- make sure to use BIND VARIABLES!
 *
 * we use three statments in this class. One to enable 
 * dbms_output - equivalent to SET SERVEROUTPUT on in SQL*PLUS.
 * another to disable it -- like SET SERVEROUTPUT OFF.
 * the last is to "dump" or display the results from dbms_output
 * using system.out
 *
 */
	private final CallableStatement	enable_stmt;
	private final CallableStatement	disable_stmt;
	private final CallableStatement	show_stmt;

/* 
 * our constructor simply prepares the three
 * statements we plan on executing. 
 *
 * the statement we prepare for SHOW is a block of 
 * code to return a String of dbms_output output.  Normally, 
 * you might bind to a PLSQL table type but the jdbc drivers
 * don't support PLSQL table types -- hence we get the output
 * and concatenate it into a string.  We will retrieve at least
 * one line of output -- so we may exceed your MAXBYTES parameter
 * below. If you set MAXBYTES to 10 and the first line is 100 
 * bytes long, you will get the 100 bytes.  MAXBYTES will stop us
 * from getting yet another line but it will not chunk up a line.
 *
 */
	public DbmsOutput(final Connection conn) throws SQLException {
		enable_stmt = conn.prepareCall("begin dbms_output.enable(:1); end;");
		disable_stmt = conn.prepareCall("begin dbms_output.disable; end;");

		show_stmt = conn.prepareCall("declare " + "    l_line varchar2(255); " + "    l_done number; " + "    l_buffer long; " + "begin " + "  loop "
				+ "    exit when length(l_buffer)+255 > :maxbytes OR l_done = 1; " + "    dbms_output.get_line( l_line, l_done ); "
				+ "    l_buffer := l_buffer || l_line || chr(10); " + "  end loop; " + " :done := l_done; " + " :buffer := l_buffer; " + "end;");
	}

/*
 * enable simply sets your size and executes
 * the dbms_output.enable call
 *
 */
	public void enable(final int size) throws SQLException {
		enable_stmt.setInt(1, size);
		enable_stmt.executeUpdate();
	}

/*
 * disable only has to execute the dbms_output.disable call
 */
	public void disable() throws SQLException {
		disable_stmt.executeUpdate();
	}

/*
 * show does most of the work.  It loops over
 * all of the dbms_output data, fetching it in this
 * case 32,000 bytes at a time (give or take 255 bytes).
 * It will print this output on stdout by default (just
 * reset what System.out is to change or redirect this 
 * output).
 */

	public String getOutput() throws SQLException {
		int done = 0;
		StringBuffer sbfSQLOutput = new StringBuffer();
		
		show_stmt.registerOutParameter(2, java.sql.Types.INTEGER);
		show_stmt.registerOutParameter(3, java.sql.Types.VARCHAR);

		for (;;) {
			show_stmt.setInt(1, 32000);
			show_stmt.executeUpdate();
			sbfSQLOutput.append(show_stmt.getString(3));
			System.out.print(show_stmt.getString(3));
			if ((done = show_stmt.getInt(2)) == 1)
				break;
		}
		return sbfSQLOutput.toString();
	}

/* 
 * close closes the callable statements associated with
 * the DbmsOutput class. Call this if you allocate a DbmsOutput
 * statement on the stack and it is going to go out of scope -- 
 * just as you would with any callable statement, result set
 * and so on.
 */
	public void close() throws SQLException {
		enable_stmt.close();
		disable_stmt.close();
		show_stmt.close();
	}
}
