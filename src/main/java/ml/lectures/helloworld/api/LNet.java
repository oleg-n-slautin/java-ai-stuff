package ml.lectures.helloworld.api;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * LMachine  description
 *
 * @author <a href="mailto:oslautin@luxoft.com">Oleg N.Slautin</a>
 */
public interface LNet {

    /**
     * train
     * @param weights - weights
     * @return train set consumer
     */
    Consumer<TrainSet> train(Weights weights);

    /**
     * check
     * @param weights - weights
     * @return function to convert input to output data
     */
    Function<double[], double[]> test(Weights weights);
}