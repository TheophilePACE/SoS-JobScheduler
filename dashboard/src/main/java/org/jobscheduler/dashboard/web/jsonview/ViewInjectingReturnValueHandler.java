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


import java.util.ArrayList;
import java.util.List;

import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

/**
 * Decorator that detects a declared {@link ResponseView}, and injects support
 * if required
 * 
 * @author martypitt
 * 
 */
public class ViewInjectingReturnValueHandler implements
		HandlerMethodReturnValueHandler {

	private final HandlerMethodReturnValueHandler delegate;

	public ViewInjectingReturnValueHandler(
			HandlerMethodReturnValueHandler delegate) {
		this.delegate = delegate;
	}


	@Override
	public boolean supportsReturnType(MethodParameter returnType) {
		return delegate.supportsReturnType(returnType);
	}

	@Override
	public void handleReturnValue(Object returnValue,
			MethodParameter returnType, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest) throws Exception {

		Class<? extends BaseView> viewClass = getDeclaredViewClass(returnType);
		if (viewClass != null) {
			returnValue = wrapResult(returnValue, viewClass);
		}

		delegate.handleReturnValue(returnValue, returnType, mavContainer,
				webRequest);
	}

	/**
	 * Returns the view class declared on the method, if it exists. Otherwise,
	 * returns null.
	 * 
	 * @param returnType
	 * @return
	 */
	private Class<? extends BaseView> getDeclaredViewClass(
			MethodParameter returnType) {
		ResponseView annotation = returnType
				.getMethodAnnotation(ResponseView.class);
		if (annotation != null) {
			return annotation.value();
		} else {
			return null;
		}
	}

	private Object wrapResult(Object result, Class<? extends BaseView> viewClass) {
		PojoView response = new PojoView(result, viewClass);
		return response;
	}


}