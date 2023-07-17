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
 * H1Metrics  description
 *
 * @author <a href="mailto:oslautin@luxoft.com">Oleg N.Slautin</a>
 */
public class H1Metrics implements NetMetrics {

    private final int isize;
    private final int h0size;
    private final int osize;

    /**
     * Constructor
     * @param isize - input layer size
     * @param h0size - h[0] layer size
     * @param osize - output layer size
     */
    public H1Metrics(final int isize,
                     final int h0size,
                     final int osize) {

        this.isize = isize;
        this.h0size = h0size;
        this.osize = osize;
    }

    @Override
    public int isize() {
        return isize;
    }

    @Override
    public int hsize(final int l) {
        return h0size;
    }

    @Override
    public int osize() {
        return osize;
    }
}