package com.example.farm.util;

public class WebSecurityUtils {
    public static final String[] publicMappingsPOST = {
            "/auth/login",
            "auth/refresh-token"
    };

    public static final String[] employeeMappingsGET = {
            "/employee/mark",
            "/employee/plan"
    };

    public static final String[] employeeMappingsPOST = {
            "/employee/collect-products"
    };

    public static final String[] adminMappingsPUT = {
            "/admin/edit/*"
    };

    public static final String[] adminMappingsPOST = {
            "/admin/register/employee",
            "/admin/register/product",
            "/admin/collected-products",
            "/admin/mark-for-employee",
            "/admin/plan-for-employee"
    };

    public static final String[] adminMappingsDELETE = {
            "/admin/employee"
    };
}
