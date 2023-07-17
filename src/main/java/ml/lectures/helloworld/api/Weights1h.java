package ml.lectures.helloworld.api;

import static java.util.Arrays.fill;

/**
 * SimpleEdges  description
 *
 * @author <a href="mailto:oslautin@luxoft.com">Oleg N.Slautin</a>
 */
public class Weights1h implements Weights {

    private final double[] i2h;
    private final double[] h2o;
    private final double[] b2h;
    private final NetMetrics metrics;

    public Weights1h(final NetMetrics metrics) {

        this.metrics = metrics;
        i2h = new double[metrics.isize() * metrics.hsize(0)];
        h2o = new double[metrics.osize() * metrics.hsize(0)];
        b2h = new double[metrics.hsize(0)];
        fill(i2h, 0.);
        fill(h2o, 0.);
        fill(b2h, 0.);
    }

    @Override
    public NetMetrics metrics() {
        return this.metrics;
    }

    @Override
    public Weights i2h(int i, int h, double v) {
        i2h[offset(i, h)] = v;
        return this;
    }

    private int offset(final int i, final int h) {
        return i * metrics.hsize(0) + h;
    }

    @Override
    public double i2h(int i, int h) {
        return i2h[offset(i, h)];
    }

    @Override
    public Weights b2h(int l, int h, double v) {
        b2h[offset(l, h)] = v;
        return this;
    }

    @Override
    public double b2h(int l, int h) {
        return b2h[offset(l, h)];
    }

    @Override
    public Weights h2o(int h, int o, double v) {
        h2o[offset(o, h)] = v;
        return this;
    }

    @Override
    public double h2o(int h, int o) {
        return  h2o[offset(o, h)];
    }
}
