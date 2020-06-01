package com.git.demo;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class FileListenerDemo {

    public static void main(String[] args) {
        // 监控的目录
        String fileDir = "E:\\demo";
        // 轮询扫描的间隔时间
        long millis = TimeUnit.SECONDS.toMillis(5);

        // 过滤目录
        IOFileFilter directors = FileFilterUtils.and(FileFilterUtils.directoryFileFilter(), HiddenFileFilter.VISIBLE);

        // 过滤文件
        IOFileFilter files = FileFilterUtils.and(FileFilterUtils.fileFileFilter(), FileFilterUtils.suffixFileFilter(".txt"));

        // 组合
        IOFileFilter fileFilter = FileFilterUtils.or(directors, files);

        // 创建观察者,并使用过滤器
        FileAlterationObserver fileAlterationObserver = new FileAlterationObserver(new File(fileDir),fileFilter);
        // 不适用过滤器
//        FileAlterationObserver fileAlterationObserver = new FileAlterationObserver(new File(fileDir));

        // 添加文件监听器
        fileAlterationObserver.addListener(new FileListener());

        FileAlterationMonitor fileAlterationMonitor = new FileAlterationMonitor(millis,fileAlterationObserver);

        // 开始监控
        try {
            fileAlterationMonitor.start();
        } catch (Exception e) {
            System.out.println("出现异常....");
            e.printStackTrace();
        }
    }
}
