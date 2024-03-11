package com.example.farm.util;

public class WebSecurityUtils {
    public static final String[] publicMappingsPOST = {
            "/auth/login", "auth/refresh-token"
    };

    public static final String[] employeeMappingsGET = {
            "/employee/get-my-mark", "/logout"
    };

    public static final String[] employeeMappingsPOST = {
            "/employee/collect-product"
    };

    public static final String[] adminMappingsGET = {
            "/logout"
    };

    public static final String[] adminMappingsPUT = {
            "/admin/update/*"
    };

    public static final String[] adminMappingsPOST= {
            "/admin/register-employee", "/admin/delete-employee",
            "/admin/get-collected-products", "/set-mark-for-employee",
            "/set-norm-for-employee"
    };


}
