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

/**
 * <p>Title: SOSTreeHelper</p>
 * <p>Description: ContentHandler zum dynamischen f&uuml;llen des SOSTree Baumes.</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: SOS-Berlin GmbH</p>
 * @author Titus Meyer
 * @version 1.0.0
 */
public class SOSTreeContentHandler {

  /**
   * Konstruktor
   *
   */
  public SOSTreeContentHandler() {

  }


  /**
   * Wird zu Anfang eines leeren Knotenelementes aufgerufen.
   * Hier kann z.B. zu dem Knotenelement ein passendes SQL-Statement ausgef&uuml;hrt
   * werden und das Result-Set in einer Instanzvariable abgespeichert werden.
   *
   * @param node Knotenelement, dessen Inhalt ben&ouml;tigt wird
   */
  public void startNode(SOSTreeElement node) throws Exception{

  }


  /**
   * Diese Methode wird nach der startNode Methode sooft aufgerufen, bis sie null
   * zur&uuml;ck liefert. Die zur&uuml;ck gelieferten Elemente werden in das
   * Knotenelement eingef&uuml;gt.
   *
   * @param parent Knotenelment, in welches die Elemente eingef&uuml;gt werden
   * @param before Element, welches ggf. zuvor eingef&uuml;gt wurde
   * @return einzuf&uuml;gendes Element
   */
  public SOSTreeElement newElement(SOSTreeElement parent, SOSTreeElement before) throws Exception {
    return null;
  }
}
