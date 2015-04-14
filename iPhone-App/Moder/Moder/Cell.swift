//
//  Cell.swift
//  Moder
//
//  Created by Evan Conrad on 4/12/15.
//  Copyright (c) 2015 Evan Conrad. All rights reserved.
//

import Foundation
import UIkit

class Cell : UITableViewCell {
    
    @IBOutlet var photo : UIImageView!
    @IBOutlet var photoDescription : UITextView!
    @IBOutlet var cardView : UIView!


    required init(coder aDecoder: NSCoder) {
        cardView.layer.masksToBounds = false
        cardView.layer.cornerRadius = 1
        cardView.layer.shadowOffset = CGSizeMake(-0.2, 0.2)
        super.init(coder: aDecoder)
    }
}