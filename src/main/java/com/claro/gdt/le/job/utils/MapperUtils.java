package com.claro.gdt.le.job.utils;

import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MapperUtils {

	private MapperUtils() {
		// should never be instantiated
	}

	public static <T, R> List<R> mapList(@Nullable List<T> list, Function<T, R> mapper) {
		return CollectionUtils.isEmpty(list) ? null : list.stream().map(mapper).collect(Collectors.toList());
	}

	public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
		Map<Object, Boolean> seen = new ConcurrentHashMap<>();
		return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}

	public static <T> List<T> filterListByKey(@Nullable List<T> list, Function<? super T, ?> keyExtractor) {
		return list == null ? null : list.stream().filter(distinctByKey(keyExtractor)).collect(Collectors.toList());
	}

	public static <T, R> void updateFieldIfChanged(T originalValues, T newValues, Function<T, R> fieldGetter,
			Consumer<R> fieldSetter) {
		if (ObjectUtils.isEmpty(fieldGetter.apply(newValues))
				|| fieldGetter.apply(originalValues) == fieldGetter.apply(newValues)
				|| Objects.equals(fieldGetter.apply(originalValues), fieldGetter.apply(newValues))) {
			return;
		}
		fieldSetter.accept(fieldGetter.apply(newValues));
	}


}
