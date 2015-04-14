//
//  ViewController.swift
//  Moder
//
//  Created by Evan Conrad on 2/12/15.
//  Copyright (c) 2015 Evan Conrad. All rights reserved.
//

import UIKit
import QuartzCore

class ViewController: UIViewController {

    required init(coder aDecoder: NSCoder) {
        self.card = nil
        super.init(coder: aDecoder)
    }

    @IBOutlet var yesButton: UIButton!
    @IBOutlet var noButton: UIButton!
    @IBOutlet var cardContainer : UIView!
    @IBOutlet var bar : UINavigationBar!
    
    var card: Card?
  
    struct CardStruct {
        static var photoID: String = ""
    }
    
    // ------ IB Actions ------ //
    @IBAction func rateYes(sender : AnyObject) {
        println("Rated yes!")
        let concurrentPhotoQueue = dispatch_queue_create(
            "com.ModerLLC.Moder.rateYes", DISPATCH_QUEUE_CONCURRENT)
        self.changePhoto()
        dispatch_barrier_async(concurrentPhotoQueue) {
            let rate = SendRate()
            let code = rate.sendRequest(true, photoID: CardStruct.photoID, cookie: AppDelegate.cookie.value)
            println(code)
        }
        
    }
    
    @IBAction func rateNo(sender : AnyObject) {
        println("Rated no!")
        let concurrentPhotoQueue = dispatch_queue_create(
            "com.ModerLLC.Moder.rateYes", DISPATCH_QUEUE_CONCURRENT)
        self.changePhoto()
        dispatch_barrier_async(concurrentPhotoQueue) {
            let rate = SendRate()
            let code = rate.sendRequest(false, photoID: CardStruct.photoID, cookie: AppDelegate.cookie.value)
            println(code)
        }
    }
    

    // ------ Overrided actions from UIViewController ----- //
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        println("Started main screen")
        
        //cardContainer.view.layer.cornerRadius = 2;
        //cardContainer.view.layer.masksToBounds = true
        //UINavigationBar.appearance()
        
    }

    //Called when phone is almost out of battery
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
  
    
    
    // ------ Helper functions ------ //

    //Currently not in use
    func changePhoto() {
        var originalPosition : CGPoint = self.cardContainer.center
        
            //Go down
        UIView.animateWithDuration(0.5, delay: 0, options: .CurveEaseOut, animations: {
            self.cardContainer.center = CGPoint(x: self.cardContainer.center.x, y: 1000)
            
            }, completion: { finished in
                println("Loading Animation...")
        })
        
        self.attemptPhotoChange()
        
        UIView.animateWithDuration(0.5, delay: 1.0, options: .CurveEaseOut, animations: {
            self.cardContainer.center = originalPosition
            
            }, completion: { finished in
                println("Loading Animation...")
        })
    }
    
    func attemptPhotoChange() {
        let concurrentPhotoQueue = dispatch_queue_create(
            "com.ModerLLC.Moder.changePhotoFromViewController", DISPATCH_QUEUE_CONCURRENT)
        
        dispatch_barrier_async(concurrentPhotoQueue) {
            if (self.card != nil) {
                self.card?.attemptPhotoChange()
            } else {
                println("Card nil!")
            }
        }
    }
    
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if let embeddedViewController = segue.destinationViewController as? Card
        {
            if (segue.identifier == "toCard") {
                card = embeddedViewController
            }
        }
    }

}

