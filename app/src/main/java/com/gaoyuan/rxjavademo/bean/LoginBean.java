package com.gaoyuan.rxjavademo.bean;

/**
 * Created by gaoyuan on 2017/3/6.
 */

public class LoginBean {


    /**
     * code : 200
     * message : 操作成功
     * data : {"is_auth":"2","is_bond":"2","token":"5408ea09f9cdf044ededde64f6f86a22"}
     */

    private int code;
    private String message;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * is_auth : 2
         * is_bond : 2
         * token : 5408ea09f9cdf044ededde64f6f86a22
         */

        private String is_auth;
        private String is_bond;
        private String token;

        public String getIs_auth() {
            return is_auth;
        }

        public void setIs_auth(String is_auth) {
            this.is_auth = is_auth;
        }

        public String getIs_bond() {
            return is_bond;
        }

        public void setIs_bond(String is_bond) {
            this.is_bond = is_bond;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
