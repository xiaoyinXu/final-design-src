package com.xxywebsite.mynote.bean;

public class RespBean {


    public static RespBean ok(String msg) {
        return new RespBean(200, msg, null);
    }

    public static RespBean ok(String msg, Object data) {
        return new RespBean(200, msg, data);
    }

    public static RespBean error(String msg) {
        return new RespBean(500, msg, null);
    }

    //{data:{},meta:{status:200,msg}}
    private Meta meta;
    private Object data;

    public RespBean(int status, String msg, Object data) {
        this.data = data;
        this.meta = new Meta(status, msg);
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    private class Meta {
        private int status;
        private String msg;

        public Meta(int status, String msg) {
            this.status = status;
            this.msg = msg;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}
