use crate::pre_structs::Solution;

///1443. 收集树上所有苹果的最少时间
impl Solution {
    //最少时间能获取全部苹果，也就是说， ret >= 2*苹果的个数
    //存储子到父的所有路径，求从苹果开始到根节点的节点数量，节点*2就是路径长
    //用例更新后无法通过，路径存在一对多
    pub fn min_time_(n: i32, edges: Vec<Vec<i32>>, has_apple: Vec<bool>) -> i32 {
        let mut visited = vec![false; n as usize];
        visited[0] = true;
        let mut ret = 0;
        let mut reverse_edges = vec![0 as usize; n as usize];

        fn dfs(to: usize, visited: &mut Vec<bool>, reverse_edges: &mut Vec<usize>, ret: &mut i32) {
            if !visited[to] {
                visited[to] = true;
                *ret += 1;
                dfs(reverse_edges[to], visited, reverse_edges, ret);
            }
        }

        for edge in edges.iter() {
            //记录子 父 路径
            reverse_edges[edge[1] as usize] = edge[0 as usize] as usize;
        }

        //遍历所有元素，从苹果开始到根节点
        for i in 0usize..n as usize {
            if i < has_apple.len() && has_apple[i] {
                dfs(i, &mut visited, &mut reverse_edges, &mut ret);
            }
        }
        ret * 2
    }

    //28~36 ms,63.64%
    //11 MB,100.00%
    pub fn min_time(n: i32, edges: Vec<Vec<i32>>, has_apple: Vec<bool>) -> i32 {
        let mut node_mappings: Vec<Vec<i32>> = vec![Vec::new(); n as usize];
        let mut reverse_edges = vec![-1i32; n as usize];
        let mut visited = vec![false; n as usize];
        visited[0] = true;
        let mut ret = 0;

        for edge in edges {
            node_mappings[edge[0usize] as usize].push(edge[1usize]);
            node_mappings[edge[1usize] as usize].push(edge[0usize]);
        }

        fn build_reverse_edges(
            node_mappings: &mut Vec<Vec<i32>>,
            val: usize,
            reverse_edges: &mut Vec<i32>,
        ) {
            let node = node_mappings[val].clone();
            for &pairVal in node.iter() {
                if pairVal != 0 && reverse_edges[pairVal as usize] as i32 == -1i32 {
                    reverse_edges[pairVal as usize] = val as i32;
                    build_reverse_edges(node_mappings, pairVal as usize, reverse_edges);
                }
            }
        }

        fn dfs(to: usize, visited: &mut Vec<bool>, reverse_edges: &mut Vec<i32>, ret: &mut i32) {
            if !visited[to] {
                visited[to] = true;
                *ret += 1;
                dfs(reverse_edges[to] as usize, visited, reverse_edges, ret);
            }
        }

        //这一步建了一颗以0为根节点的树，确保能通过子结点找到父结点，父结点记录在reverse_edges数组中
        build_reverse_edges(&mut node_mappings, 0, &mut reverse_edges);
        //遍历所有元素，从苹果开始到根节点
        for i in 0usize..n as usize {
            if i < has_apple.len() && has_apple[i] {
                dfs(i, &mut visited, &mut reverse_edges, &mut ret);
            }
        }
        ret * 2
    }
}

#[cfg(test)]
mod test {
    use crate::pre_structs::Solution;

    #[test]
    fn min_time() {
        let n = 7;
        let edges = vec![[0, 2].to_vec(), [0, 3].to_vec(), [1, 2].to_vec()];
        let has_apple = vec![false, true, false, false].to_vec();

        let edges2 = vec![
            [0, 1].to_vec(),
            [0, 2].to_vec(),
            [1, 4].to_vec(),
            [1, 5].to_vec(),
            [2, 3].to_vec(),
            [2, 6].to_vec(),
        ];
        let has_apple2 = vec![false, false, true, false, true, true, false].to_vec();

        let ret = Solution::min_time(n, edges, has_apple);
        let ret2 = Solution::min_time(n, edges2, has_apple2);
        println!("{}", ret);
        println!("{}", ret2);
    }
}
