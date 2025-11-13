package generators;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

public class RandomData {

    public static String getValidUserName() {
        return RandomStringUtils.randomAlphabetic(4);
    }

    public static String getValidPassword() {
        return RandomStringUtils.randomAlphabetic(2).toLowerCase() +
                RandomStringUtils.randomAlphabetic(2).toUpperCase() +
                RandomStringUtils.randomNumeric(2) +
                ".$";
    }

    public static String getValidName() {
        return RandomStringUtils.randomAlphabetic(4) +
                " " +
                RandomStringUtils.randomAlphabetic(4);
    }

    public static double getValidRandomDepositValue() {
        var randomDouble = RandomUtils.nextDouble(0.01, 5000.);
        double roundedDouble = Math.round(randomDouble * 100.0) / 100.0;
        return roundedDouble;
    }

    public static double getValidMaxDepositValue() {
        return 5000.;
    }

    public static double getValidMinDepositValue() {
        return 0.01;
    }

    public static double getValidRandomTransferValue() {
        var randomDouble = RandomUtils.nextDouble(0.01, 10000.);
        double roundedDouble = Math.round(randomDouble * 100.0) / 100.0;
        return roundedDouble;
    }

    public static double getValidMaxTransferValue() {
        return 10000.;
    }

    public static double getValidMinTransferValue() {
        return 0.01;
    }
}
