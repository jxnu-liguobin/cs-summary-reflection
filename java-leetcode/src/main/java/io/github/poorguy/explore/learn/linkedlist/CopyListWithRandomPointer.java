package io.github.poorguy.explore.learn.linkedlist;/*
// Definition for a Node.
class Node {
    int val;
    Node next;
    Node random;

    public Node(int val) {
        this.val = val;
        this.next = null;
        this.random = null;
    }
}
*/

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class CopyListWithRandomPointer {
    public Node copyRandomList(Node head) {
        if(head==null){
            return null;
        }
        
        List<Node> list=new ArrayList<>();
        List<Node> oldList=new ArrayList<>();
        Map<Node,Integer> map=new HashMap<>();
        Node p=head;
        for(int i=0;p!=null;i++){
            Node node=new Node(p.val);
            list.add(node);
            oldList.add(p);
            map.put(p,i);
            p=p.next;
        }
        List<Integer> mapList=new ArrayList<>(list.size());
        for(Node node:oldList){
            mapList.add(map.get(node.random));
        }
        for(int i=0;i<list.size()-1;i++){
            list.get(i).next=list.get(i+1);
        }
        System.out.println(list.size());
        System.out.println(mapList.size());
        for(int i=0;i<list.size();i++){
            if(mapList.get(i)!=null){
                list.get(i).random=list.get(mapList.get(i));
            }else{
                list.get(i).random=null;
            }
        }
        return list.get(0);
    }
}