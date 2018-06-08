package com.raduq.mapped;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Optional;

import org.junit.Test;

public class MappedTest {

	@Test
	public void canMapToOptional() {
		String mapped = new Mapped<>( 1 )
				.toOptional( integer -> Optional.of( String.valueOf( integer ) ) )
				.orElse( "broken" );
		assertThat( mapped, equalTo( "1" ) );
	}

	@Test
	public void canMapToOptionalWithCondition() {
		String mapped = new Mapped<>( 1 )
				.when( integer -> integer == 1 )
				.toOptional( integer -> Optional.of( String.valueOf( integer ) ) )
				.orElse( "broken" );
		assertThat( mapped, equalTo( "1" ) );
	}

	@Test
	public void cantMapToOptionalWhenConditionFails() {
		String mapped = new Mapped<>( 1 )
				.when( integer -> integer == 2 )
				.toOptional( integer -> Optional.of( String.valueOf( integer ) ) )
				.orElse( "broken" );
		assertThat( mapped, equalTo( "broken" ) );
	}

	@Test
	public void cantMapToOptionalWhenNull() {
		String mapped = new Mapped<>( null )
				.toOptional( integer -> Optional.of( String.valueOf( integer ) ) )
				.orElse( "broken" );
		assertThat( mapped, equalTo( "broken" ) );
	}

	@Test
	public void canMap() {
		String mapped = new Mapped<>( 1 )
				.to( integer -> Optional.of( String.valueOf( integer ) ) );
		assertThat( mapped, equalTo( "1" ) );
	}

	@Test
	public void canMapWithCondition() {
		String mapped = new Mapped<>( 1 )
				.when( integer -> integer == 1 )
				.to( integer -> Optional.of( String.valueOf( integer ) ) );
		assertThat( mapped, equalTo( "1" ) );
	}

	@Test
	public void cantMapWhenConditionFails() {
		String mapped = new Mapped<>( 1 )
				.when( integer -> integer == 2 )
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
