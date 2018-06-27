package com.raduq.mapped.requirement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * Responsible to hold a predicate function that is correspondent to one or more mandatory validations.
 *
 * @param <T>
 * 		type of the object been validated
 */
public class Requirement<T> {

	private List<Predicate<T>> requirements;

	/**
	 * Construct a requirement with a default <i>Objects::nonNull</i> validation.
	 */
	public Requirement() {
		this.requirements = defaultRequirements();
	}

	/**
	 * Add a new predicate to the requirement list.
	 *
	 * @param condition
	 * 		predicate function of T value
	 * @return this own class with the added requirement
	 */
	public Requirement add(Predicate<T> condition) {
		this.requirements.add( condition );
		return this;
	}

	/**
	 * Validate if the requirement matches in all values of the received list.
	 *
	 * @param values
	 * 		the list of values to be validated
	 * @return true: all values matches the requirements - false: one or more didn't match the requirements
	 */
	public boolean allValid(List<T> values) {
		return Objects.nonNull( values ) && values.stream().allMatch( this::isValid );
	}

	/**
	 * Validate if the received value match the requirements
	 *
	 * @param value
	 * 		a value of type T to be validated
	 * @return true: the value match the requirements - false: the value don't match the requirements
	 */
	public boolean isValid(T value) {
		return requirements.stream().allMatch( requirement -> requirement.test( value ) );
	}

	private List<Predicate<T>> defaultRequirements() {
		return new ArrayList<>( Collections.singletonList( Objects::nonNull ) );
	}
}
