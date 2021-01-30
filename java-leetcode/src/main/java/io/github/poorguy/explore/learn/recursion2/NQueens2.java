package io.github.poorguy.explore.learn.recursion2;

import java.util.concurrent.atomic.AtomicInteger;

class NQueens2 {
    public int totalNQueens(int n) {
        boolean[][] board=new boolean[n][n];
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                board[i][j]=false;
            }
        }
        AtomicInteger counter=new AtomicInteger();
        helper(board,0,counter);
        return counter.get();
    }
    
    private void helper(boolean[][] board,int row,AtomicInteger counter){
        if(row==board.length){
            return;
        }
        for(int i=0;i<board.length;i++){
            if(ok(board,i,row)){
                if(row==board.length-1){
                    counter.incrementAndGet();
                    return; 
                }
                board[row][i]=true;
                helper(board,row+1,counter);
                cleanSameRow(board,row);
            }
        }
    }
    
    private void cleanSameRow(boolean[][] board,int row){
        if(row>0||row<board.length){
            for(int i=0;i<board.length;i++){
                board[row][i]=false;   
            }
        }
    }
    
    //check if queen can be placed
    private boolean ok(boolean[][] board,int x,int y){
        for(int i=0;i<board.length;i++){
            //check row
            if(board[y][i]){
                return false;
            }
            //check column
            if(board[i][x]){
                return false;
            }
            //check others
            if(x-i>=0&&y-i>=0&&board[y-i][x-i]){
                return false;
            }
            if(x+i<board.length&&y+i<board.length&&board[y+i][x+i]){
                return false;
            }
            if(x-i>0&&y+i<board.length&&board[y+i][x-i]){
                return false;
            }
            if(y-i>=0&&x+i<board.length&&board[y-i][x+i]){
                return false;
            }
        }
        return true;
    }
}