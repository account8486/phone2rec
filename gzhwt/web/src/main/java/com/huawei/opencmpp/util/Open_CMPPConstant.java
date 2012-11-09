package com.huawei.opencmpp.util;

import com.huawei.insa2.comm.cmpp.CMPPConstant;

public class Open_CMPPConstant extends CMPPConstant {
    public static String getCommandId(int code) {
        switch (code) {
            case 0x00000001:
                return "CMPP_CONNECT";
            case 0x80000001:
                return "CMPP_CONNECT_RESP";
            case 0x00000002:
                return "CMPP_TERMINATE";
            case 0x80000002:
                return "CMPP_TERMINATE_RESP";
            case 0x00000004:
                return "CMPP_SUBMIT";
            case 0x80000004:
                return "CMPP_SUBMIT_RESP";
            case 0x00000005:
                return "CMPP_DELIVER";
            case 0x80000005:
                return "CMPP_DELIVER_RESP";
            case 0x00000006:
                return "CMPP_QUERY";
            case 0x80000006:
                return "CMPP_QUERY_RESP";
            case 0x00000007:
                return "CMPP_CANCEL";
            case 0x80000007:
                return "CMPP_CANCEL_RESP";
            case 0x00000008:
                return "CMPP_ACTIVE_TEST";
            case 0x80000008:
                return "CMPP_ACTIVE_TEST_RESP";
            case 0x00000009:
                return "CMPP_FWD";
            case 0x80000009:
                return "CMPP_FWD_RESP";
            case 0x00000010:
                return "CMPP_MT_ROUTE";
            case 0x80000010:
                return "CMPP_MT_ROUTE_RESP";
            case 0x00000011:
                return "CMPP_MO_ROUTE";
            case 0x80000011:
                return "CMPP_MO_ROUTE_RESP";
            case 0x00000012:
                return "CMPP_GET_ROUTE";
            case 0x80000012:
                return "CMPP_GET_ROUTE_RESP";
            case 0x00000013:
                return "CMPP_MT_ROUTE_UPDATE";
            case 0x80000013:
                return "CMPP_MT_ROUTE_UPDATE_RESP";
            case 0x00000014:
                return "CMPP_M0_ROUTE_UPDATE";
            case 0x80000014:
                return "CMPP_MO_ROUTE_UPDATE_RESP";
            case 0x00000015:
                return "CMPP_PUSH_MT_ROUTE_UPDATE";
            case 0x80000015:
                return "CMPP_PUSH_MT_ROUTE_UPDATE_RESP";
            case 0x00000016:
                return "CMPP_PUSH_MO_ROUTE_UPDATE";
            case 0x80000016:
                return "CMPP_PUSH_MO_ROUTE_UPDATE_RESP";
            default:
                return "expect Command_Id code: " + code;
        }
    }
}
