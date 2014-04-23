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
package com.sos.dialog;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.CloseWindowListener;
import org.eclipse.swt.browser.OpenWindowListener;
import org.eclipse.swt.browser.ProgressAdapter;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.browser.StatusTextEvent;
import org.eclipse.swt.browser.StatusTextListener;
import org.eclipse.swt.browser.VisibilityWindowListener;
import org.eclipse.swt.browser.WindowEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class BrowserViewForm {

	private Browser			browser	= null;
	private GridLayout		gridLayout;
	private GridData		data;
	private final Logger	logger	= Logger.getLogger(BrowserViewForm.class);
	private String			url		= "http://localhost:4444";
	private final Composite	parent;

	public BrowserViewForm(final Composite parent_, final int style, final String url_) {
		parent = parent_;
		url = url_;
		showBrowser();

	}

	public BrowserViewForm(final Composite parent_, final int style, final String host, final int port) {
		parent = parent_;
		url = String.format("%s:%s", host, port);
		showBrowser();
	}

	private void showBrowser() {
		gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		parent.setLayout(gridLayout);

		try {

			browser = new Browser(parent, SWT.NONE);
			initialize(parent.getDisplay(), browser);
			// browser = new Browser(parent, SWT.MOZILLA);
            if ((!url.toLowerCase().startsWith("http://")) &&
               (!url.toLowerCase().startsWith("file://"))) {
				url = "http://" + url;
			}
			logger.debug("load url = " + url);
			
			browser.setUrl(url);
			browser.addProgressListener(new ProgressAdapter() {
				@Override
				public void completed(final ProgressEvent event) {
					logger.trace("processed");
				}
			});
			browser.addStatusTextListener(new StatusTextListener() {
				@Override
				public void changed(final StatusTextEvent event) {
					logger.trace("status = " + event.text);
				}
			});

			data = new GridData();
			data.horizontalAlignment = GridData.FILL;
			data.verticalAlignment = GridData.FILL;
			data.horizontalSpan = 2;
			data.grabExcessHorizontalSpace = true;
			data.grabExcessVerticalSpace = true;

			browser.setLayoutData(data);
			browser.layout();
		}
		catch (Exception E) {
			Label label = new Label(parent, SWT.NONE);
			label.setText("Browser could not be startet. Check wether XULRUNNER 1.9.2 is installed");
		}

	}

	/* register WindowEvent listeners */
	static void initialize(final Display display, final Browser browser) {
		browser.addOpenWindowListener(new OpenWindowListener() {
			@Override
			public void open(final WindowEvent event) {
				if (!event.required)
					return; /* only do it if necessary */
				Shell shell = new Shell(display);
				shell.setLayout(new FillLayout());
				Browser browser = new Browser(shell, SWT.NONE);

				initialize(display, browser);
				event.browser = browser;
			}
		});
		browser.addVisibilityWindowListener(new VisibilityWindowListener() {
			@Override
			public void hide(final WindowEvent event) {
				Browser browser = (Browser) event.widget;
				Shell shell = browser.getShell();
				shell.setVisible(false);
			}

			@Override
			public void show(final WindowEvent event) {
				Browser browser = (Browser) event.widget;
				final Shell shell = browser.getShell();
				if (event.location != null)
					shell.setLocation(event.location);
				if (event.size != null) {
					Point size = event.size;
					shell.setSize(shell.computeSize(size.x, size.y));
				}
				shell.open();
			}
		});
		browser.addCloseWindowListener(new CloseWindowListener() {
			@Override
			public void close(final WindowEvent event) {
				Browser browser = (Browser) event.widget;
				Shell shell = browser.getShell();
				shell.close();
			}
		});
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(final String url) {
		this.url = url;
		browser.setUrl(url);
	}

}
