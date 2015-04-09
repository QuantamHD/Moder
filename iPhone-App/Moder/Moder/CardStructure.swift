//
//  CardStructure.swift
//  Moder
//
//  Created by Evan Conrad on 4/5/15.
//  Copyright (c) 2015 Evan Conrad. All rights reserved.
//

import Foundation
import UIKit

class CardStructure {
    
    var photo : UIImage
    var description : String
    var id : String
    
    init(photo : UIImage, id : String) {
        self.photo = photo
        self.id = id
        self.description = "No Description"
    }
    
    init(photo : UIImage, id : String, description : String) {
        self.photo = photo
        self.id = id
        self.description = description
    }
}