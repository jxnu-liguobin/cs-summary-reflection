use priority_queue::PriorityQueue;

/// https://github.com/garro95/priority-queue
/// 这个优先级队列使用IndexMap存储元素，并且有每个元素单独的优先级属性
pub(crate) fn priority_queue_garro95() {
    let mut pq = PriorityQueue::new();

    assert!(pq.is_empty());
    pq.push("Apples", 5);
    pq.push("Bananas", 8);
    pq.push("Strawberries", 23);

    assert_eq!(pq.peek(), Some((&"Strawberries", &23)));

    pq.change_priority("Bananas", 25);
    assert_eq!(pq.peek(), Some((&"Bananas", &25)));

    for (item, _) in pq.into_sorted_iter() {
        println!("{}", item);
    }
}
