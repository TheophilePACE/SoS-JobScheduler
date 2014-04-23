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
package com.sos.hibernate.interfaces;

import com.sos.JSHelper.Options.SOSOptionBoolean;
import com.sos.JSHelper.Options.SOSOptionDBDriver;
import com.sos.JSHelper.Options.SOSOptionInFileName;
import com.sos.JSHelper.Options.SOSOptionJdbcUrl;
import com.sos.JSHelper.Options.SOSOptionPassword;
import com.sos.JSHelper.Options.SOSOptionString;

public interface IHibernateOptions {

	/**
	 * \brief gethibernate_connection_autocommit : 
	 * 
	 * \details
	 * 
	 *
	 * \return 
	 *
	 */
	public abstract SOSOptionBoolean gethibernate_connection_autocommit();

	/**
	 * \brief sethibernate_connection_autocommit : 
	 * 
	 * \details
	 * 
	 *
	 * @param hibernate_connection_autocommit : 
	 */
	public abstract void sethibernate_connection_autocommit(
			SOSOptionBoolean p_hibernate_connection_autocommit);

	/**
	 * \brief gethibernate_connection_config_file : 
	 * 
	 * \details
	 * Hibernate configuration file of the database connection
	 *
	 * \return 
	 *
	 */
	public abstract SOSOptionInFileName gethibernate_connection_config_file();

	/**
	 * \brief sethibernate_connection_config_file : 
	 * 
	 * \details
	 * Hibernate configuration file of the database connection
	 *
	 * @param hibernate_connection_config_file : 
	 */
	public abstract void sethibernate_connection_config_file(
			SOSOptionInFileName p_hibernate_connection_config_file);

	/**
	 * \brief gethibernate_connection_driver_class : 
	 * 
	 * \details
	 * Class of JBDC driver of the database connection
	 *
	 * \return 
	 *
	 */
	public abstract SOSOptionDBDriver gethibernate_connection_driver_class();

	/**
	 * \brief sethibernate_connection_driver_class : 
	 * 
	 * \details
	 * Class of JBDC driver of the database connection
	 *
	 * @param hibernate_connection_driver_class : 
	 */
	public abstract void sethibernate_connection_driver_class(
			SOSOptionDBDriver p_hibernate_connection_driver_class);

	/**
	 * \brief gethibernate_connection_password : 
	 * 
	 * \details
	 * Password of the database connection
	 *
	 * \return 
	 *
	 */
	public abstract SOSOptionPassword gethibernate_connection_password();

	/**
	 * \brief sethibernate_connection_password : 
	 * 
	 * \details
	 * Password of the database connection
	 *
	 * @param hibernate_connection_password : 
	 */
	public abstract void sethibernate_connection_password(
			SOSOptionPassword p_hibernate_connection_password);

	/**
	 * \brief gethibernate_connection_url : 
	 * 
	 * \details
	 * JDBC URL of the database connection
	 *
	 * \return 
	 *
	 */
	public abstract SOSOptionJdbcUrl gethibernate_connection_url();

	/**
	 * \brief sethibernate_connection_url : 
	 * 
	 * \details
	 * JDBC URL of the database connection
	 *
	 * @param hibernate_connection_url : 
	 */
	public abstract void sethibernate_connection_url(
			SOSOptionJdbcUrl p_hibernate_connection_url);

	/**
	 * \brief gethibernate_connection_username : 
	 * 
	 * \details
	 * User of the database connection
	 *
	 * \return 
	 *
	 */
	public abstract SOSOptionString gethibernate_connection_username();

	/**
	 * \brief sethibernate_connection_username : 
	 * 
	 * \details
	 * User of the database connection
	 *
	 * @param hibernate_connection_username : 
	 */
	public abstract void sethibernate_connection_username(
			SOSOptionString p_hibernate_connection_username);

	/**
	 * \brief gethibernate_dialect : 
	 * 
	 * \details
	 * Hibernate dialect of the database connection
	 *
	 * \return 
	 *
	 */
	public abstract SOSOptionString gethibernate_dialect();

	/**
	 * \brief sethibernate_dialect : 
	 * 
	 * \details
	 * Hibernate dialect of the database connection
	 *
	 * @param hibernate_dialect : 
	 */
	public abstract void sethibernate_dialect(
			SOSOptionString p_hibernate_dialect);

	/**
	 * \brief gethibernate_format_sql : 
	 * 
	 * \details
	 * 
	 *
	 * \return 
	 *
	 */
	public abstract SOSOptionBoolean gethibernate_format_sql();

	/**
	 * \brief sethibernate_format_sql : 
	 * 
	 * \details
	 * 
	 *
	 * @param hibernate_format_sql : 
	 */
	public abstract void sethibernate_format_sql(
			SOSOptionBoolean p_hibernate_format_sql);

	/**
	 * \brief gethibernate_show_sql : 
	 * 
	 * \details
	 * 
	 *
	 * \return 
	 *
	 */
	public abstract SOSOptionBoolean gethibernate_show_sql();

	/**
	 * \brief sethibernate_show_sql : 
	 * 
	 * \details
	 * 
	 *
	 * @param hibernate_show_sql : 
	 */
	public abstract void sethibernate_show_sql(
			SOSOptionBoolean p_hibernate_show_sql);

}
