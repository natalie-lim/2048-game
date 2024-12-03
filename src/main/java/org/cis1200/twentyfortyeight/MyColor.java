package org.cis1200.twentyfortyeight;

import com.sun.source.tree.Tree;

import java.awt.*;
import java.util.TreeMap;
// three themes: blue/purple, pinks, green/beige

public class MyColor {
    private static int SCHEME = 0;
    private static final TreeMap<Integer, Color> BLUE_MAP_0 = initMap(0);
    private static final TreeMap<Integer, Color> PINK_MAP_1 = initMap(1);
    private static final TreeMap<Integer, Color> GREEN_MAP_2 = initMap(2);
    private static final TreeMap<Integer, TreeMap<Integer, Color>> SCHEME_MAP = initSchemeMap();

    public static void setScheme(int scheme) {
        SCHEME = scheme;
    }

    private static TreeMap<Integer, TreeMap<Integer, Color>> initSchemeMap() {
        TreeMap<Integer, TreeMap<Integer, Color>> map = new TreeMap<>();
        map.put(0, BLUE_MAP_0);
        map.put(1, PINK_MAP_1);
        map.put(2, GREEN_MAP_2);
        return map;
    }

    private static TreeMap<Integer, Color> initMap(int col) {
        // 0 is blue, 1 is pink, 2 is green)
        TreeMap<Integer, Color> map = new TreeMap<>();
        if (col == 0) {
            map.put(2, new Color(125, 175, 215));
            map.put(4, new Color(185, 150, 255));
            map.put(8, new Color(145, 70, 255));
            map.put(16, new Color(30, 130, 255));
            map.put(32, new Color(180, 130, 255));
            map.put(64, new Color(30, 180, 255));
            map.put(128, new Color(180, 150, 255));
            map.put(256, new Color(97, 88, 255));
            map.put(512, new Color(97, 130, 255));
            map.put(1024, new Color(190, 90, 255));
            map.put(2048, new Color(120, 90, 255));
        } else if (col == 1) {
            map.put(2, new Color(255, 190, 230));
            map.put(4, new Color(215, 0, 255));
            map.put(8, new Color(255, 145, 210));
            map.put(16, new Color(255, 200, 190));
            map.put(32, new Color(235, 135, 230));
            map.put(64, new Color(210, 100, 235));
            map.put(128, new Color(255, 200, 200));
            map.put(256, new Color(255, 90, 215));
            map.put(512, new Color(200, 100, 155));
            map.put(1024, new Color(210, 50, 125));
            map.put(2048, new Color(255, 90, 255));
        } else {
            map.put(2, new Color(171, 189, 154));
            map.put(4, new Color(0, 50, 0));
            map.put(8, new Color(50, 90, 0));
            map.put(16, new Color(222, 184, 135));
            map.put(32, new Color(90, 100, 70));
            map.put(64, new Color(160, 82, 45));
            map.put(128, new Color(25, 60, 0));
            map.put(256, new Color(139, 154, 113));
            map.put(512, new Color(57, 120, 71));
            map.put(1024, new Color(218, 165, 32));
            map.put(2048, new Color(80, 150, 71));
        }
        return map;
    }

    public static Color getColor(int value) {
        return SCHEME_MAP.get(SCHEME).get(value);
    }
}
