package com.nfym.apk.common;

import java.util.*;
import java.util.concurrent.*;

public class ProcessManager {
    // 使用线程安全的队列保存所有子进程
    private static final Queue<Process> processQueue = new ConcurrentLinkedQueue<>();

    // 注册 JVM 关闭钩子（确保退出时清理）
    static {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("正在清理子进程...");
            cleanupProcesses();
        }));
    }

    // 启动一个新进程，并加入管理队列
    public static void addProcess(Process process) throws Exception {
        processQueue.add(process);
//        System.out.println(process.info().commandLine().toString());
    }

    // 手动触发清理（也可在 JavaFX 的 stop() 中调用）
    public static void cleanupProcesses() {
        Process p;
        while ((p = processQueue.poll()) != null) {
            if (p.isAlive()) {
                System.out.println("正在终止进程: " + p.pid());
                p.destroyForcibly();
            }
        }
    }
}
