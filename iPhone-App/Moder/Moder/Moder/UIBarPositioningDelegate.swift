//
//  UIBarPositioningDelegate.swift
//  Moder
//
//  Created by Evan Conrad on 4/3/15.
//  Copyright (c) 2015 Evan Conrad. All rights reserved.
//

import Foundation
import UIKit

class UIBarPositioningDelegate : ViewController {
    
    override func viewDidLoad() {
        
    }
    
    func positionForBar(bar: UIBarPositioning) -> UIBarPosition {
        return .TopAttached
    }
}