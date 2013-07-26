/***
 * Copyright (c) 2009 Caelum - www.caelum.com.br/opensource
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.com.caelum.vraptor4.view;

import java.lang.reflect.Method;

import br.com.caelum.vraptor4.proxy.JavassistProxifier;
import br.com.caelum.vraptor4.proxy.MethodInvocation;
import br.com.caelum.vraptor4.proxy.ObjenesisInstanceCreator;
import br.com.caelum.vraptor4.proxy.Proxifier;
import br.com.caelum.vraptor4.proxy.SuperMethod;
import br.com.caelum.vraptor4.view.PageResult;

public class MockedPage implements PageResult {

	private final Proxifier proxifier;

	public MockedPage() {
		proxifier = new JavassistProxifier(new ObjenesisInstanceCreator());
	}

	public void forwardTo() {

	}

	public void forwardTo(String url) {

	}
	
	public void defaultView() {
		
	}

	public void include() {
	}

	public void redirectTo(String url) {

	}

	public <T> T of(Class<T> controllerType) {
		return proxifier.proxify(controllerType, new MethodInvocation<T>() {

			public Object intercept(T proxy, Method method, Object[] args, SuperMethod superMethod) {
				return null;
			}

		});
	}

	public void redirect(String url) {
		this.redirectTo(url);
	}

	public void forward(String url) {
		this.forwardTo(url);
	}

	public void forward() {
		this.defaultView();
	}

	

}