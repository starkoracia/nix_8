package ua.com.alevel.util;

import java.math.BigDecimal;

public class CollectionUtil {
    private CollectionUtil() { }

    public static String arrayToString(Object[] a) {
        if (a == null)
            return "null";

        int iMax = a.length - 1;
        if (iMax == -1)
            return "[]";

        StringBuilder b = new StringBuilder();
        b.append('[');
        for (int i = 0; ; i++) {
            b.append(String.valueOf(a[i]));
            if (i == iMax)
                return b.append(']').toString();
            b.append(", ");
        }
    }

    public static void mergeSort(Number[] array, int left, int right, boolean isAsc) {
        if (right <= left) return;
        int mid = (left + right) / 2;
        mergeSort(array, left, mid, isAsc);
        mergeSort(array, mid + 1, right, isAsc);
        merge(array, left, mid, right, isAsc);
    }

    private static void merge(Number[] array, int left, int mid, int right, boolean isAsc) {
        // calculating lengths
        int lengthLeft = mid - left + 1;
        int lengthRight = right - mid;

        // creating temporary subarrays
        Number[] leftArray = new Number[lengthLeft];
        Number[] rightArray = new Number[lengthRight];

        // copying our sorted subarrays into temporaries
        for (int i = 0; i < lengthLeft; i++)
            leftArray[i] = array[left + i];
        for (int i = 0; i < lengthRight; i++)
            rightArray[i] = array[mid + i + 1];

        // iterators containing current index of temp subarrays
        int leftIndex = 0;
        int rightIndex = 0;

        // copying from leftArray and rightArray back into array
        for (int i = left; i < right + 1; i++) {
            // if there are still uncopied elements in R and L, copy minimum of the two
            if (leftIndex < lengthLeft && rightIndex < lengthRight) {
                if (isAscCompare(compareNumber(leftArray[leftIndex], rightArray[rightIndex]), isAsc)) {
                    array[i] = leftArray[leftIndex];
                    leftIndex++;
                } else {
                    array[i] = rightArray[rightIndex];
                    rightIndex++;
                }
            }
            // if all the elements have been copied from rightArray, copy the rest of leftArray
            else if (leftIndex < lengthLeft) {
                array[i] = leftArray[leftIndex];
                leftIndex++;
            }
            // if all the elements have been copied from leftArray, copy the rest of rightArray
            else if (rightIndex < lengthRight) {
                array[i] = rightArray[rightIndex];
                rightIndex++;
            }
        }
    }

    private static boolean isAscCompare(int compValue, boolean isAsc) {
        if(isAsc) {
            return compValue < 0;
        } else {
            return compValue > 0;
        }
    }


    public static int compareNumber(Number a, Number b) {
        return new BigDecimal(a.toString()).compareTo(new BigDecimal(b.toString()));
    }

    public static Number additionNumber(Number a, Number b) {
        return new BigDecimal(a.toString()).add(new BigDecimal(b.toString())).doubleValue();
    }

    public static Number divideNumber(Number a, Number b) {
        return new BigDecimal(a.toString()).divide(new BigDecimal(b.toString())).doubleValue();
    }
}
