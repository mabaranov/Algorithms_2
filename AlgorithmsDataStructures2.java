package com.company;

import java.util.*;

public class AlgorithmsDataStructures2
{
    public static int [] BBSTArray;

    public static int[] GenerateBBSTArray(int[] a)
    {
        Arrays.sort(a);

        BBSTArray = new int[a.length];
        recursiveCreateBBST(a, 0);

        return BBSTArray;
    }

    private static void recursiveCreateBBST(int [] sortedArray, int index)
    {
        int middle = sortedArray.length / 2;
        BBSTArray[index] = sortedArray[middle];

        if(sortedArray.length == 1)
            return;

        int [] leftSubArray = new int[middle];
        System.arraycopy(sortedArray, 0, leftSubArray, 0, middle);
        recursiveCreateBBST(leftSubArray, 2 * index + 1);

        int [] rightSubArray = new int[middle];
        System.arraycopy(sortedArray, middle+1, rightSubArray, 0, middle);
        recursiveCreateBBST(rightSubArray, 2 * index + 2);
    }
}