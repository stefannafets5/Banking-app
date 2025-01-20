package org.poo.checker;

/**
 * The class contains the minimum of constants needed.
 * You can define your own constants here or create separate files.
 */
public final class CheckerConstants {
    private CheckerConstants() {
    }

    /**
     * The constant BIG_TEST_POINTS.
     */
// checker constants
    public static final Integer BIG_TEST_POINTS = 3;
    /**
     * The constant MAXIMUM_ERROR_CHECKSTYLE.
     */
    public static final Integer MAXIMUM_ERROR_CHECKSTYLE = 30;
    /**
     * The constant CHECKSTYLE_POINTS.
     */
    public static final Integer CHECKSTYLE_POINTS = 10;

    /**
     * The constant CHECKER_PATH.
     */
    public static final String CHECKER_PATH = "src/main/resources";
    /**
     * The constant TESTS_PATH.
     */
    public static final String TESTS_PATH = "input/";
    /**
     * The constant OUT_PATH.
     */
    public static final String OUT_PATH = "result/out_";
    /**
     * The constant REF_PATH.
     */
    public static final String REF_PATH = "ref/ref_";
    /**
     * The constant RESULT_PATH.
     */
    public static final String RESULT_PATH = "result";
    /**
     * The constant OUT_FILE.
     */
    public static final String OUT_FILE = "out.txt";

    /**
     * The constant EMPTY_STR.
     */
    public static final String EMPTY_STR = "";
    /**
     * The constant DIGIT_REGEX.
     */
    public static final String DIGIT_REGEX = "\\D+";
    /**
     * The constant DECIMALS_REGEX.
     */
    public static final String DECIMALS_REGEX = "[0-9]+(\\.[0-9]+)? [A-Z]{3}";

    /**
     * The constant DECIMAL_POINTS.
     */
    public static final int DECIMAL_POINTS = 2;

    /**
     * The constant BASIC_START.
     */
    public static final int BASIC_START = 1;
    /**
     * The constant BASIC_END.
     */
    public static final int BASIC_END = 6;
    /**
     * The constant BASIC_POINTS.
     */
    public static final int BASIC_POINTS = 2;

    /**
     * The constant FUNCTIONAL_START.
     */
    public static final int FUNCTIONAL_START = 7;
    /**
     * The constant FUNCTIONAL_END.
     */
    public static final int FUNCTIONAL_END = 10;
    /**
     * The constant FUNCTIONAL_POINTS.
     */
    public static final int FUNCTIONAL_POINTS = 3;

    /**
     * The constant FLOW_START.
     */
    public static final int FLOW_START = 11;
    /**
     * The constant FLOW_END.
     */
    public static final int FLOW_END = 17;
    /**
     * The constant FLOW_POINTS.
     */
    public static final int FLOW_POINTS = 5;

    /**
     * The constant BIG_START.
     */
    public static final int BIG_START = 18;
    /**
     * The constant BIG_END.
     */
    public static final int BIG_END = 20;
    /**
     * The constant BIG_POINTS.
     */
    public static final int BIG_POINTS = 7;

    /**
     * The constant MAX_POINTS.
     */
    public static final int MAX_POINTS = 100;
    /**
     * The constant FIVE_POINTS.
     */
    public static final int FIVE_POINTS = 5;
    /**
     * The constant ZERO_POINTS.
     */
    public static final int ZERO_POINTS = 0;
    /**
     * The constant LEN_LONGEST_TEST_NAME.
     */
    public static final int LEN_LONGEST_TEST_NAME = 40;
}
