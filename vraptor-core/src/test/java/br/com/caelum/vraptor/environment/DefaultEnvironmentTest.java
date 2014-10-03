/***
 * Copyright (c) 2009 Caelum - www.caelum.com.br/opensource All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package br.com.caelum.vraptor.environment;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.URL;
import java.util.NoSuchElementException;

import javax.servlet.ServletContext;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

public class DefaultEnvironmentTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	private ServletContext context;

	@Before
	public void setup() {
		context = Mockito.mock(ServletContext.class);
	}

	@Test
	public void shouldUseEnvironmentBasedFileIfFoundUnderEnvironmentFolder() throws IOException {
		DefaultEnvironment env = new DefaultEnvironment(EnvironmentType.DEVELOPMENT);
		URL resource = env.getResource("/rules.txt");

		assertThat(resource, notNullValue());
		assertThat(resource, is(getClass().getResource("/development/rules.txt")));
	}

	@Test
	public void shouldUseRootBasedFileIfNotFoundUnderEnvironmentFolder() throws IOException {
		DefaultEnvironment env = new DefaultEnvironment(EnvironmentType.PRODUCTION);
		URL resource = env.getResource("/rules.txt");

		assertThat(resource, notNullValue());
		assertThat(resource.toString(), endsWith("/rules.txt"));
	}

	@Test
	public void shouldLoadConfigurationInDefaultFileEnvironment() throws IOException {
		DefaultEnvironment env = new DefaultEnvironment(context);
		when(context.getInitParameter(DefaultEnvironment.ENVIRONMENT_PROPERTY)).thenReturn("production");
		
		assertThat(env.get("env_name"), is("production"));
		assertThat(env.get("only_in_default_file"), is("only_in_default_file"));
	}

	@Test
	public void shouldUseFalseIfFeatureIsNotPresent() throws IOException {
		DefaultEnvironment env = new DefaultEnvironment(context);
		assertThat(env.supports("feature_that_doesnt_exists"), is(false));
	}

	@Test
	public void shouldTrimValueWhenEvaluatingSupports() throws Exception {
		DefaultEnvironment env = new DefaultEnvironment(context);
		assertThat(env.supports("untrimmed_boolean"), is(true));
	}

	@Test
	public void shouldThrowExceptionIfKeyDoesNotExist() throws Exception {
		exception.expect(NoSuchElementException.class);

		DefaultEnvironment env = new DefaultEnvironment(context);
		env.get("key_that_doesnt_exist");
	}

	@Test
	public void shouldGetDefaultValueIfTheValueIsntSet() throws Exception {
		DefaultEnvironment env = new DefaultEnvironment(context);
		String value = env.get("inexistent", "fallback");
		assertThat(value, is("fallback"));
	}

	@Test
	public void shouldGetValueIfIsSetInProperties() throws Exception {
		DefaultEnvironment env = new DefaultEnvironment(context);
		String value = env.get("env_name", "fallback");
		assertThat(value, is("development"));
	}
}