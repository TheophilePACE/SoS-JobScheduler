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
package sos.scheduler.editor.conf.listeners;

import java.text.Collator;
import java.util.Locale;
import java.util.regex.Pattern;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

public class SortTreeListener implements SelectionListener {
	@Override
	public void widgetDefaultSelected(final SelectionEvent e) {

	}

	@Override
	public void widgetSelected(final SelectionEvent e) {
		sortTree(e);
	}

	private void sortTree(final SelectionEvent e) {
		TreeColumn column = (TreeColumn) e.widget;
		Tree tree = column.getParent();
		TreeItem[] treeItems = tree.getItems();
		TreeColumn sortColumn = tree.getSortColumn();
		TreeColumn columns[] = tree.getColumns();
		tree.setSortColumn(column);
		int numOfColumns = columns.length;
		int columnIndex = this.findColumnIndex(columns, column, numOfColumns);
		Collator collator = Collator.getInstance(Locale.getDefault());
		Boolean sort = false;
		Pattern pattern = Pattern.compile("([\\+]*|[\\-]*)\\d+");
		if (column.equals(sortColumn) && tree.getSortDirection() == SWT.UP) {
			tree.setSortDirection(SWT.DOWN);
			for (int i = 1; i < treeItems.length; i++) {
				String value1 = treeItems[i].getText(columnIndex).trim();
				for (int j = 0; j < i; j++) {
					String value2 = treeItems[j].getText(columnIndex).trim();
					if (pattern.matcher(value1).matches() && pattern.matcher(value2).matches()) {
						double d1 = this.getDouble(value1);
						double d2 = this.getDouble(value2);
						if (d1 > d2) {
							sort = true;
						}
					}
					else
						if (collator.compare(value1, value2) > 0) {
							sort = true;
						}
					if (sort) {
						String[] values = this.getColumnValues(treeItems[i], numOfColumns);
						TreeItem[] subItems = treeItems[i].getItems();
						TreeItem item = new TreeItem(tree, SWT.NONE, j);
						item.setText(values);
						for (TreeItem si : subItems) {
							TreeItem[] subSubItems = si.getItems();
							TreeItem subItem = new TreeItem(item, SWT.NONE);
							subItem.setText(this.getColumnValues(si, numOfColumns));
							for (TreeItem ssi : subSubItems) {
								TreeItem subSubItem = new TreeItem(subItem, SWT.NONE);
								subSubItem.setText(this.getColumnValues(ssi, numOfColumns));
							}
						}
						treeItems[i].dispose();
						treeItems = tree.getItems();
						sort = false;
						break;
					}
				}
			}
		}
		else {
			tree.setSortDirection(SWT.UP);
			for (int i = 1; i < treeItems.length; i++) {
				String value1 = treeItems[i].getText(columnIndex).trim();
				for (int j = 0; j < i; j++) {
					String value2 = treeItems[j].getText(columnIndex).trim();
					if (pattern.matcher(value1).matches() && pattern.matcher(value2).matches()) {
						double d1 = this.getDouble(value1);
						double d2 = this.getDouble(value2);
						if (d1 < d2) {
							sort = true;
						}
					}
					else
						if (collator.compare(value1, value2) < 0) {
							sort = true;
						}
					if (sort) {
						String[] values = this.getColumnValues(treeItems[i], numOfColumns);
						TreeItem[] subItems = treeItems[i].getItems();
						TreeItem item = new TreeItem(tree, SWT.NONE, j);
						item.setText(values);
						for (TreeItem si : subItems) {
							TreeItem[] subSubItems = si.getItems();
							TreeItem subItem = new TreeItem(item, SWT.NONE);
							subItem.setText(this.getColumnValues(si, numOfColumns));
							for (TreeItem ssi : subSubItems) {
								TreeItem subSubItem = new TreeItem(subItem, SWT.NONE);
								subSubItem.setText(this.getColumnValues(ssi, numOfColumns));
							}
						}
						treeItems[i].dispose();
						treeItems = tree.getItems();
						sort = false;
						break;
					}
				}
			}
		}
	}

	/**
	 * Find the index of a column
	 *
	 * @param columns
	 * @param numOfColumns
	 * @return int
	 */
	private int findColumnIndex(final TreeColumn[] columns, final TreeColumn column, final int numOfColumns) {
		int index = 0;
		for (int i = 0; i < numOfColumns; i++) {
			if (column.equals(columns[i])) {
				index = i;
				break;
			}
		}
		return index;
	}

	/**
	 * Get the double value from a string
	 *
	 * @param str
	 * @return double
	 */
	private double getDouble(final String str) {
		double d;
		if (str.startsWith("+")) {
			d = Double.parseDouble(str.split("\\+")[1]);
		}
		else {
			d = Double.parseDouble(str);
		}
		return d;
	}

	/**
	 * Get the array of string value from the provided TreeItem
	 *
	 * @param treeItem
	 * @param numOfColumns
	 * @return String[]
	 */
	private String[] getColumnValues(final TreeItem treeItem, final int numOfColumns) {
		String[] values = new String[numOfColumns];
		for (int i = 0; i < numOfColumns; i++) {
			values[i] = treeItem.getText(i);
		}
		return values;
	}
}
