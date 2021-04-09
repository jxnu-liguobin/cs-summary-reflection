/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.queuestack;

import java.util.*;

public class OpenTheLock {
    private static class LockState {
        String state;
        int height;

        public LockState(String state, int height) {
            this.state = state;
            this.height = height;
        }
    }

    public int openLock(String[] deadends, String target) {
        if ("0000".equals(target)) {
            return 0;
        }
        Set<String> deadSet = new HashSet<>(Arrays.asList(deadends));
        if (deadSet.contains("0000")) {
            return -1;
        }
        Deque<LockState> queue = new ArrayDeque<>();
        queue.addLast(new LockState("0000", 0));
        Set<String> experienced = new HashSet<>();
        experienced.add("0000");

        while (!queue.isEmpty()) {
            LockState state = queue.pollFirst();
            for (int i = 0; i < 8; i++) {
                String newStr = swap(state.state, i >>> 1, i % 2);
                if (newStr.equals(target)) {
                    return state.height + 1;
                } else if (!deadSet.contains(newStr) && !experienced.contains(newStr)) {
                    experienced.add(newStr);
                    queue.addLast(new LockState(newStr, state.height + 1));
                }
            }
        }
        return -1;
    }

    private String swap(String str, int index, int isUp) {
        char[] chs = str.toCharArray();
        char newChar = chs[index];
        if (isUp == 1) {
            if (newChar == '9') {
                chs[index] = '0';
            } else {
                chs[index] = (char) (((int) newChar) + 1);
            }
        } else {
            if (newChar == '0') {
                chs[index] = '9';
            } else {
                chs[index] = (char) (((int) newChar) - 1);
            }
        }
        return new String(chs);
    }

    public static void main(String[] args) {
        OpenTheLock openTheLock = new OpenTheLock();
        int i =
                openTheLock.openLock(
                        new String[] {
                            "5556", "5554", "5565", "5545", "5655", "5455", "6555", "4555"
                        },
                        "5555");
        System.out.println(i);
    }
}
