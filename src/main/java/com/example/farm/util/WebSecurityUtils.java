package com.example.farm.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class WebSecurityUtils {
    public static final String[] publicMappings = {
            "/login"
    };
    public static final String[] employeeMappingsGET = {

    };
    public static final String[] employeeMappingsPOST = {

    };
    public static final String[] adminMappingsGET = {
        "/admin**"
    };
    public static final String[] adminMappingsPOST = {
        "/admin**"
    };
}
