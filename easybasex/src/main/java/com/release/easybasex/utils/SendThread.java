package com.release.easybasex.utils;

/**
 * @author Mr.release
 * @create 2020/9/9
 * @Describe
 */

public class SendThread extends Thread {
    private static SendThread sendThread;
    private boolean isRun = true;//是否结束线程的标志位
    private byte[] data;//需要发送的字节流

    private SendThread() {
    }

    public static SendThread getInstance() {
        if (sendThread == null) {
            sendThread = new SendThread();
            sendThread.start();
            System.out.println("新建了一个发送线程");
        } else {
            System.out.println("使用已有的发送线程");
        }
        return sendThread;
    }

    @Override
    public void run() {
        try {
            synchronized (this) {
                while (isRun) {
                    System.out.println("线程开始运行");
                    wait();
                    System.out.println("线程被唤醒");
                    if (mRunCallback != null)
                        mRunCallback.runing(data);
                }
            }
            System.out.println("线程结束");
            sendThread = null;
        } catch (InterruptedException e) {
            sendThread = null;
            e.printStackTrace();
        }
    }

    private RunCallback mRunCallback;

    public interface RunCallback {
        void runing(byte[] data);
    }

    public void setRunCallback(RunCallback runCallback) {
        this.mRunCallback = runCallback;
    }

    //唤醒线程
    public synchronized void setNotify() {
        notify();
    }

    public boolean isRun() {
        return isRun;
    }

    public void setRun(boolean isRun) {
        this.isRun = isRun;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

}