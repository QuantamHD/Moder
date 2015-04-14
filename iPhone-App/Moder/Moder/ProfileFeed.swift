//
//  ProfileFeed.swift
//  Moder
//
//  Created by Evan Conrad on 4/12/15.
//  Copyright (c) 2015 Evan Conrad. All rights reserved.
//

import Foundation
import UIKit

class ProfileFeed : UITableViewController {
    
    @IBOutlet var cell : Cell!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.tableView.separatorColor = UIColor.clearColor()
        
        cell.cardView = UIView()
    }
    
}