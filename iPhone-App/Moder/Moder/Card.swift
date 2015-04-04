//
//  Card.swift
//  Moder
//
//  Created by Evan Conrad on 4/2/15.
//  Copyright (c) 2015 Evan Conrad. All rights reserved.
//

import Foundation
import UIKit

class Card : UIViewController {
    
    @IBOutlet var photoView : UIImageView!
    
    override func viewDidLoad() {
        var photoTask : GetPhoto
        photoTask = GetPhoto()
        var photo = photoTask.getPhoto(AppDelegate.cookie.value)
        if (photo != nil) {
            changePhoto(photo!)
        }
        
        
    }
    
    func changePhoto(photo : UIImage) {
        photoView.image = photo
    }
}