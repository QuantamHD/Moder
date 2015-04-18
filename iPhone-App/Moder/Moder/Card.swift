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
    
    private let concurrentPhotoQueue = dispatch_queue_create(
        "com.ModerLLC.", DISPATCH_QUEUE_SERIAL)
    
    var GlobalMainQueue: dispatch_queue_t {
        return dispatch_get_main_queue()
    }
    
    @IBOutlet var photoView : UIImageView!
    
    var currentPhotoID : String?
    
    override func viewDidLoad() {
        attemptPhotoChange()
    }
    
    func attemptPhotoChange(){
        println("Attempting photo change")
        let concurrentPhotoQueue = dispatch_queue_create("com.ModerApp.Moder.photoQueue", DISPATCH_QUEUE_CONCURRENT)
        
        
        dispatch_barrier_async(concurrentPhotoQueue) {
            var photoTask : GetPhoto
            photoTask = GetPhoto()
            var someStruct : GetPhoto.Photo? = photoTask.get()
            if (someStruct != nil) {
                dispatch_async(dispatch_get_main_queue()) {
                    var photo : UIImage? = someStruct!.image
                    if (photo != nil) {
                        self.changePhoto(photo!)
                        //Tell everyone what the current photo ID is
                        self.currentPhotoID = someStruct!.id
                        println("Changed photo!")
                    } else {
                        println("photo not changed")
                    }
                }
            }
        }
    }
    
    func changePhoto(photo : UIImage) {
        photoView.image = photo
    }
}