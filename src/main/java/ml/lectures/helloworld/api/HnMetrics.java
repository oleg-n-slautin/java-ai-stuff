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

/**
 * HnMetrics  description
 *
 * @author <a href="mailto:oslautin@luxoft.com">Oleg N.Slautin</a>
 */
public class HnMetrics implements NetMetrics {

    private final int isize;
    private final int[] hsizes;
    private final int osize;

    /**
     * Constructor
     * @param isize - input layer size
     * @param hsizes - hidden layers's sizes
     * @param osize - output layer size
     */
    public HnMetrics(final int isize, final int[] hsizes, final int osize) {
        this.isize = isize;
        this.hsizes = hsizes;
        this.osize = osize;
    }

    @Override
    public int isize() {
        return isize;
    }

    @Override
    public int hsize(final int l) {
        return hsizes[l];
    }

    @Override
    public int hwidth() {
        return hsizes.length;
    }

    @Override
    public int osize() {
        return osize;
    }
}