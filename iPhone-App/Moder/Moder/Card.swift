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
    
    var currentPhotoID : String;

    required init(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    @IBOutlet var photoView : UIImageView!
    var card : CardStructure
    
    override func viewDidLoad() {
        var photoTask : GetPhoto
        photoTask = GetPhoto()
        var photo = photoTask.getPhoto(AppDelegate.cookie.value)
        if (photo != nil) {
            currentPhotoID = photo!.ID
            changePhoto(photo!.image)
        }
    }
    
    func changePhoto(photo : UIImage) {
        photoView.image = photo
    }
    
    func getCurrentPhotoID() -> String {
        return currentPhotoID
    }
}