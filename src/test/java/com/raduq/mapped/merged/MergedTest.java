package com.raduq.mapped.merged;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class MergedTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void canMergeFromB() {
		SomeClass someClassA = new SomeClass( "A" );
		SomeClass someClassB = new SomeClass( "B" );

		SomeClass mergedClass = new Merged<>( someClassA ).with( someClassB )
				.and( someClassB::getValue, someClassA::setValue )
				.get();

		assertThat( mergedClass.getValue(), equalTo( "B" ) );
	}

	@Test
	public void canMergeFromA() {
		SomeClass someClassA = new SomeClass( "A" );
		SomeClass someClassB = new SomeClass( null );

		SomeClass mergedClass = new Merged<>( someClassA ).with( someClassB )
				.and( someClassB::getValue, someClassA::setValue )
				.get();

		assertThat( mergedClass.getValue(), equalTo( "A" ) );
	}

	@Test
	public void cantMergeWhenANull() {
		thrown.expect( MergeException.class );

		new Merged<>( null ).with( new SomeClass( "B" ) ).get();
	}

	@Test
	public void cantMergeWhenBNull() {
		thrown.expect( MergeException.class );

		new Merged<>( new SomeClass( "A" ) ).with( null ).get();
	}

	@Test
	public void canRunWhenCondition() {
		SomeClass classA = new SomeClass( "A" );
		SomeClass classB = new SomeClass( "B" );

		SomeClass classX = new Merged<>( classA ).with( classB )
				.when( classA.getValue().equals( "A" ), classA::setValue, "!" )
				.get();

		assertThat( classX.getValue(), equalTo( "!" ) );
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
