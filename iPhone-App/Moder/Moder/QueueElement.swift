//
//  QueueElement.swift
//  Moder
//
//  Created by Evan Conrad on 4/11/15.
//  Copyright (c) 2015 Evan Conrad. All rights reserved.
//

import Foundation

class QueueElement<T> {
    var key: T? = nil
    var next: QueueElement? = nil

    init (key : T?, next : QueueElement?) {
        self.key = key
        self.next = next
    }
}
