/**
 * Copyright (C) 2014 BigLoupe http://bigloupe.github.io/SoS-JobScheduler/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */
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
package com.sos.jobnet.classes;

/**
 * Created with IntelliJ IDEA.
 *
 * @uthor schaedi
 * at 09.09.13 17:16
 */
public enum NodeStatus {
    NOT_PROCESSED(0),
    RUNNING      (1),
    FINISHED     (2),
    ERROR        (3),
    IGNORED      (4),
    WAITING      (5),
    DISPATCHING  (6),
    STARTING     (7),
    STARTED      (8),
    UNDEFINED    (99);

    private int statusValue = -1;
    private NodeStatusText text;

    private NodeStatus(int status) {
        this.statusValue = status;
        this.text = new NodeStatusText(this);
    }

    public Integer getIndex() {
        return statusValue;
    }

    public String getMsg() {
        return text.getMsg();
    }

    public static NodeStatus valueOf(int index) {
        for(NodeStatus s : NodeStatus.values()) {
            if(s.getIndex() == index)
                return s;
        }
        throw new JobNetException("The index " + index + " does not point to a valid NodeStatus.");
    }

    public boolean isRunning() {
        return(this == NodeStatus.RUNNING);
    }

    public boolean isNotProcessed() {
        return(this == NodeStatus.NOT_PROCESSED);
    }

    public boolean isFinished() {
        return(this == NodeStatus.FINISHED);
    }

    public boolean isStarted() {
        return(this == NodeStatus.STARTED);
    }

    public boolean isStarting() {
        return(this == NodeStatus.STARTING);
    }

    public boolean isError() {
        return(this == NodeStatus.ERROR);
    }

    public boolean isIgnored() {
        return(this == NodeStatus.IGNORED);
    }

    public boolean isWaiting() {
        return(this == NodeStatus.WAITING);
    }

    public boolean isDispatching() {
        return(this == NodeStatus.DISPATCHING);
    }

}
