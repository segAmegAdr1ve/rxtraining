package ru.artkorchagin.rxtraining.rx;

import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

/**
 * @author Arthur Korchagin (artur.korchagin@simbirsoft.com)
 * @since 20.11.18
 */
public class RxMaybeTraining {

    /* Тренировочные методы */

    /**
     * Эммит только 1 положительного элемента либо пустая последовательность
     *
     * @param value любое произвольное число
     * @return {@code Maybe} который эммитит значение {@code value} если оно положительное,
     * либо не эммитит ничего, если {@code value} отрицательное
     */
    Maybe<Integer> positiveOrEmpty(Integer value) {
        if(value > 0) {
            return Maybe.just(value);
        } else {
            return Maybe.empty();
        }
    }

    /**
     * Эммит только 1 положительного элемента либо пустая последовательность
     *
     * @param valueSingle {@link Single} который эммитит любое произвольное число
     * @return {@code Maybe} который эммитит значение из {@code valueSingle} если оно эммитит
     * положительное число, иначе не эммитит ничего
     */
    Maybe<Integer> positiveOrEmpty(Single<Integer> valueSingle) {
        return valueSingle.flatMapMaybe(new Function<Integer, MaybeSource<Integer>>() {
            @Override
            public MaybeSource<Integer> apply(Integer value) {
                if (value > 0) {
                    return Maybe.just(value);
                } else {
                    return Maybe.empty();
                }
            }
        });
    }

    /**
     * Сумма всех элементов последовательности
     *
     * @param integerObservable {@link Observable} произвольная последовательность чисел
     * @return {@link Maybe} который эммитит сумму всех элементов, либо не эммитит ничего если
     * последовательность пустая
     */
    Maybe<Integer> calculateSumOfValues(Observable<Integer> integerObservable) {
        return integerObservable.reduce(new BiFunction<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer value1, Integer value2) throws Exception {
                return value1 + value2;
            }
        });
    }

    /**
     * Если {@code integerMaybe} не эммитит элемент, то возвращать {@code defaultValue}
     *
     * @param defaultValue произвольное число
     * @return {@link Single} который эммитит значение из {@code integerMaybe}, либо
     * {@code defaultValue} если последовательность пустая
     */
    Single<Integer> leastOneElement(Maybe<Integer> integerMaybe, int defaultValue) {
        return integerMaybe.defaultIfEmpty(defaultValue).toSingle();
    }

}
