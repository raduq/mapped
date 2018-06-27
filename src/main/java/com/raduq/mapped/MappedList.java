package com.raduq.mapped;

import static java.util.Optional.empty;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.raduq.mapped.requirement.Requirement;

/**
 * Provides mapping between two list of objects.
 * Automatically runs Requirement validations with a default avoiding NullPointers.
 *
 * @param <A>
 * 		type of the object to be converted to <B>
 */
public class MappedList<A> {

	private List<A> listA;
	private Requirement<A> requirement;

	/**
	 * Constructs a Mapped object.
	 *
	 * @param listA
	 * 		list to be converted
	 */
	public MappedList(List<A> listA) {
		this.listA = listA;
		this.requirement = new Requirement<>();
	}

	/**
	 * Run the received function to map the value A to B value, or an empty list as value if something goes wrong.
	 * Automatically runs Requirement validations.
	 *
	 * @param method
	 * 		- function that makes the mapping.
	 * @param <B>
	 * 		- type of the resultant object.
	 * @return value of B or empty list if something goes wrong.
	 */
	public <B> List<B> to(Function<A, Optional<B>> method) {
		return toOptional( method ).orElse( Collections.emptyList() );
	}

	/**
	 * Add a Requirement to be validated in all values of the list.
	 * If one fail, the requirement fail.
	 *
	 * @param predicate
	 * 		predicate of the requirement
	 * @return this own class
	 */
	public MappedList<A> whenAll(Predicate<A> predicate) {
		this.requirement.add( predicate );
		return this;
	}

	private <B> Optional<List<B>> toOptional(Function<A, Optional<B>> method) {
		return requirement.allValid( listA ) ? each( method ) : empty();
	}

	private <B> Optional<List<B>> each(Function<A, Optional<B>> method) {
		return Optional.of( listA.stream()
				.map( method )
				.filter( Optional::isPresent )
				.map( Optional::get )
				.collect( Collectors.toList() ) );
	}
}
