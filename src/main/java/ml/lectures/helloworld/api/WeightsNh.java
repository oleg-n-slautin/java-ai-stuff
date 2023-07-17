/*
 *  Copyright 2020 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ml.lectures.helloworld.api;

import lombok.val;

import static java.util.Arrays.fill;

/**
 * HnWeights  description
 *
 * @author <a href="mailto:oslautin@luxoft.com">Oleg N.Slautin</a>
 */
public class WeightsNh implements Weights {

    private final double[][] i2h;
    private final double[][] h2o;
    private final double[][] h2h;
    private final double[][] b2h;
    private final NetMetrics metrics;

    public WeightsNh(final NetMetrics metrics) {

        this.metrics = metrics;
        val latesth = metrics.hwidth() - 1;
        i2h = new double[metrics.isize()][];
        for (int i = 0; i < i2h.length; i++) {
            i2h[i] = new double[metrics.hsize(0)];
            fill(i2h[i], 0.);
        }

        h2o = new double[metrics.hsize(latesth)][];
        for (int i = 0; i < h2o.length; i++) {
            h2o[i] = new double[metrics.osize()];
            fill(h2o[i], 0.);
        }

        h2h = new double[latesth][];
        for (int i = 0; i < h2h.length; i++) {
            h2h[i] = new double[metrics.hsize(i) * metrics.hsize(i + 1)];
            fill(h2h[i], 0.);
        }
        b2h = new double[latesth + 1][];
        for (int i = 0; i < b2h.length; i++) {
            b2h[i] = new double[metrics.hsize(i)];
            fill(b2h[i], 0.);
        }
    }

    @Override
    public double h2h(final int l, final int h1, final int h2) {
        return h2h[l][metrics.hsize(l) * h1 + h2];
    }

    @Override
    public Weights h2h(final int l, final int h1, final int h2, final double v) {
        h2h[l][metrics.hsize(l) * h1 + h2] = v;
        return this;
    }

    @Override
    public NetMetrics metrics() {
        return this.metrics;
    }

    @Override
    public Weights i2h(int i, int h, double v) {
        i2h[i][h] = v;
        return this;
    }

    @Override
    public double i2h(int i, int h) {
        return i2h[i][h] ;
    }

    @Override
    public Weights b2h(int l, int h, double v) {
        b2h[l][h] = v;
        return this;
    }

    @Override
    public double b2h(int l, int h) {
        return b2h[l][h];
    }

    @Override
    public Weights h2o(int h, int o, double v) {
        h2o[h][o] = v;
        return this;
    }

    @Override
    public double h2o(int h, int o) {
        return h2o[h][o];
    }
}