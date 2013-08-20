package com.hch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: huaiwang
 * Date: 8/20/13
 * Time: 3:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class ClassFinder {
    private static final Logger log = LoggerFactory.getLogger(ClassFinder.class);
    private final static ClassFinder ins = new ClassFinder();
    private String searchTarget = ".";

    public static ClassFinder getInstance() {
        return ins;
    }


    public void search(String regex, String searchTarget) {
        Pattern pattern = Pattern.compile(regex);

        File file = new File(searchTarget);

        if (!file.exists()) return;

        if (file.isDirectory()) {
            searchInDirectory(pattern, file);
        } else if (file.getName().endsWith(".jar")) {
            searchInJar(pattern, file);
        } else {
            log.info("ignore: " + file.getAbsolutePath());
        }
    }

    private void searchInDirectory(Pattern regex, File directory) {
        File[] files = directory.listFiles();

        for (File file : files) {
            if (file.isDirectory()) {
                searchInDirectory(regex, file);
            } else if (file.getName().endsWith(".jar")) {
                searchInJar(regex, file);
            } else {
                log.info("ignore: " + file.getAbsolutePath());
            }
        }
    }

    private void searchInJar(Pattern regex, File jar) {
        JarFile jarFile = null;

        try {
            jarFile = new JarFile(jar);
        } catch (IOException e) {
            log.error(e.getMessage());
            return;
        }

        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            String name = entry.getName();
            Matcher matcher = regex.matcher(name);
            if(matcher.find()) {
                log.info("find [" + name + "] in [" + jar.getAbsolutePath() + "]");
            }
        }
    }
}
