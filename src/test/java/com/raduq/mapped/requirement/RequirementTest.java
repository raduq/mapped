package com.raduq.mapped.requirement;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.Test;

public class RequirementTest {

	@Test
	public void canCreateDefaultRequirement() {
		assertThat( new Requirement<String>(), notNullValue() );
	}

	@Test
	public void canBeValidWithDefaultRequirement() {
		Requirement<String> requirement = new Requirement<>();
		assertThat( requirement.isValid( "something" ), is( true ) );
	}

	@Test
	public void canBeValid() {
		Requirement<String> requirement = new Requirement<>();
		requirement.add( vl -> vl.equals( "banana" ) );
		assertThat( requirement.isValid( "banana" ), is( true ) );
	}

	@Test
	public void cantBeValid() {
		Requirement<String> requirement = new Requirement<>();
		requirement.add( vl -> vl.equals( "apple" ) );
		assertThat( requirement.isValid( "banana" ), is( false ) );
	}

	@Test
	public void canBeAllValid() {
		Requirement<String> requirement = new Requirement<>();
		requirement.add( vl -> vl.startsWith( "banana" ) );
		assertThat( requirement.allValid( Arrays.asList( "banana1", "banana2" ) ), is( true ) );
	}

	@Test
	public void cantBeAllValid() {
		Requirement<String> requirement = new Requirement<>();
		requirement.add( vl -> vl.startsWith( "banana" ) );
		assertThat( requirement.allValid( Arrays.asList( "banana1", "apple1" ) ), is( false ) );
	}

	@Test
	public void cantBeAllValidWithListNull() {
		Requirement<String> requirement = new Requirement<>();
		assertThat( requirement.allValid( null ), is( false ) );
	}
}
