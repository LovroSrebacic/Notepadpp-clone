package main.java.util;

import java.net.URL;

public class Constants {
    public static final int WINDOW_HEIGHT = 700;
    public static final int WINDOW_WIDTH = 700;

    private static final String RED_DISKETTE_FILE = "redDiskette.png";
    private static final String GREEN_DISKETTE_FILE = "greenDiskette.png";
    private static final String HR_FLAG_FILE = "hr.png";
    private static final String EN_FLAG_FILE = "en.png";
    private static final String DE_FLAG_FILE = "de.png";

    public static URL getRedDisketteResource() {
        return Constants.class.getClassLoader().getResource(RED_DISKETTE_FILE);
    }

    public static URL getGreenDisketteResource() {
        return Constants.class.getClassLoader().getResource(GREEN_DISKETTE_FILE);
    }

    public static URL getHrFlagResource() {
        return Constants.class.getClassLoader().getResource(HR_FLAG_FILE);
    }

    public static URL getEnFlagResource() {
        return Constants.class.getClassLoader().getResource(EN_FLAG_FILE);
    }

    public static URL getDeFlagResource() {
        return Constants.class.getClassLoader().getResource(DE_FLAG_FILE);
    }
}
