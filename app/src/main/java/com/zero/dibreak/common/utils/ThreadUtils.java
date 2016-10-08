/*
 * The GPL License (GPL)
 *
 * Copyright (c) 2016 MarkZhai (http://zhaiyifan.cn)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.zero.dibreak.common.utils;

import android.os.Handler;
import android.os.Looper;
import android.util.Printer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 线程相关工具类
 *
 * @author markzhai on 16/3/4
 * @version 1.0.0
 */
public final class ThreadUtils {

    /**
     * Map to remember looper' printer wrappers. Use WeakHashMap to avoid
     * hold looper instance permanently.
     */
    private final static WeakHashMap<Looper, PrinterWrapper> sLooperPrinters =
            new WeakHashMap<Looper, PrinterWrapper>();
    private static Thread sMainThread = Looper.getMainLooper().getThread();
    private static Handler sMainHandler = new Handler(Looper.getMainLooper());

    private ThreadUtils() {
        // static usage.
    }

    /**
     * <p>Causes the Runnable to be added to the <b>MAIN</b> message queue.
     * The runnable will be run on the user interface thread.</p>
     *
     * @param r The Runnable that will be executed.
     * @see #postDelayed
     * @see #removeCallbacks
     */
    public static void post(Runnable r) {
        sMainHandler.post(r);
    }

    /**
     * <p>Causes the Runnable to be added to the <b>MAIN</b> message queue, to be run
     * after the specified amount of time elapses.
     * The runnable will be run on the user interface thread.</p>
     *
     * @param r           The Runnable that will be executed.
     * @param delayMillis The delay (in milliseconds) until the Runnable
     *                    will be executed.
     * @see #post
     * @see #removeCallbacks
     */
    public static void postDelayed(Runnable r, long delayMillis) {
        sMainHandler.postDelayed(r, delayMillis);
    }

    /**
     * <p>Removes the specified Runnable from the <b>MAIN</b> message queue.</p>
     *
     * @param r The Runnable to remove from the message handling queue
     * @see #post
     * @see #postDelayed
     */
    public static void removeCallbacks(Runnable r) {
        sMainHandler.removeCallbacks(r);
    }

    /**
     * Run this runnable in  <b>MAIN</b> ui thread.
     *
     * @param runnable Runnable to run.
     */
    public static void runOnUiThread(Runnable runnable) {
        if (isMainThread()) {
            runnable.run();
        } else {
            post(runnable);
        }
    }

    /**
     * Whether current thread is <b>MAIN</b> ui thread.
     */
    public static boolean isMainThread() {
        return sMainThread == Thread.currentThread();
    }

    /**
     * Get the <b>MAIN</b> handler.
     *
     * @return Main handler.
     */
    public static Handler getMainHandler() {
        return sMainHandler;
    }

    /**
     * Add a printer to current thread's looper.
     *
     * @param printer Looper printer.
     */
    public static void addLooperPrinter(Printer printer) {
        addLooperPrinter(Looper.myLooper(), printer);
    }

    /**
     * Add a printer to corresponding looper.
     *
     * @param looper  Looper.
     * @param printer Looper printer.
     */
    public static void addLooperPrinter(Looper looper, Printer printer) {
        if (looper == null) {
            throw new RuntimeException("null looper");
        }
        PrinterWrapper wrapper;
        synchronized (sLooperPrinters) {
            wrapper = sLooperPrinters.get(looper);
            if (wrapper == null) {
                wrapper = new PrinterWrapper();
                sLooperPrinters.put(looper, wrapper);
                // set looper' message logging.
                looper.setMessageLogging(wrapper);
            }
        }
        wrapper.add(printer);
    }

    /**
     * Printer wrapper which allows multiple printers.
     */
    final static class PrinterWrapper implements Printer {

        // wrapped printers, can ONLY be accessed within #println (thus corresponding looper thread).
        private final List<Printer> mWrappedPrinters = new ArrayList<Printer>();
        // pending printers, which is used to hold pending printers (waiting to be wrapped).
        private final List<Printer> mPendingPrinters = new LinkedList<Printer>();
        private final AtomicBoolean mHasPendingPrinter = new AtomicBoolean(false);

        PrinterWrapper() {
        }

        @Override
        public void println(String x) {
            // deal with pending printer, try to avoid synchronize.
            if (mHasPendingPrinter.getAndSet(false)) {
                synchronized (mPendingPrinters) {
                    mWrappedPrinters.addAll(mPendingPrinters);
                    mPendingPrinters.clear();
                }
            }
            // print.
            for (Printer printer : mWrappedPrinters) {
                printer.println(x);
            }
        }

        public void add(Printer printer) {
            synchronized (mPendingPrinters) {
                mPendingPrinters.add(printer);
            }
            mHasPendingPrinter.set(true);
        }
    }
}
