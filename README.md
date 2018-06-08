# Mapped

Simple mapper utilities for java projects.

## Usage

### Mapped 

Receives a value of some type `A`, executes a method that returns a instance of `B`.

Example: 

- Receives a value `1` of type `Integer`.
- Receives a function `String.valueOf` that converts the `Integer` to a `String` value.
- The final result will be `1` as type of `String`

```java
String mapped = new Mapped<>( 1 ).to( integer -> Optional.of( String.valueOf( integer ) ) );
Assert.assertThat( mapped, equalTo( "1" ) );
```