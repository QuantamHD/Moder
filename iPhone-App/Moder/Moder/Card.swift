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
    
    var cardStruct : CardStruct
    var photoQueue : Queue<CardStruct>
    var photoIDDefaults : Array<String>! {
        get {
            if let photoIDDefaults : AnyObject! = NSUserDefaults.standardUserDefaults().objectForKey("photoIDs") {
                return photoIDDefaults as Array<String>!
            } else {
                return nil
            }
        } set {
            NSUserDefaults.standardUserDefaults().setObject(newValue, forKey: "photoIDs")
            NSUserDefaults.standardUserDefaults().synchronize()
        }
    }
    
    struct CardStruct {
        var photoID: String?
        var photo: UIImage?
        
        init (id: String, photo: UIImage?) {
            self.photoID = id
            self.photo = photo
        }
    }
    
    var GlobalMainQueue: dispatch_queue_t {
        return dispatch_get_main_queue()
    }
    
    required init(coder aDecoder: NSCoder) {
        self.photoQueue = Queue<CardStruct>()
        self.cardStruct = CardStruct(id: "", photo: nil)
        super.init(coder: aDecoder)
    }
    
    override init(nibName nibNameOrNil: String!, bundle nibBundleOrNil: NSBundle!) {
        self.cardStruct = CardStruct(id: "", photo: nil)
        self.photoQueue = Queue<CardStruct>()
        super.init(nibName: nibNameOrNil, bundle: nibBundleOrNil)
        var coder : NSCoder
    }

    
    override func viewDidLoad() {
        attemptPhotoChange()
    }
    
    
    func attemptPhotoChange(){
        let concurrentPhotoQueue = dispatch_queue_create("com.ModerApp.Moder.photoQueue", DISPATCH_QUEUE_CONCURRENT)
        
        
        dispatch_barrier_async(concurrentPhotoQueue) {
            var photoTask : GetPhoto
            photoTask = GetPhoto()
            var someStruct : CardStruct? = photoTask.getCardStruct(AppDelegate.cookie.value)
            if (someStruct != nil) {
                dispatch_async(dispatch_get_main_queue()) {
                    var photo : UIImage? = someStruct?.photo
                    if (photo != nil) {
                        self.changePhoto(photo!)
                        ViewController.CardStruct.photoID = self.cardStruct.photoID!
                        println("Changed photo!")
                    }
                }
            }
        }
    }
    
    
    /*
    func attemptPhotoChange(){
        let concurrentPhotoQueue = dispatch_queue_create("com.ModerApp.Moder.photoQueue", DISPATCH_QUEUE_CONCURRENT)
        
        dispatch_barrier_async(concurrentPhotoQueue) {
            if (self.photoQueue.isEmpty()) {
                println("Empty photoQueue!")
                for i in 1...5 {
                    self.buffer() //Add 5 more photos
                }
            } else {
                println("Photo queue not empty!")
                dispatch_async(dispatch_get_main_queue()) {
                    var photo : UIImage? = self.photoQueue.dequeue()?.photo
                    if (photo != nil) {
                        self.changePhoto(photo!)
                        ViewController.CardStruct.photoID = self.cardStruct.photoID!
                        println("Changed photo!")
                    }
                }
            }
        }
    } */
    
    private func buffer() {
        let concurrentPhotoQueue = dispatch_queue_create("com.ModerApp.Moder.buffer", DISPATCH_QUEUE_CONCURRENT)
        
        dispatch_barrier_async(concurrentPhotoQueue) {
            var photoTask : GetPhoto
            photoTask = GetPhoto()
            var someStruct : CardStruct? = photoTask.getCardStruct(AppDelegate.cookie.value)
            if (someStruct != nil) {
                self.photoQueue.enqueue(someStruct!)
            }
        }
    }
    
    private func changePhoto(photo : UIImage?) {
        if (photo != nil) {
            photoView.image = photo
        }
    }

}