package com.hch.main;

import com.hch.ClassFinder;

/**
 * Created with IntelliJ IDEA.
 * User: huaiwang
 * Date: 8/20/13
 * Time: 4:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class ClassFinderMain {
    public static void main(String[] args) {
        ClassFinder.getInstance().search("VelocityEngine", "D:\\program_data\\m2repository\\org\\apache\\velocity");
    }
}
