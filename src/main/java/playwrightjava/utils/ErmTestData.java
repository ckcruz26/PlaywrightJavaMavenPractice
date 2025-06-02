package playwrightjava.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;
import com.github.javafaker.Faker;

public class ErmTestData {

    public static final int HEIGHT = ThreadLocalRandom.current().nextInt(150, 211);
    public static final BigDecimal HEIGHT_IN_METERS = BigDecimal.valueOf(HEIGHT / 100.0).setScale(2, RoundingMode.HALF_UP);
    public static final int WEIGHT = ThreadLocalRandom.current().nextInt(50, 71);
    public static final int bloodTypeVal = ThreadLocalRandom.current().nextInt(1, 20);

    public static final String heightStr = String.valueOf(HEIGHT_IN_METERS);
    public static final String weightStr = String.valueOf(WEIGHT);
    public static final String bloodType = String.valueOf(bloodTypeVal);

    private static final Faker faker = new Faker();
    public static final String pagIbig = faker.numerify("%%%%-%%%%-%%%%");
    public static final String philHealth = "21-" + faker.numerify("%%%%%%%%%-");
    public static final String tinID = faker.numerify("%%%-%%%-%%%-%%%");

}
