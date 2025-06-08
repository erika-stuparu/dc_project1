package benchmark.cpu;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import benchmark.IBenchmark;

public class CPUDigitsOfPi implements IBenchmark {
    private int digits;
    private MathContext mc;

    @Override
    public void initialize(Object... params) {
        this.digits = (int) params[0];
        this.mc = new MathContext(digits + 5, RoundingMode.HALF_UP);
    }

    @Override
    public void warmup() {
        computePi(1000); // JVM warm-up
    }

    @Override
    public void run() {
        computePi(digits);
    }

    @Override
    public void run(Object... options) {
        run();
    }

    @Override
    public void cancel() {}

    @Override
    public void clean() {}

    private BigDecimal computePi(int digits) {
        MathContext context = new MathContext(digits + 5, RoundingMode.HALF_UP);
        BigDecimal arctan1_5 = arctan(new BigDecimal("0.2"), context);
        BigDecimal arctan1_239 = arctan(BigDecimal.ONE.divide(new BigDecimal("239"), context), context);
        BigDecimal pi = arctan1_5.multiply(new BigDecimal(4), context)
                .subtract(arctan1_239, context)
                .multiply(new BigDecimal(4), context);
        return pi.round(new MathContext(digits, RoundingMode.HALF_UP));
    }

    private BigDecimal arctan(BigDecimal x, MathContext mc) {
        BigDecimal result = BigDecimal.ZERO;
        BigDecimal term = x;
        BigDecimal xSquared = x.multiply(x, mc);
        int k = 1;

        // Add max iterations or precision limit
        while (term.abs().compareTo(BigDecimal.ONE.scaleByPowerOfTen(-mc.getPrecision())) > 0 && k < 10000) {
            if (k % 4 == 1) {
                result = result.add(term, mc);
            } else {
                result = result.subtract(term, mc);
            }

            k += 2;
            term = term.multiply(xSquared, mc).divide(new BigDecimal(k), mc);
        }

        return result;
    }

}
