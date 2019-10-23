package com.learning.cloud.ding.picture.entity;

public class WangEditorUtil {
    public static WangEditor success(String[] object) {
        WangEditor we = new WangEditor();
        we.setErrno(0);
        we.setData(object);
        return we;
    }

    public static WangEditor success() {
        return success(null);
    }

    public static WangEditor error(){
        WangEditor we = new WangEditor();
        we.setErrno(-1);
        return we;
    }

}
