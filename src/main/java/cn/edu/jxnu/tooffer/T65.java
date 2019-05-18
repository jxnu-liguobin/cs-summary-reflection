package cn.edu.jxnu.tooffer;

/**
 * 请设计一个函数，用来判断在一个矩阵中是否存在一条包含某字符串所有字符的路径。
 * 路径可以从矩阵中的任意一个格子开始，每一步可以在矩阵中向左，
 * 向右，向上，向下移动一个格子。如果一条路径经过了矩阵中的某一个格子，
 * 则之后不能再次进入这个格子。 例如 a b c e s f c s a d e e
 * 这样的3 X 4 矩阵中包含一条字符串"bcced"的路径，
 * 但是矩阵中不包含"abcb"路径，
 * 因为字符串的第一个字符b占据了矩阵中的第一行第二个格子之后，
 * 路径不能再次进入该格子。
 */
public class T65 {

    /**
     * 分析：回溯算法
     * 这是一个可以用回朔法解决的典型题。首先，在矩阵中任选一个格子作为路径的起点。如果路径上的第i个字符不是ch，那么这个格子不可能处在路径上的
     * 第i个位置。如果路径上的第i个字符正好是ch，那么往相邻的格子寻找路径上的第i+1个字符。除在矩阵边界上的格子之外，其他格子都有4个相邻的格子。
     * 重复这个过程直到路径上的所有字符都在矩阵中找到相应的位置。
     * 　　由于回朔法的递归特性，路径可以被开成一个栈。当在矩阵中定位了路径中前n个字符的位置之后，在与第n个字符对应的格子的周围都没有找到第n+1个
     * 字符，这个时候只要在路径上回到第n-1个字符，重新定位第n个字符。
     * 　　由于路径不能重复进入矩阵的格子，还需要定义和字符矩阵大小一样的布尔值矩阵，用来标识路径是否已经进入每个格子。 当矩阵中坐标为（row,col）的
     * 格子和路径字符串中相应的字符一样时，从4个相邻的格子(row,col-1),(row-1,col),(row,col+1)以及(row+1,col)中去定位路径字符串中下一个字符
     * 如果4个相邻的格子都没有匹配字符串中下一个的字符，表明当前路径字符串中字符在矩阵中的定位不正确，我们需要回到前一个，然后重新定位。
     * 　　一直重复这个过程，直到路径字符串上所有字符都在矩阵中找到合适的位置
     */
    public boolean hasPath(char[] matrix, int rows, int cols, char[] str) {
        if (null == matrix || matrix.length == 0 || null == str || str.length == 0 || matrix.length != rows * cols || rows <= 0 || cols <= 0) {
            return false;
        }
        boolean[] visited = new boolean[rows * cols];
        int[] pathLength = {0};
        for (int i = 0; i <= rows - 1; i++) {
            for (int j = 0; j <= cols - 1; j++) {
                if (hasPathCore(matrix, rows, cols, str, i, j, visited, pathLength)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasPathCore(char[] matrix, int rows, int cols, char[] str, int row, int col, boolean[] visited, int[] pathLength) {
        boolean flag = false;

        if (row >= 0 && row < rows && col >= 0 && col < cols && !visited[row * cols + col] && matrix[row * cols + col] == str[pathLength[0]]) {
            pathLength[0]++;
            visited[row * cols + col] = true;
            if (pathLength[0] == str.length) {
                return true;
            }
            flag = hasPathCore(matrix, rows, cols, str, row, col + 1, visited, pathLength) || hasPathCore(matrix, rows, cols, str, row + 1, col, visited, pathLength) || hasPathCore(matrix, rows, cols, str, row, col - 1, visited, pathLength) || hasPathCore(matrix, rows, cols, str, row - 1, col, visited, pathLength);
            if (!flag) {
                pathLength[0]--;
                visited[row * cols + col] = false;
            }
        }
        return flag;
    }
}
