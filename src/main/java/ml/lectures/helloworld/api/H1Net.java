package ml.lectures.helloworld.api;

import lombok.val;

import java.util.function.Consumer;
import java.util.function.Function;

import static ml.lectures.helloworld.api.Utils.col;
import static ml.lectures.helloworld.api.Utils.mul;
import static ml.lectures.helloworld.api.Utils.oper;
import static ml.lectures.helloworld.api.Utils.row;
import static ml.lectures.helloworld.api.Utils.sum;
import static ml.lectures.helloworld.api.Utils.tran;

/**
 * OneLayerMachine
 * Expected results for xors
 * 6.008	0.771	-5.498	1.527	2.296	1.378	-8.862	4.210
 * epoch: 5000	error:	0.000
 * @author <a href="mailto:oslautin@luxoft.com">Oleg N.Slautin</a>
 */
public class H1Net implements LNet {

    private final LMath math;
    private final Function<Double, Double> activationFun;

    /**
     * Constructor
     * @param math - math
     */
    public H1Net(final LMath math) {
        this.math = math;
        activationFun = H1Net.this.math::activation;
    }

    public Function<double[], double[]> test(final Weights weights) {

        return input -> {
            val layers = newLayers(weights);
            val w2d = new Weights2d(weights);
            forward(input, w2d, layers);
            return layers.olayer().out();
        };
    }

    private H1Layers newLayers(final Weights weights) {

        return new H1Layers(weights.metrics(), activationFun);
    }

    @Override
    public Consumer<TrainSet> train(final Weights weights) {

        return set -> {
            val deltas = new Weights2d(new Weights1h(weights.metrics()));
            val layers = newLayers(weights);
            val w2d = new Weights2d(weights);
            set.forEach(
                (d, t) -> {
                    forward(d, w2d, layers);
                    backward(layers, w2d, deltas, t);
                }
            );
            fixWeights(w2d, deltas);
        };
    }

    private void fixWeights(final Weights2d weights, final Weights2d deltas) {

        weights.i2h(sum(weights.i2h(), deltas.i2h()));
        weights.b2h(sum(weights.b2h(), deltas.b2h()));
        weights.h2o(sum(weights.h2o(), deltas.h2o()));
    }

    private void forward(final double[] set,
                         final Weights2d weights,
                         final Layers layers) {

        layers.clean();
        val il = layers.ilayer();
        val hl = layers.hlayer();
        val bl = layers.blayer();
        val ol = layers.olayer();
        il.net(set);

        hl.net(
            row(
                sum(
                    mul(row(il.out()), weights.i2h()),
                    mul(row(bl.out()), weights.b2h())
                ),
                0
            )
        );

        ol.net(
            row(
                mul(row(hl.out()), weights.h2o()),
                0
            )
        );
    }

    private void backward(final Layers layers,
                          final Weights2d weights,
                          final Weights2d deltas,
                          final double[] target) {

        val hout = col(layers.hlayer().out());
        val iout = col(layers.ilayer().out());
        val bout = col(layers.blayer().out());
        val oout = col(layers.olayer().out());

        val errs = oper(oout, col(target), math::odelta);

        deltas.h2o(oper(mul(hout, errs), deltas.h2o(), math::dweight));
        val dhs = tran(oper(hout, mul(weights.h2o(), errs), math::hdelta));

        deltas.i2h(oper(mul(iout, dhs), deltas.i2h(), math::dweight));
        deltas.b2h(oper(mul(bout, dhs), deltas.b2h(), math::dweight));
    }
}
