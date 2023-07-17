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

import java.util.HashSet;

import static java.lang.String.format;
import static java.lang.System.currentTimeMillis;
import static java.lang.System.out;
import static ml.lectures.helloworld.TrainCommon.BPOINTS;
import static ml.lectures.helloworld.api.Utils.dumpLegend;
import static ml.lectures.helloworld.api.Utils.randomizeWeights;

/**
 * TrainQuarters
 *
 * @author <a href="mailto:oslautin@luxoft.com">Oleg N.Slautin</a>
 */
public class TrainQuarters {

    public static void main(String[] args) {

        val metrics = new H1Metrics(2, 4, 2);
        val net = new H1Net(new SigmoidMath(0.5, 1.0));
        val h1weights = new Weights1h(metrics);
        val weights1d = new Weights1d(h1weights);
        randomizeWeights(weights1d);
        dumpLegend(h1weights);

        final TrainSet set = consumer -> {
            for (double i = 0.; i <= 1.0; i += 0.2) {
                for (double j = 0.; j <= 1.0; j += 0.2) {
                    consumer.accept(
                        new double[] {i, j}, quarter(i, j)
                    );
                }
            }
        };
        train(net, BPOINTS, set, h1weights, 5000);
    }

    static void train(final LNet net,
                      final int[] bpoints,
                      final TrainSet set,
                      final Weights weights,
                      final int epochs) {

        val bp = new HashSet<>();
        for (int i: bpoints) {
            bp.add(i);
        }
        bp.add(epochs);
        val started = currentTimeMillis();
        val trainer = net.train(weights);
        val test = net.test(weights);
        for (int i = 1; i <= epochs; i++) {
            trainer.accept(set);
            if (bp.contains(i)) {
                val r0 = test.apply(new double[] {0.25, 0.25});
                val r1 = test.apply(new double[] {0.25, 0.75});
                val r2 = test.apply(new double[] {0.75, 0.75});
                val r3 = test.apply(new double[] {0.75, 0.25});
                out.println(format("epoch: %d" +
                        "\t[0.25, 0.25]=[%.3f,%.3f]" +
                        "\t[0.25, 0.75]=[%.3f,%.3f]" +
                        "\t[0.75, 0.75]=[%.3f,%.3f]" +
                        "\t[0.75, 0.25]=[%.3f,%.3f]",
                    i,
                    r0[0], r0[1],
                    r1[0], r1[1],
                    r2[0], r2[1],
                    r3[0], r3[1]));
            }
        }
        out.println(format("Timed\t%d", currentTimeMillis() - started));

    }

    //
    //   0, 1  |   1, 1
    //   ------+-------
    //   0, 0  |   1, 0
    //
    private static double[] quarter(final double i, final double j) {

        return new double[] {i <= 0.5 ? 0 : 1, j <= 0.5 ? 0 : 1};
    }
}