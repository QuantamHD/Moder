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
<<<<<<< HEAD
        attemptPhotoChange()
    }
    
    func attemptPhotoChange(){
        let concurrentPhotoQueue = dispatch_queue_create("com.ModerApp.Moder.photoQueue", DISPATCH_QUEUE_CONCURRENT)
        
<<<<<<< HEAD
        dispatch_barrier_async(concurrentPhotoQueue) {
            var photoTask : GetPhoto
            photoTask = GetPhoto()
            var someStruct : CardStruct? = photoTask.getCardStruct(AppDelegate.cookie.value)
            if (someStruct != nil) {
                dispatch_async(dispatch_get_main_queue()) {
                    var photo : UIImage? = someStruct?.photo
                    if (photo != nil) {
                        self.changePhoto(photo!)
                        //ViewController.CardStruct.photoID = self.cardStruct.photoID!
                        println("Changed photo!")
                    }
                }
            }
        }
    }
    
    
    /*
    func attemptPhotoChange(){
        let concurrentPhotoQueue = dispatch_queue_create("com.ModerApp.Moder.photoQueue", DISPATCH_QUEUE_CONCURRENT)
=======
        
>>>>>>> parent of 5b38af8... Somewhat Broken
        
        dispatch_barrier_async(concurrentPhotoQueue) {
            if (self.photoQueue.isEmpty()) {
                for i in 1...5 {
                    self.buffer() //Add 5 more photos
                }
            } else {
                var photo : UIImage? = self.photoQueue.dequeue()?.photo
                if (photo != nil) {
                    self.changePhoto(photo!)
                    ViewController.CardStruct.photoID = self.cardStruct.photoID!
                    println("Changed photo!")
                }
            }
            
=======
        var photoTask : GetPhoto
        photoTask = GetPhoto()
        var photo = photoTask.getPhoto(AppDelegate.cookie.value)
        if (photo != nil) {
            changePhoto(photo!)
>>>>>>> parent of 6d67204... Rate mostly done, minor edits
        }
        
        
    }
    
    func changePhoto(photo : UIImage) {
        photoView.image = photo
    }
}