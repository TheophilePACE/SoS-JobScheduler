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
package com.sos.scheduler.engine.kernel.cppproxy;

import com.sos.scheduler.engine.kernel.order.Order;
import com.sos.scheduler.engine.cplusplus.runtime.CppProxyWithSister;
import com.sos.scheduler.engine.cplusplus.runtime.annotation.CppClass;

@CppClass(clas="sos::scheduler::order::Order", directory="scheduler", include="spooler.h")
public interface OrderC extends CppProxyWithSister<Order> {
    Order.Type sisterType = new Order.Type();

    String path();
    String file_path();
    String job_chain_path_string();
    Job_chainC job_chain();
    String string_id();
    void set_id(String id);
    String title();
    void set_title(String o);
    Variable_setC params();
    //    Node job_chain_node();
    String string_state();
    void set_end_state(String order_state);
    String string_end_state();
    Prefix_logC log();
    void java_remove();
    boolean suspended();
    void set_suspended(boolean o);
}
