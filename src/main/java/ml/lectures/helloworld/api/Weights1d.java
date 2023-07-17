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
 * Util class to operate with 1d-array weights
 *
 * @author <a href="mailto:oslautin@luxoft.com">Oleg N.Slautin</a>
 */
public class Weights1d implements MetricsProvider {
    
    private final Weights nested;
    private final NetMetrics metrics;

    /**
     * Constructor
     * @param weights - weights
     */
    public Weights1d(final Weights weights) {
        this.nested = weights;
        this.metrics = weights.metrics();
    }

    /**
     * Get hidden layers count
     * @return hidden layers count
     */
    public NetMetrics metrics() {
        return this.metrics;
    }

    public Weights nested() {
        return nested;
    }

    /**
     * Set hidden to hidden weights
     * @param l - layer index
     * @param h - index in hidden layer
     * @param v - weights
     * @return self instance
     */
    public Weights1d h2h(int l, int h, double[] v) {
        for (int h1 = 0; h1 < v.length; h1++) {
            nested.h2h(l, h, h1, v[h1]);
        }
        return this;
    }

    /**
     * Get hidden to hidden weights
     * @param l - layer index
     * @param h - index in hidden layer
     * @return hidden to hidden weights
     */
    double[] h2h(final int l, int h) {
        val ret = new double[metrics.hsize(l)];
        for (int h1 = 0; h1 < ret.length; h1++) {
            ret[h1] = nested.h2h(l, h, h1);
        }
        return ret;
    }

    /**
     * Get input to hidden weights
     * @param i - input index
     * @return input to hidden weights
     */
    public double[] i2h(int i) {
        val ret = new double[metrics.hsize(0)];
        for (int h = 0; h < ret.length; h++) {
            ret[h] = nested.i2h(i, h);
        }
        return ret;
    }

    /**
     * Set input to hidden weights
     * @param i - input index
     * @param v - weights
     * @return self instance
     */
    public Weights1d i2h(int i, double[] v) {
        for (int h = 0; h < v.length; h++) {
            nested.i2h(i, h, v[h]);
        }
        return this;
    }

    /**
     * Set bias to hidden weights
     * @param l - layer index
     * @param v - weights
     * @return self instance
     */
    public Weights1d b2h(int l, double[] v) {
        for (int i = 0; i < v.length; i++) {
            nested.b2h(l, i, v[i]);
        }
        return this;
    }

    /**
     * Get bias to hidden weights
     * @param l - layer index
     * @return bias to hidden weights
     */
    public double[] b2h(int l) {
        val ret = new double[metrics.hsize(l)];
        for (int h = 0; h < ret.length; h++) {
            ret[h] = nested.b2h(l, h);
        }
        return ret;
    }

    /**
     * Set hidden to output weights
     * @param h - hidden index
     * @param v - weights
     * @return self instance
     */
    public Weights1d h2o(int h, double[] v) {

        for (int i = 0; i < v.length; i++) {
            nested.h2o(h, i, v[i]);
        }
        return this;
    }

    /**
     * Get hidden to output weights
     * @param h - hidden index
     * @return hidden to output weights
     */
    public double[] h2o(int h) {
        val ret = new double[metrics.osize()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = nested.h2o(h, i);
        }
        return ret;
    }
}