package com.kevin.courserefactor.base.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class URL {


    public static String decodeParam(String str) {
        try {
            return URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    public static List<Integer> decodeIntList(String str) {
        try {
            return Arrays.asList(str.split(","))
                    .stream()
                    .map(value -> Integer.parseInt(value))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
