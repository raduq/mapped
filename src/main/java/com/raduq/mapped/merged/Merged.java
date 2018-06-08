package com.raduq.mapped.merged;

import static java.util.Optional.ofNullable;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Merged<A> {

	private A value;

	public Merged(A value) {
		this.value = ofNullable( value ).orElseThrow( MergeException::new );
	}

	public <B> MergedWith<B> with(B valueB) {
		return new MergedWith<>( valueB );
	}

	public class MergedWith<B> {

		MergedWith(B valueB) {
			if (Objects.isNull( valueB ))
				throw new MergeException();
		}

		public <C> MergedWith<B> when(boolean condition, Consumer<C> method, C value) {
			if (condition)
				method.accept( value );
			return this;
		}

		public <C> MergedWith<B> and(Supplier<C> supplier, Consumer<C> method) {
			ofNullable( supplier.get() ).ifPresent( method );
			return this;
		}

		public A get() {
			return value;
		}

	}

}
