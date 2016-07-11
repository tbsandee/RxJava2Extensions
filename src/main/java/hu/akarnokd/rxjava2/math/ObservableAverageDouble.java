/*
 * Copyright 2016 David Karnok
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package hu.akarnokd.rxjava2.math;

import hu.akarnokd.rxjava2.util.DeferredScalarObserver;
import io.reactivex.*;
import io.reactivex.internal.operators.observable.ObservableSource;

public class ObservableAverageDouble extends ObservableSource<Number, Double> {

    public ObservableAverageDouble(ObservableConsumable<Number> source) {
        super(source);
    }

    @Override
    protected void subscribeActual(Observer<? super Double> observer) {
        source.subscribe(new AverageDoubleObserver(observer));
    }
    
    static final class AverageDoubleObserver extends DeferredScalarObserver<Number, Double> {

        double accumulator;
        
        long count;
        
        public AverageDoubleObserver(Observer<? super Double> actual) {
            super(actual);
        }

        @Override
        public void onNext(Number value) {
            count++;
            accumulator += value.doubleValue();
        }
        
        @Override
        public void onComplete() {
            long c = count;
            if (c != 0) {
                complete(accumulator / c);
            } else {
                actual.onComplete();
            }
        }

    }
}