package com.library.depending.utils;

/**
 * @author：zhangerpeng
 * 版本：
 * 日期：2019/4/12 22:24
 * 描述：
 *     地址  省
 */
public class Province {
  private   String id,name;

    public Province() {
    }

    public Province(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
