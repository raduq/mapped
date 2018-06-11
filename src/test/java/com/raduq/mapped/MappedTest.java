package com.raduq.mapped;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Optional;

import org.junit.Test;

public class MappedTest {

	private static final String ERROR_VALUE = "broken";
	private static final String STRING_VALUE = "1";
	private static final int INT_VALUE_1 = 1;
	private static final int INT_VALUE_2 = 2;

	@Test
	public void canMapToOptional() {
		String mapped = new Mapped<>( INT_VALUE_1 )
				.toOptional( integer -> Optional.of( String.valueOf( integer ) ) )
				.orElse( ERROR_VALUE );
		assertThat( mapped, equalTo( STRING_VALUE ) );
	}

	@Test
	public void canMapToOptionalWithCondition() {
		String mapped = new Mapped<>( INT_VALUE_1 )
				.when( integer -> integer == INT_VALUE_1 )
				.toOptional( integer -> Optional.of( String.valueOf( integer ) ) )
				.orElse( ERROR_VALUE );
		assertThat( mapped, equalTo( STRING_VALUE ) );
	}

	@Test
	public void cantMapToOptionalWhenConditionFails() {
		String mapped = new Mapped<>( INT_VALUE_1 )
				.when( integer -> integer == INT_VALUE_2 )
				.toOptional( integer -> Optional.of( String.valueOf( integer ) ) )
				.orElse( ERROR_VALUE );
		assertThat( mapped, equalTo( ERROR_VALUE ) );
	}

	@Test
	public void cantMapToOptionalWhenNull() {
		String mapped = new Mapped<>( null )
				.toOptional( integer -> Optional.of( String.valueOf( integer ) ) )
				.orElse( ERROR_VALUE );
		assertThat( mapped, equalTo( ERROR_VALUE ) );
	}

	@Test
	public void canMap() {
		String mapped = new Mapped<>( INT_VALUE_1 )
				.to( integer -> Optional.of( String.valueOf( integer ) ) );
		assertThat( mapped, equalTo( STRING_VALUE ) );
	}

	@Test
	public void canMapWithCondition() {
		String mapped = new Mapped<>( INT_VALUE_1 )
				.when( integer -> integer == INT_VALUE_1 )
				.to( integer -> Optional.of( String.valueOf( integer ) ) );
		assertThat( mapped, equalTo( STRING_VALUE ) );
	}

	@Test
	public void cantMapWhenConditionFails() {
		String mapped = new Mapped<>( INT_VALUE_1 )
				.when( integer -> integer == INT_VALUE_2 )
				.to( integer -> Optional.of( String.valueOf( integer ) ) );
		assertThat( mapped, nullValue() );
	}

	@Test
	public void cantMapWhenNull() {
		String mapped = new Mapped<>( null )
				.to( integer -> Optional.of( String.valueOf( integer ) ) );
		assertThat( mapped, nullValue() );
	}
}
