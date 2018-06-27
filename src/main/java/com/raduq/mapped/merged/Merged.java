package com.raduq.mapped.merged;

import static java.util.Optional.ofNullable;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Provides mapping between two complex objects.
 * The goal of this class is providing the <i>Mapped</i> to each field of a complex class.
 * Example:
 *
 * <pre>
 * class A {
 *     String valueA;
 * }
 * class B {
 *     String valueB;
 * }
 *
 * A a = new A();
 * B b = new B();
 * A mergedA = new Merged<>( a ).with( b ).and( b::getValueB, a::setValueA ).get();
 *
 * </pre>
 *
 * @param <A>
 * 		the type of the merged value
 */
public class Merged<A> {

	private A value;

	/**
	 * Construct a merged of the value received.
	 * If the value is null an <i>MergedException</i> will be thrown.
	 *
	 * @param value
	 * 		the value to be merged
	 */
	public Merged(A value) {
		this.value = ofNullable( value ).orElseThrow( MergeException::new );
	}

	/**
	 * Add a second value to be merged with.
	 *
	 * @param valueB
	 * 		a secondary value that can be matched with the original value
	 * @param <B>
	 * 		the type of the second value
	 * @return a instance of the merging class
	 */
	public <B> MergedWith<B> with(B valueB) {
		return new MergedWith<>( valueB );
	}

	/**
	 * Responsible for merging the value <i>B</i> with the original merged value <i>A</i>
	 *
	 * @param <B>
	 * 		the type of the secondary value
	 */
	public class MergedWith<B> {

		MergedWith(B valueB) {
			if (Objects.isNull( valueB ))
				throw new MergeException();
		}

		/**
		 * Add a condition to be validated before a method execution
		 *
		 * @param condition
		 * 		the conditional value
		 * @param method
		 * 		the method to be execute if the condition is true
		 * @param value
		 * 		the value to be executed used in the method
		 * @param <C>
		 * 		the type of the value used in the method
		 * @return this own class
		 */
		public <C> MergedWith<B> when(boolean condition, Consumer<C> method, C value) {
			if (condition)
				method.accept( value );
			return this;
		}

		/**
		 * Validates if the supplier method value is present, and executes the method.
		 *
		 * @param supplier
		 * 		supplier of the value to be validated
		 * @param method
		 * 		method to be executed if the value is true
		 * @param <C>
		 * 		the type of the value to be validated
		 * @return this own class
		 */
		public <C> MergedWith<B> and(Supplier<C> supplier, Consumer<C> method) {
			ofNullable( supplier.get() ).ifPresent( method );
			return this;
		}

		/**
		 * @return the final value of the <i>A</i> value
		 */
		public A get() {
			return value;
		}

	}

}
