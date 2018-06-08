package com.raduq.mapped;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.emptyCollectionOf;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

public class MappedListTest {

	@Test
	public void canMap() {
		List<String> mapped = new MappedList<>( Arrays.asList( 1, 2, 3 ) )
				.to( integer -> Optional.of( String.valueOf( integer ) ) );
		assertThat( mapped, equalTo( Arrays.asList( "1", "2", "3" ) ) );
	}

	@Test
	public void canMapWithCondition() {
		List<String> mapped = new MappedList<>( Arrays.asList( 1, 1, 1 ) )
				.whenAll( integer -> integer == 1 )
				.to( integer -> Optional.of( String.valueOf( integer ) ) );
		assertThat( mapped, equalTo( Arrays.asList( "1", "1", "1" ) ) );
	}

	@Test
	public void cantMapWhenConditionFails() {
		List<String> mapped = new MappedList<>( Arrays.asList( 1, 2, 3 ) )
				.whenAll( integer -> integer == 2 )
				.to( integer -> Optional.of( String.valueOf( integer ) ) );
		assertThat( mapped, emptyCollectionOf( String.class ) );
	}

	@Test
	public void cantMapWhenNull() {
		List<String> mapped = new MappedList<>( null )
				.to( integer -> Optional.of( String.valueOf( integer ) ) );
		assertThat( mapped, emptyCollectionOf( String.class ) );
	}
}
