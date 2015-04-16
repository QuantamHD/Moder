//
//  ViewController.swift
//  Moder
//
//  Created by Evan Conrad on 4/14/15.
//  Copyright (c) 2015 Evan Conrad. All rights reserved.
//

import UIKit

class ViewController: UIViewController {

<<<<<<< HEAD
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
=======
    @IBOutlet var yesButton: UIButton!
    @IBOutlet var noButton: UIButton!
    @IBOutlet var cardContainer : UIView!
    @IBOutlet var bar : UINavigationBar!
    
    
    // ------ IB Actions ------ // 
    
    @IBAction func rateYes(sender : AnyObject) {
        //movePhotoFrame(true)
        println("Rated yes!")
        
        //Send info to server
    }
    
    @IBAction func rateNo(sender : AnyObject) {
        //movePhotoFrame(false)
        println("Rated no!")
        //Send info to server
    }
    

    // ------ Overrided actions from UIViewController ----- //
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        println("Started main screen")
        
        cardContainer.layer.cornerRadius = 2;
        cardContainer.layer.masksToBounds = true
        UINavigationBar.appearance()
        
>>>>>>> parent of 6d67204... Rate mostly done, minor edits
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
<<<<<<< HEAD

=======
    
    
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
    
>>>>>>> parent of 6d67204... Rate mostly done, minor edits

}

