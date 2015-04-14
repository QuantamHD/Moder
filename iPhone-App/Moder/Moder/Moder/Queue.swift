//
//  Queue.swift
//  Moder
//
//  Created by Evan Conrad on 4/11/15.
//  Copyright (c) 2015 Evan Conrad. All rights reserved.
//

import Foundation

public class Queue<T> {
    
    private var top: QueueElement<T>! = QueueElement<T>(key: nil, next: nil)
    
    func enqueue(var key: T) {
        //Check base case
        if (top == nil) {
            top = QueueElement(key: key, next: nil)
            return
        }
        
        //Define new element and iterator
        var newChild : QueueElement<T> = QueueElement(key: key, next: nil)
        var current: QueueElement = top
        
        //iterate
        while (current.next != nil) {
            current = current.next!
        }
        
        //Assign child
        current.next = newChild
    }
    
    func dequeue() -> T? {
        let topItem: T? = self.top?.key
        
        if (topItem == nil) {
            return nil
        }
        
        var item: T? = top.key!
        
        if let next = top.next {
            top = next
        } else {
            top = nil
        }
        
        return item
    }
    
    func isEmpty() -> Bool {
        if let topitem: T = self.top?.key {
            return false
        } else {
            return true
        }
    }
    
    func peek() -> T? {
        return top.key!
    }
}