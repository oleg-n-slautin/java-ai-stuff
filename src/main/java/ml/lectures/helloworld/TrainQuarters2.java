package ml.lectures.helloworld;

import lombok.val;
import ml.lectures.helloworld.api.H1Metrics;
import ml.lectures.helloworld.api.H1Net;
import ml.lectures.helloworld.api.Weights1h;
import ml.lectures.helloworld.api.LNet;
import ml.lectures.helloworld.api.SigmoidMath;
import ml.lectures.helloworld.api.TrainSet;
import ml.lectures.helloworld.api.Weights;
import ml.lectures.helloworld.api.Weights1d;
import org.apache.commons.lang3.mutable.MutableDouble;

import java.util.HashSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import static java.lang.String.format;
import static java.lang.System.currentTimeMillis;
import static java.lang.System.out;
import static ml.lectures.helloworld.TrainCommon.BPOINTS;
import static ml.lectures.helloworld.TrainCommon.deviation;
import static ml.lectures.helloworld.api.Utils.dump;
import static ml.lectures.helloworld.api.Utils.dumpLegend;

/**
 * TrainQuarters2
 *
 * @author <a href="mailto:oslautin@luxoft.com">Oleg N.Slautin</a>
 */
public class TrainQuarters2 {

    public static void main(String[] args) {

        val metrics = new H1Metrics(2, 4, 1);
        val net = new H1Net(new SigmoidMath(1.6, 1.0));
        val h1weights = new Weights1h(metrics);
//        randomizeWeights(weights);
        dumpLegend(h1weights);

        val weights1d = new Weights1d(h1weights);

        weights1d.i2h(0, new double[] {0.45, 0.45, 0.306, 0.312})
            .i2h(1, new double[] {0.746, 0.372, 0.713, 0.579})
            .b2h(0, new double[] {0.129, 0.617, 0.457, 0.355})
            .h2o(0, new double[] {0.398})
            .h2o(1, new double[] {0.103})
            .h2o(2, new double[] {0.626})
            .h2o(3, new double[] {0.159})
        ;

        dump(h1weights);

        final TrainSet set = consumer -> {
            for (double i = 0.; i <= 1.0; i += 0.2) {
                for (double j = 0.; j <= 1.0; j += 0.2) {
                    consumer.accept(
                        new double[] {i, j}, quarter(i, j)
                    );
                }
            }
        };
        train(net, BPOINTS, set, h1weights, 2500);
        dump(h1weights);
        test(
            net,
            h1weights,
            arr -> {
                if (arr[3] > 0.1) {
                    out.println(
                        format("[%.1f, %.1f]=[%.3f] E=%.3f", arr[0], arr[1], arr[2], arr[3])
                    );
                }
            }
        );
    }

    private static void test(final LNet net, final Weights weights, Consumer<double[]> consumer) {

        val toConsume = new double[4];
        val test = net.test(weights);
        for (double i = 0.; i <= 1.0; i += 0.1) {
            for (double j = 0.; j <= 1.0; j += 0.1) {
                toConsume[0] = i;
                toConsume[1] = j;
                val r = test.apply(new double[] {i, j})[0];
                val e = deviation(r, quarter(i, j)[0]);
                toConsume[2] = r;
                toConsume[3] = e;
                consumer.accept(toConsume);
            }
        }
    }

    static void train(final LNet net,
                      final int[] bpoints,
                      final TrainSet set,
                      final Weights weights,
                      final int epochs) {

        val train = net.train(weights);
        val bp = new HashSet<>();
        for (int i: bpoints) {
            bp.add(i);
        }
        bp.add(epochs);
        val started = currentTimeMillis();
        for (int i = 1; i <= epochs; i++) {
            train.accept(set);
            if (bp.contains(i)) {
                val error = new MutableDouble(0.);
                val cnt = new AtomicInteger(0);
                test(net, weights, arr -> {
                    error.add(arr[3]);
                    cnt.incrementAndGet();
                });
                out.println(format("epoch: %d" + "\tE: %.3f", i, error.doubleValue() / cnt.get()));
            }
        }
        out.println(format("Timed\t%d", currentTimeMillis() - started));

    }

    //
    //      0  |   1
    //   ------+-------
    //      1  |   0
    //
    private static double[] quarter(final double i, final double j) {

        return new double[] {(i <= 0.5 && j <= 0.5) || (i >= 0.5 && j >= 0.5) ? 1 : 0};
    }
}