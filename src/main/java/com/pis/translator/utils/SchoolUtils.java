package com.pis.translator.utils;

import com.ibm.icu.text.Transliterator;
import com.pis.translator.entity.School;

import java.util.regex.Pattern;

public class SchoolUtils {

    private SchoolUtils() {}

    private static final Transliterator RULATIN = Transliterator.getInstance("Russian-Latin/BGN");
    private static final Transliterator BLRATIN = Transliterator.getInstance("Belarusian-Latin/BGN");
    private static final Pattern pattern = Pattern.compile("\\W+");

    public static void generateId(School school) {
        String id = "school_";
        String engName = school.getNameEng();
        if (engName != null && !engName.isEmpty()) {
            id = id + engName;
        } else {
            id = id + BLRATIN.transliterate(RULATIN.transliterate(school.getName()));
        }
        id = pattern.matcher(id).replaceAll("_")
                .replaceAll("_{2,}","_");
        school.setId(id);
    }
}
