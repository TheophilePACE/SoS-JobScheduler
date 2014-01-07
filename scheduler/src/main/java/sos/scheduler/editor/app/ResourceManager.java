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
package sos.scheduler.editor.app;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import com.swtdesigner.SWTResourceManager;

public class ResourceManager extends SWTResourceManager {
	@SuppressWarnings("unused")
	private final String					conClassName		= this.getClass().getSimpleName();
	@SuppressWarnings("unused")
	private static final String				conSVNVersion		= "$Id: ResourceManager.java 20651 2013-07-05 07:23:48Z kb $";
	@SuppressWarnings("unused")
	private final Logger					logger				= Logger.getLogger(this.getClass());

	private static final int				MISSING_IMAGE_SIZE	= 10;

	private static HashMap<String, Image>	m_ClassImageMap		= new HashMap<String, Image>();

	public static Image getImageFromResource(final String path) {
		return getImageFromResource("default", path);
	}

	public static Image getImageFromResource(final String section, final String path) {
		String key = section + '|' + SWTResourceManager.class.getName() + '|' + path;
		Image image = m_ClassImageMap.get(key);
		if (image == null) {
			try {
				InputStream is = key.getClass().getResourceAsStream(path);
				image = getImage(is);
				m_ClassImageMap.put(key, image);
				is.close();
			}
			catch (Exception e) {
				try {
					//					new ErrorLog("error in " + SOSClassUtil.getMethodName(), e);
				}
				catch (Exception ee) {
				}
				image = getMissingImage();
				m_ClassImageMap.put(key, image);
			}
		}
		return image;
	}

	private static Image getMissingImage() {
		Image image = new Image(Display.getCurrent(), MISSING_IMAGE_SIZE, MISSING_IMAGE_SIZE);
		//
		GC gc = new GC(image);
		gc.setBackground(getColor(SWT.COLOR_RED));
		gc.fillRectangle(0, 0, MISSING_IMAGE_SIZE, MISSING_IMAGE_SIZE);
		gc.dispose();
		//
		return image;
	}

	/**
	 * Dispose of cached objects and their underlying OS resources. This should
	 * only be called when the cached objects are no longer needed (e.g. on
	 * application shutdown)
	 */
	public static void dispose() {
		disposeImages();
		SWTResourceManager.disposeColors();
		SWTResourceManager.disposeFonts();
		SWTResourceManager.disposeCursors();
	}

	/**
	 * Dispose all of the cached images
	 */
	public static void disposeImages() {
		SWTResourceManager.disposeImages();

		for (Object element : m_ClassImageMap.values())
			((Image) element).dispose();
		m_ClassImageMap.clear();
	}

	/**
	 * Dispose cached images in specified section
	 *
	 * @param section the section do dispose
	 */
	public static void disposeImages(final String section) {
		SWTResourceManager.disposeImages(section);

		for (Iterator I = m_ClassImageMap.keySet().iterator(); I.hasNext();) {
			String key = (String) I.next();
			if (!key.startsWith(section + '|'))
				continue;
			Image image = m_ClassImageMap.get(key);
			image.dispose();
			I.remove();
		}
	}

}
