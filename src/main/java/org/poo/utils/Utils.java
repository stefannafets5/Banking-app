package org.poo.utils;

import java.util.Random;

/**
 * The type Utils.
 */
public final class Utils {
    private Utils() {
        // Checkstyle error free constructor
    }

    private static final int IBAN_SEED = 1;
    private static final int CARD_SEED = 2;
    private static final int DIGIT_BOUND = 10;
    private static final int DIGIT_GENERATION = 16;
    private static final String RO_STR = "RO";
    private static final String POO_STR = "POOB";
    public static final int LIMIT = 500;
    public static final int SILVER_FEE = 250;
    public static final int STANDARD_FEE = 350;
    public static final int FEE = 100;
    public static final int RON_FOR_UPGRADE = 300;
    public static final int NUM_FOR_UPGRADE = 5;
    public static final int NUM_TR_FOR_DISCOUNT = 10;
    public static final double GOLD_100 = 0.995;
    public static final double GOLD_300 = 0.9945;
    public static final double GOLD_500 = 0.993;
    public static final double SILVER_100 = 0.997;
    public static final double SILVER_300 = 0.996;
    public static final double SILVER_500 = 0.995;
    public static final double STANDARD_100 = 0.999;
    public static final double STANDARD_300 = 0.998;
    public static final double STANDARD_500 = 0.9975;
    public static final double DISCOUNT_2 = 0.98;
    public static final double DISCOUNT_5 = 0.95;
    public static final double DISCOUNT_10 = 0.90;
    public static final double STANDARD_COMMISION = 0.002;
    public static final double SILVER_COMMISION = 0.001;
    public static final double ADULT_AGE = 21;

    private static Random ibanRandom = new Random(IBAN_SEED);
    private static Random cardRandom = new Random(CARD_SEED);

    /**
     * Utility method for generating an IBAN code.
     *
     * @return the IBAN as String
     */
    public static String generateIBAN() {
        StringBuilder sb = new StringBuilder(RO_STR);
        for (int i = 0; i < RO_STR.length(); i++) {
            sb.append(ibanRandom.nextInt(DIGIT_BOUND));
        }

        sb.append(POO_STR);
        for (int i = 0; i < DIGIT_GENERATION; i++) {
            sb.append(ibanRandom.nextInt(DIGIT_BOUND));
        }

        return sb.toString();
    }

    /**
     * Utility method for generating a card number.
     *
     * @return the card number as String
     */
    public static String generateCardNumber() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < DIGIT_GENERATION; i++) {
            sb.append(cardRandom.nextInt(DIGIT_BOUND));
        }

        return sb.toString();
    }

    /**
     * Resets the seeds between runs.
     */
    public static void resetRandom() {
        ibanRandom = new Random(IBAN_SEED);
        cardRandom = new Random(CARD_SEED);
    }
}
