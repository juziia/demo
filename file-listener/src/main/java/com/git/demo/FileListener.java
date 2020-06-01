package com.git.demo;

import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;

/**
 *  监听文件的修改,删除,增加
 */
public class FileListener extends FileAlterationListenerAdaptor {

    @Override
    public void onStart(FileAlterationObserver observer) {
        System.out.println("启动监听......");
        super.onStart(observer);
    }

    @Override
    public void onStop(FileAlterationObserver observer) {
        System.out.println("监听结束.......");
        super.onStop(observer);
    }

    @Override
    public void onDirectoryCreate(File directory) {
        System.out.println("创建了目录: "+ directory.getName());
        System.out.println("目录大小: "+directory.length());
        System.out.println("目录的路径: "+directory.getPath());
        System.out.println("目录下的所有文件"+ directory.listFiles());
    }

    @Override
    public void onDirectoryChange(File directory) {
        System.out.println("修改了目录: "+ directory.getName());
        System.out.println("目录大小: "+directory.length());
        System.out.println("目录的路径: "+directory.getPath());
        System.out.println("目录下的所有文件"+ directory.listFiles());
    }

    @Override
    public void onDirectoryDelete(File directory) {
        System.out.println("删除了目录: "+ directory.getName());
        System.out.println("目录大小: "+directory.length());
        System.out.println("目录的路径: "+directory.getPath());
        System.out.println("目录下的所有文件"+ directory.listFiles());
    }

    @Override
    public void onFileCreate(File file) {
        System.out.println("创建文件: "+ file.getName());
        System.out.println("文件大小: "+file.length());
        System.out.println("文件的路径: "+file.getPath());
    }

    @Override
    public void onFileChange(File file) {

        System.out.println("文件被修改: "+ file.getName());
        System.out.println("文件大小: "+file.length());
        System.out.println("文件的路径: "+file.getPath());

    }

    @Override
    public void onFileDelete(File file) {
        System.out.println("文件被删除: "+file.getName());
        System.out.println("文件大小: "+file.length());
        System.out.println("文件的路径: "+file.getPath());
    }
}
