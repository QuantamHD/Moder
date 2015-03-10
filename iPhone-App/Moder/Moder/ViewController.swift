//
//  ViewController.swift
//  Moder
//
//  Created by Evan Conrad on 2/12/15.
//  Copyright (c) 2015 Evan Conrad. All rights reserved.
//

import UIKit

class ViewController: UIViewController {
    
    @IBOutlet var photo : UIImageView!
    @IBOutlet var yesButton: UIButton!
    @IBOutlet var noButton: UIButton!
    
    
    // ------ IB Actions ------ // 
    
    @IBAction func rateYes(sender : AnyObject) {
        movePhotoFrame(true)
        
        //Send info to server
    }
    
    @IBAction func rateNo(sender : AnyObject) {
        movePhotoFrame(false)
        
        //Send info to server
    }
    

    // ------ Overrided actions from UIViewController ----- //
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
    }

    //Called when phone is almost out of battery
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    
    // ------ Helper functions ------ //
    
    private func movePhotoFrame(toRight: Bool) {
        //Animate after view appears
        UIView.animateWithDuration(0.5, delay: 0, options: .CurveEaseOut, animations: {
            //Swipe it off to the right
            var photoFrame = self.photo.frame; //Get the frame
            if (toRight) {
                photoFrame.origin.x += photoFrame.size.height
            } else {
                photoFrame.origin.x -= photoFrame.size.height
            }
            self.photo.frame = photoFrame //Reset the frame
            
            }, completion: { finished in
                println("Animated!") //DEBUG only
        })
    }
    
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

