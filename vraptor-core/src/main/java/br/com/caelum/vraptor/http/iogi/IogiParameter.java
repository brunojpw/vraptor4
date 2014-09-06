package br.com.caelum.vraptor.http.iogi;

import java.util.regex.Pattern;

import br.com.caelum.iogi.parameters.Parameter;

public class IogiParameter
	extends Parameter {

	private static final Pattern DECORATION_REGEX = Pattern.compile("\\[\\w+\\]$");

	public IogiParameter(String name, String value) {
		super(name, value);
	}

	@Override
	public String getFirstNameComponent() {
		final String first = getFirstNameComponentWithDecoration();
		return DECORATION_REGEX.matcher(first).replaceAll("");
	}

	@Override
	public boolean isDecorated() {
		return DECORATION_REGEX.matcher(getFirstNameComponentWithDecoration()).find();
	}
}
