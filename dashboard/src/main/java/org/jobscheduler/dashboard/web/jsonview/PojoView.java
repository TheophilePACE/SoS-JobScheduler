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
package org.jobscheduler.dashboard.web.jsonview;



public class PojoView implements DataView {

	private final Object data;
	private final Class<? extends BaseView> view;

	public PojoView(Object data, Class<? extends BaseView> view) {
		this.data = data;
		this.view = view;
	}

	@Override
	public boolean hasView() {
		return true;
	}

	@Override
	public Object getData() {
		return data;
	}

	@Override
	public Class<? extends BaseView> getView() {
		return view;
	}
}
