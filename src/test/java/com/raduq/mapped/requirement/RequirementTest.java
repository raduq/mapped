package com.raduq.mapped.requirement;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.Test;

public class RequirementTest {

	private static final String VALUE_SOMETHING = "something";
	private static final String VALUE_BANANA = "banana";
	private static final String VALUE_APPLE = "apple";
	private static final String VALUE_APPLE_1 = "apple1";
	private static final String VALUE_BANANA_1 = "banana1";
	private static final String VALUE_BANANA_2 = "banana2";

	@Test
	public void canCreateDefaultRequirement() {
		assertThat( new Requirement<String>(), notNullValue() );
	}

	@Test
	public void canBeValidWithDefaultRequirement() {
		Requirement<String> requirement = new Requirement<>();
		assertThat( requirement.isValid( VALUE_SOMETHING ), is( true ) );
	}

	@Test
	public void canBeValid() {
		Requirement<String> requirement = new Requirement<>();
		requirement.add( vl -> vl.equals( VALUE_BANANA ) );
		assertThat( requirement.isValid( VALUE_BANANA ), is( true ) );
	}

	@Test
	public void cantBeValid() {
		Requirement<String> requirement = new Requirement<>();
		requirement.add( vl -> vl.equals( VALUE_APPLE ) );
		assertThat( requirement.isValid( VALUE_BANANA ), is( false ) );
	}

	@Test
	public void canBeAllValid() {
		Requirement<String> requirement = new Requirement<>();
		requirement.add( vl -> vl.startsWith( VALUE_BANANA ) );
		assertThat( requirement.allValid( Arrays.asList( VALUE_BANANA_1, VALUE_BANANA_2 ) ), is( true ) );
	}

	@Test
	public void cantBeAllValid() {
		Requirement<String> requirement = new Requirement<>();
		requirement.add( vl -> vl.startsWith( VALUE_BANANA ) );
		assertThat( requirement.allValid( Arrays.asList( VALUE_BANANA_1, VALUE_APPLE_1 ) ), is( false ) );
	}

	@Test
	public void cantBeAllValidWithListNull() {
		Requirement<String> requirement = new Requirement<>();
		assertThat( requirement.allValid( null ), is( false ) );
	}
}
