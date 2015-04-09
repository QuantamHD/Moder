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
    
    var cardStructure : CardStructure?;
    
    
    // ------ IB Actions ------ // 
    
    @IBAction func rateYes(sender : AnyObject) {
        //movePhotoFrame(true)
        println("Rated yes!")
        
        var rater : SendRate = SendRate()
        //var card : Card = Card()
        //rater.sendRequest(true, photoID: card.getCurrentPhotoID(), cookie: AppDelegate.cookie.value)
        
    }
    
    @IBAction func rateNo(sender : AnyObject) {
        //movePhotoFrame(false)
        println("Rated no!")
        var rater : SendRate = SendRate()
        //var card : Card = Card()
        //rater.sendRequest(false, photoID: card.getCurrentPhotoID(), cookie: AppDelegate.cookie.value)
    }
    

    // ------ Overrided actions from UIViewController ----- //


    required init(coder aDecoder: NSCoder) {
        //fatalError("init(coder:) has not been implemented")
        print("Starting View Controller")
        super.init(nibName: nil, bundle: nil);
    }

    
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
    
    
    // ------ Helper functions ------ //
    
    /*
    private func movePhotoFrame(toRight: Bool) {
        //Animate after view appears
        UIView.animateWithDuration(0.5, delay: 0, options: .CurveEaseOut, animations: {
            //Swipe it off to the right
            var photoFrame = self.photoView.frame; //Get the frame
            if (toRight) {
                photoFrame.origin.x += photoFrame.size.height
            } else {
                photoFrame.origin.x -= photoFrame.size.height
            }
            self.photoView.frame = photoFrame //Reset the frame
            
            }, completion: { finished in
                println("Animated!") //DEBUG only
        })
    }
    */
    
    /**
    private func addNewPhoto() {
        let imageName = "test.jpg"
        let image = UIImage(named: imageName)
        let imageView = UIImageView(image: image!)
        imageView.frame = CGRect(x: 115, y: 93, width: 370, height: 370)
        imageView.setTranslatesAutoresizingMaskIntoConstraints(false)
        
        self.view.addSubview(imageView)
    }
**/
    

}

