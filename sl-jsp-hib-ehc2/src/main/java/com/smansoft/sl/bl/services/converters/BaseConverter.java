/**
 * 
 */
package com.smansoft.sl.bl.services.converters;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author SMan
 *
 */
public interface BaseConverter<A, B> extends Function<A, B> {

	/**
	 * 
	 * @param input
	 * @return
	 */
	default List<B> convertToList(final List<A> input) {
		return input.stream().map(this::apply).collect(Collectors.toList());
	}
}
