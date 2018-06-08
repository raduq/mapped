package com.raduq.mapped;

import static java.util.Optional.empty;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import com.raduq.mapped.requirement.Requirement;

public class Mapped<A> {

	private A valueA;
	private Requirement<A> requirement;

	public Mapped(A valueA) {
		this.valueA = valueA;
		this.requirement = new Requirement<>();
	}

	public <B> Optional<B> toOptional(Function<A, Optional<B>> method) {
		return requirement.isValid( valueA ) ? method.apply( valueA ) : empty();
	}

	public <B> B to(Function<A, Optional<B>> method) {
		return toOptional( method ).orElse( null );
	}

	public Mapped<A> when(Predicate<A> condition) {
		this.requirement.add( condition );
		return this;
	}

}
