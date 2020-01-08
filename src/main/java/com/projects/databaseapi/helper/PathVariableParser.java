package com.projects.databaseapi.helper;

import java.util.HashSet;
import java.util.Set;

public class PathVariableParser {
    /**
     * Translates provided String into List of Long numbers.
     * @param values String supposed to represent a set of positive numbers and/or ranges.
     * @return List of positive numbers.
     */
    public static Set<Long> getRange(String values) {
        String[] splitValues = values.split(",");
        Set<Long> list = new HashSet<>();

        try {
            for (String splitValue : splitValues) {
                if (!splitValue.contains("-")) {
                    list.add(Long.parseLong(splitValue));
                } else {
                    String[] idValuesRange = splitValue.split("-");

                    for (long i = Long.parseLong(idValuesRange[0]); i <= Long.parseLong(idValuesRange[idValuesRange.length - 1]); i++) {
                        list.add(i);
                    }
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return list;
    }
}
