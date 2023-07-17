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

/**
 * Util class to operate with 2d-array weights
 *
 * @author <a href="mailto:oslautin@luxoft.com">Oleg N.Slautin</a>
 */
public class Weights2d implements MetricsProvider {

    private final Weights1d nested;
    private final NetMetrics metrics;

    /**
     * Constructor
     * @param weights - weights
     */
    public Weights2d(final Weights weights) {
        this(new Weights1d(weights));
    }

    /**
     * Constructor
     * @param weights - weights
     */
    public Weights2d(final Weights1d weights) {
        this.nested = weights;
        this.metrics = weights.metrics();
    }

    public Weights1d nested() {
        return nested;
    }

    public NetMetrics metrics() {
        return metrics;
    }

    /**
     * Get hidden to hidden weights
     * @param l - layer index
     * @return hidden to hidden weights
     */
    double[][] h2h(final int l) {
        val ret = new double[metrics.hsize(l)][];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = nested.h2h(l, i);
        }
        return ret;
    }

    /**
     * Set hidden to hidden weights
     * @param l - layer index
     * @param v - hidden to hidden weights
     * @return self instance
     */
    public Weights2d h2h(final int l, double[][] v) {
        for (int i = 0; i < metrics.isize(); i++) {
            nested.h2h(l, i, v[i]);
        }
        return this;
    }

    /**
     * Get input to hidden weights
     * @return input to hidden weights
     */
    public double[][] i2h() {
        val ret = new double[metrics.isize()][];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = nested.i2h(i);
        }
        return ret;
    }

    /**
     * Set input to hidden weights
     * @param v - input to hidden weights
     * @return self instance
     */
    public Weights2d i2h(double[][] v) {
        for (int i = 0; i < v.length; i++) {
            nested.i2h(i, v[i]);
        }
        return this;
    }

    /**
     * Set bias to hidden weights
     * @param v - bias to hidden weights
     * @return self instance
     */
    public Weights2d b2h(double[][] v) {
        for (int i = 0; i < v.length; i++) {
            nested.b2h(i, v[i]);
        }
        return this;
    }

    /**
     * Get bias to hidden weights
     * @return bias to hidden weights
     */
    public double[][] b2h() {
        val ret = new double[metrics.hwidth()][];
        for (int l = 0; l < ret.length; l++) {
            ret[l] = nested.b2h(l);
        }
        return ret;
    }

    /**
     * Set hidden to output weights
     * @param v - hidden to output weights
     * @return self instance
     */
    public Weights2d h2o(final double[][] v) {
        for (int h = 0; h < v.length; h++) {
            nested.h2o(h, v[h]);
        }
        return this;
    }

    /**
     * Get hidden to output weights
     * @return bias to hidden weights
     */
    public double[][] h2o() {
        val ret = new double[metrics.hsize(metrics.hwidth() - 1)][];
        for (int h = 0; h < ret.length; h++) {
            ret[h] = nested.h2o(h);
        }
        return ret;
    }
}