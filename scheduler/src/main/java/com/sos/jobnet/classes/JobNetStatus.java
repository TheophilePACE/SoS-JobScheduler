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
 * @author ss
 *         at 02.09.13 - 15:00
 */
public enum JobNetStatus {
    NOT_PROCESSED(0),
    RUNNING      (1),
    FINISHED     (2),
    ERROR        (3);

    private final int statusValue;
    private JobNetStatusText text;

    private JobNetStatus(int status) {
        this.statusValue = status;
        this.text = new JobNetStatusText(this);
    }

    public Integer getIndex() {
        return statusValue;
    }

    public static JobNetStatus valueOf(int index) {
        for(JobNetStatus s : JobNetStatus.values()) {
            if(s.getIndex() == index)
                return s;
        }
        throw new JobNetException("The index " + index + " does not point to a valid JobNetStatus.");
    }

    public String getMsg() {
        return text.getMsg();
    }

    public boolean isNotProcessed() {
        return(this == JobNetStatus.NOT_PROCESSED);
    }

    public boolean isRunning() {
        return(this == JobNetStatus.RUNNING);
    }

    public boolean isFinished() {
        return(this == JobNetStatus.FINISHED);
    }

    public boolean isError() {
        return(this == JobNetStatus.ERROR);
    }
}
