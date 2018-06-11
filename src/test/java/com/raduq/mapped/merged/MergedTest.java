package com.raduq.mapped.merged;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class MergedTest {

	private static final String VALUE_A = "A";
	private static final String VALUE_B = "B";
	private static final String VALUE_EXCLAMATION = "!";

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void canMergeFromB() {
		SomeClass someClassA = new SomeClass( VALUE_A );
		SomeClass someClassB = new SomeClass( VALUE_B );

		SomeClass mergedClass = new Merged<>( someClassA ).with( someClassB )
				.and( someClassB::getValue, someClassA::setValue )
				.get();

		assertThat( mergedClass.getValue(), equalTo( VALUE_B ) );
	}

	@Test
	public void canMergeFromA() {
		SomeClass someClassA = new SomeClass( VALUE_A );
		SomeClass someClassB = new SomeClass( null );

		SomeClass mergedClass = new Merged<>( someClassA ).with( someClassB )
				.and( someClassB::getValue, someClassA::setValue )
				.get();

		assertThat( mergedClass.getValue(), equalTo( VALUE_A ) );
	}

	@Test
	public void cantMergeWhenANull() {
		thrown.expect( MergeException.class );

		new Merged<>( null ).with( new SomeClass( VALUE_B ) ).get();
	}

	@Test
	public void cantMergeWhenBNull() {
		thrown.expect( MergeException.class );

		new Merged<>( new SomeClass( VALUE_A ) ).with( null ).get();
	}

	@Test
	public void canRunWhenCondition() {
		SomeClass classA = new SomeClass( VALUE_A );
		SomeClass classB = new SomeClass( VALUE_B );

		SomeClass classX = new Merged<>( classA ).with( classB )
				.when( classA.getValue().equals( VALUE_A ), classA::setValue, VALUE_EXCLAMATION )
				.get();

		assertThat( classX.getValue(), equalTo( VALUE_EXCLAMATION ) );
	}

	private class SomeClass {
		private String value;

		public SomeClass(String value) {
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}
}
