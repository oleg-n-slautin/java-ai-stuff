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
 * WeightsMetrix  description
 *
 * @author <a href="mailto:oslautin@luxoft.com">Oleg N.Slautin</a>
 */
public interface NetMetrics {

    /**
     * Input layer size
     * @return input layer size
     */
    int isize();

    /**
     * Hidden layer size
     * @param l - hidden layer index
     * @return hidden layer size
     */
    int hsize(final int l);

    /**
     * Get hidden layers count
     * @return hidden layers count
     */
    default int hwidth() {
        return 1;
    }

    /**
     * Output layer size
     * @return output layer size
     */
    int osize();
}