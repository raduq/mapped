package com.raduq.mapped.requirement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class Requirement<T> {

	private List<Predicate<T>> requirements;

	public Requirement() {
		this.requirements = defaultRequirements();
	}

	public Requirement add(Predicate<T> condition) {
		this.requirements.add( condition );
		return this;
	}

	public boolean allValid(List<T> values) {
		return Objects.nonNull( values ) && values.stream().allMatch( this::isValid );
	}

	public boolean isValid(T value) {
		return requirements.stream().allMatch( requirement -> requirement.test( value ) );
	}

	private List<Predicate<T>> defaultRequirements() {
		return new ArrayList<>( Collections.singletonList( Objects::nonNull ) );
	}
}
