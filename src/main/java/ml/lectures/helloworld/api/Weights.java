package ml.lectures.helloworld.api;

/**
 * Edges
 *
 * @author <a href="mailto:oslautin@luxoft.com">Oleg N.Slautin</a>
 */
public interface Weights extends MetricsProvider {

    /**
     * Set input to hidden neuron weight
     * @param i - input layer index
     * @param h - hidden neuron in layer index
     * @param v - weight value
     * @return self instance
     */
    Weights i2h(int i, int h, double v);

    /**
     * Get input to hidden neuron weight
     * @param i - input layer index
     * @param h - hidden neuron in layer index
     * @return weight value
     */
    double i2h(int i, int h);

    /**
     * Get weight between hidden neurons
     * @param l - hidden layer index
     * @param h1 - neuron in layer index
     * @param h2 - neuron in next layer index
     * @return weight value
     */
    default double h2h(int l, int h1, int h2) {
        return 0.;
    }

    /**
     * Set weight between hidden neurons
     * @param l - hidden layer index
     * @param h1 - neuron in layer index
     * @param h2 - neuron in next layer index
     * @param v - weight value
     * @return self instance
     */
    default Weights h2h(int l, int h1, int h2, double v) {
        return this;
    }

    /**
     * Set bias to hidden neuron weight
     * @param l - hidden layer index
     * @param h - hidden neuron in layer index
     * @param v - weight value
     * @return self instance
     */
    Weights b2h(int l, int h, double v);

    /**
     * Get bias to hidden neuron weight
     * @param l - hidden layer index
     * @param h - hidden neuron in layer index
     * @return weight value
     */
    double b2h(int l, int h);

    /**
     * Set hidden to output weight
     * @param h - hidden neuron in layer index
     * @param o - output index
     * @param v - weight value
     * @return self instance
     */
    Weights h2o(int h, int o, double v);

    /**
     * Get hidden to output weight
     * @param h - hidden neuron in layer index
     * @param o - output index
     * @return weight value
     */
    double h2o(int h, int o);

}