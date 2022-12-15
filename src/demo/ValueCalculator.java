package demo;

import java.util.Arrays;

public class ValueCalculator {
    static float[] arr = new float[1_000_000];
    static int arrLen = arr.length;
    static int arrHalf = arrLen / 2;

    public static void main(String[] args) {
        doTask();
    }

    static void doTask() {
        float[] a2 = new float[arrHalf];
        float[] a1 = new float[arrHalf];

        long start = System.currentTimeMillis();

        for (int i = 0; i < arrLen; i++) {
            arr[i] = 1;
        }

        System.arraycopy(arr, 0, a1, 0, arrHalf);

        System.arraycopy(arr, arrHalf, a2, 0, arrHalf);

        Thread t1 = new Thread(() -> {
            for (float i = 0; i < a1.length; i++) {
                a1[(int) i] = (float) (a1[(int) i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5)
                        * Math.cos(0.4f + i / 2));
                System.out.println("T1");
            }
        });
        t1.start();

        Thread t2 = new Thread(() -> {
            for (float i = 0; i < a2.length; i++) {
                a2[(int) i] = (float) (a1[(int) i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5)
                        * Math.cos(0.4f + i / 2));
                System.out.println("T2");
            }
        });
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.arraycopy(a1, 0, arr, 0, arrHalf);
        System.arraycopy(a2, 0, arr, arrHalf, arrHalf);

        System.out.println(Arrays.toString(arr));
        System.out.println("Time processing " + (System.currentTimeMillis() - start));
    }
}