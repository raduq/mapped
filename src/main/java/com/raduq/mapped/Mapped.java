package com.raduq.mapped;

import static java.util.Optional.empty;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import com.raduq.mapped.requirement.Requirement;

/**
 * Provides mapping between two objects.
 * Automatically runs Requirement validations with a default avoiding NullPointers.
 *
 * @param <A>
 * 		type of the object to be converted to <B>
 */
public class Mapped<A> {

	private A valueA;
	private Requirement<A> requirement;

	/**
	 * Constructs a Mapped object.
	 *
	 * @param valueA
	 * 		value to be converted
	 */
	public Mapped(A valueA) {
		this.valueA = valueA;
		this.requirement = new Requirement<>();
	}

	/**
	 * Run the received function to map the value A to a Optional of a value B.
	 * Automatically runs Requirement validations.
	 *
	 * @param method
	 * 		function that makes the mapping.
	 * @param <B>
	 * 		type of the resultant object.
	 * @return an Optional of a B object or Optional.empty if something goes wrong.
	 */
	public <B> Optional<B> toOptional(Function<A, Optional<B>> method) {
		return requirement.isValid( valueA ) ? method.apply( valueA ) : empty();
	}

	/**
	 * Run the received function to map the value A to B value, or Null value if something goes wrong.
	 * Automatically runs Requirement validations.
	 *
	 * @param method
	 * 		- function that makes the mapping.
	 * @param <B>
	 * 		- type of the resultant object.
	 * @return value of B or null if something goes wrong.
	 */
	public <B> B to(Function<A, Optional<B>> method) {
		return toOptional( method ).orElse( null );
	}

	/**
	 * Add a validation to be ran before the mapping method runs.
	 * There's no way to ensure the run order of the requirement validations.
	 *
	 * @param condition
	 * 		a predicate to value A to be ran
	 * @return this own Mapped object with the new added validation added.
	 */
	public Mapped<A> when(Predicate<A> condition) {
		this.requirement.add( condition );
		return this;
	}

}
