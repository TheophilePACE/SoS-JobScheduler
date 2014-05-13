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
package sos.net;


import java.io.File;


/**
 *
 * @version $Id: SOSMailAttachment.java 1849 2006-03-22 08:52:18Z gb $
 */


public class SOSMailAttachment {

         private String contentType="";
         private String encoding="";
         private String charset="";
         private File file=null;

        private String filename;
        private String fileExtension;

        private byte[] content;

        private String contentid;         
         
            public SOSMailAttachment( SOSMail sosmail, File f){
               file = f;
               contentType = sosmail.getAttachmentContentType();
               if (contentType.length()==0){
                  contentType = sosmail.getContentType();
               }

               encoding = sosmail.getAttachmentEncoding();
               if (encoding.length()==0){
                  encoding = sosmail.getEncoding();
               }
            
               charset = sosmail.getAttachmentCharset();
               if (charset.length()==0){
                  charset = sosmail.getCharset();
               }
        
            }
     
            public SOSMailAttachment() {}

            public String getCharset() {
              return charset;
            }
     
            public String getContentType() {
              return contentType;
            }
     
              public String getEncoding() {
                return encoding;
              }
     
              public File getFile() {
                return file;
              }
              public void setCharset(String charset) {
                if (charset != null && charset.length() > 0){    
                this.charset = charset;
                }
              }
              public void setContentType(String content_type) {
                if (content_type != null && content_type.length() > 0){    
                  this.contentType = content_type;
                }
              }
              public void setEncoding(String encoding) {
                if (encoding != null && encoding.length() > 0){    
                  this.encoding = encoding;
                }
              }
              
            public String getFilename() {
                return filename;
            }

            public void setFilename(String filename) {
                this.filename = filename;
            }

            public byte[] getContent() {
                return content;
            }

            public void setContent(byte[] content) {
                this.content = content;
            }

            public String getContentid() {
                return contentid;
            }

            public void setContentid(String contentid) {
                this.contentid = contentid;
            }

            /**
             * @return Returns the fileExtension.
             */
            public final String getFileExtension() {
                return fileExtension;
            }

            /**
             * @param fileExtension The fileExtension to set.
             */
            public final void setFileExtension(String fileExtension) {
                this.fileExtension = fileExtension;
            }
            
              
        }
