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
package sos.util.security;

import java.io.FileOutputStream;
import java.security.PrivateKey;
import java.security.cert.Certificate;

import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfSignatureAppearance;
import com.lowagie.text.pdf.PdfStamper;

/**
 * @author re
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class SOSPDFSignatur {

    private static String reason = "";

    private static String contact = "";

    private static String location = "";
    
    private static boolean visible	= false;
    
    /**
     * PDF Signatur erzeugen
     * 
     * @param privateKey		Private Key
     * @param chain				Certificate Chain
     * @param originalPdfName	Original PDF Datei zur Signierung
     * @param outputPdfName		Output (signierte) PDF Datei
     * @throws Exception
     */
    public static void createSignatur(PrivateKey privateKey,
            Certificate[] chain, String originalPdfName, String outputPdfName)
            throws Exception {

        PdfReader reader = new PdfReader(originalPdfName);
        FileOutputStream fout = new FileOutputStream(outputPdfName);

        //createSignature(PdfReader reader, OutputStream os, char pdfVersion)
        //pdfVersion - the new pdf version or '\0' to keep the same version as
        // the original document

        PdfStamper stp = PdfStamper.createSignature(reader, fout, '\0');
        PdfSignatureAppearance sap = stp.getSignatureAppearance();

        
        //setCrypto(PrivateKey privKey, Certificate[] certChain, CRL[] crlList, PdfName filter) 
        // CRL - certificate revocation lists (CRLs) that have different formats but important common uses.
        //		 For example, all CRLs share the functionality of listing revoked certificates, and can be queried on whether or not they list a given certificate.
        // PdfName
        // SELF_SIGNED    	- 	The self signed filter
        // VERISIGN_SIGNED  - 	The VeriSign filter
        // WINCER_SIGNED    - 	The Windows Certificate Security
        sap.setCrypto(privateKey, chain, null,PdfSignatureAppearance.SELF_SIGNED);
        //sap.setCrypto(privateKey, chain, null,PdfSignatureAppearance.WINCER_SIGNED);

        sap.setReason(SOSPDFSignatur.reason);
        sap.setContact(SOSPDFSignatur.contact);
        sap.setLocation(SOSPDFSignatur.location);

        //GregorianCalendar cal = new GregorianCalendar();
        //sap.setSignDate(cal);

        //             comment next line to have an invisible signature
        //setVisibleSignature(Rectangle pageRect, int page, String fieldName)

        //sap.setVisibleSignature(new Rectangle(100, 100, 200, 200), 1, null);
        //sap.setVisibleSignature(new Rectangle(100,100,200, 200), 1, null);
        
        if(SOSPDFSignatur.visible){// todo
            //sap.setVisibleSignature(new Rectangle(200, 200, 400, 400), 1, null);
        }
        
        stp.close();

    }

    /**
     * Signatur Description
     * 
     * @param reason
     */
    public static void setReason(String reason) {
        SOSPDFSignatur.reason = reason;
    }

    /**
     * Kontakt
     * 
     * @param contact
     */
    public static void setContact(String contact) {
        SOSPDFSignatur.contact = contact;
    }
    
    /**
     * Location
     * 
     * @param location
     */
    public static void setLocation(String location) {
        SOSPDFSignatur.location = location;
    }

    /**
     * @param visible The visible to set.
     */
    public static void setVisible(boolean visible) {
        SOSPDFSignatur.visible = visible;
    }
    
    
    public static void main(String[] args) throws Exception {

        try {

            
            String path = "J:/E/java/al/sos.util/signature/";
            
            String keyAlgorithmName = "RSA";
            String provider			= "BC";
            String signatureAlgo	= "SHA1";
            
            String privateKeyFileName = path + "new_"+keyAlgorithmName+"="+provider+".privatekey";
            //String privateKeyFileName = path + "new_RSA=BC.privatekey";
            
            
            PrivateKey privKey = SOSKeyGenerator.getPrivateKeyFromFile(privateKeyFileName);
            
            
            //System.out.println("privKey Length : "+privKey.getEncoded().length);
            
            
            String certFile = path + privKey.getAlgorithm()+"("+provider+")="+signatureAlgo+".cer";
            //String certFile = path + "RSA(BC)=SHA1.cer";
            
            
            Certificate[] chain = SOSCertificate.getCertificateChain(certFile);

            
            SOSPDFSignatur.setReason("Das ist eine Test signatur");
            SOSPDFSignatur.setContact("al@sos-berlin.com");
            SOSPDFSignatur.setLocation("Berlin");

            String signPdfFile = privKey.getAlgorithm()+"("+provider+")="+signatureAlgo+".pdf";
            
            SOSPDFSignatur.createSignatur(privKey, chain,
                    path + "scheduler_install.pdf", path + signPdfFile);

            System.out.println("Sign. PDF wurde erstellt");
            System.out.println("privateKey = "+privateKeyFileName);
            System.out.println("certFile = "+certFile);
            System.out.println("pdfFile = "+path+signPdfFile);

        } catch (Exception e) {
            System.out.println("da + "+e.getMessage());
            System.out.println(e.getStackTrace());
        }

    }
    
}
