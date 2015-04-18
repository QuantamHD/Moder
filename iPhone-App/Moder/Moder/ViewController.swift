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

    @IBOutlet var yesButton: UIButton!
    @IBOutlet var noButton: UIButton!
    @IBOutlet var cardContainer : UIView!
    @IBOutlet var bar : UINavigationBar!
    
    var card: Card?
    
    // ------ IB Actions ------ // 
    
    @IBAction func rateYes(sender : AnyObject) {
        println("Rated yes!")
        var rater : SendRate = SendRate()
        if (card != nil) {
            println("Sent rate!")
            rater.send(true, id: card!.currentPhotoID!)
            card!.attemptPhotoChange()
        }
        
    }
    
    @IBAction func rateNo(sender : AnyObject) {
        println("Rated no!")
        var rater : SendRate = SendRate()
        if (card != nil) {
            println("Sent rate!")
            rater.send(false, id: card!.currentPhotoID!)
            card!.attemptPhotoChange()
        }
    }

    
    // ------ Overrided actions from UIViewController ----- //
    override func viewDidLoad() {
        super.viewDidLoad()
        
        println("Started main screen")
        
        cardContainer.layer.cornerRadius = 2;
        cardContainer.layer.masksToBounds = true
        UINavigationBar.appearance()
        
    }

    //Called when phone is almost out of battery
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
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

