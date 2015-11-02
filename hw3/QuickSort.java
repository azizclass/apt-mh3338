// Code orignially taken from
// http://www.programcreek.com/2012/11/quicksort-array-in-java/
import java.util.Comparator;
import java.util.Arrays;
import java.lang.Integer;

class AscendingComparator implements Comparator<Integer> {
    @Override
        public int compare(Integer a, Integer b) {
            return Integer.compare(a, b);
        }
}

class DescendingComparator implements Comparator<Integer> {
    @Override
        public int compare(Integer a, Integer b) {
            return Integer.compare(b, a);
        }
}

class QuickSort {
    public static void main(String[] args) {
        Integer[] x = { 9, 2, 4, 7, 3, 7, 10 };
        System.out.println("Original: " + Arrays.toString(x));

        int low = 0;
        int high = x.length - 1;
        quickSort(x, low, high, new AscendingComparator());
        System.out.println("Ascending Comparator: " + Arrays.toString(x));

        low = 0;
        high = x.length - 1;
        quickSort(x, low, high, new DescendingComparator());
        System.out.println("Descending Comparator: " + Arrays.toString(x));
    }

    private static <T> T generatePivot(T[] arr, int low, int high) {
        int middle = low + (high - low) / 2;
        return arr[middle];
    }
    
    public static <T> void quickSort(T[] arr, int low, int high, Comparator<? super T> strategy) {
        if (arr == null || arr.length == 0)
            return;

        if (low >= high)
            return;

        // pick the pivot
        T pivot = generatePivot(arr, low, high);

        // make left < pivot and right > pivot
        int i = low, j = high;
        while (i <= j) {
            while (strategy.compare(arr[i], pivot) < 0) {
                i++;
            }

            while (strategy.compare(arr[j], pivot) > 0) {
                j--;
            }

            if (i <= j) {
                T temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                i++;
                j--;
            }
        }

        // recursively sort two sub parts
        if (low < j)
            quickSort(arr, low, j, strategy);

        if (high > i)
            quickSort(arr, i, high, strategy);
    }
}
