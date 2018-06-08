package com.raduq.mapped;

import static java.util.Optional.empty;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.raduq.mapped.requirement.Requirement;

public class MappedList<A> {

	private List<A> listA;
	private Requirement<A> requirement;

	public MappedList(List<A> listA) {
		this.listA = listA;
		this.requirement = new Requirement<>();
	}

	public <B> List<B> to(Function<A, Optional<B>> method) {
		return toOptional( method ).orElse( Collections.emptyList() );
	}

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
