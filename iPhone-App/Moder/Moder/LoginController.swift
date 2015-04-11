//
//  LoginController.swift
//  Moder
//
//  Created by Evan Conrad on 2/14/15.
//  Copyright (c) 2015 Evan Conrad. All rights reserved.
//


import UIKit

class LoginController: UIViewController {
    
    @IBOutlet var loginButton : UIButton!
    @IBOutlet var registerButton : UIButton!
    @IBOutlet var loginTextField : UITextField!
    @IBOutlet var pwdTextField : UITextField!
    
    var SUCCESS_CODE = 300
    var MISSING_INFO_CODE = 100
    var RETRY_CODE = 140
    var trys = 0
    
    
    func switchToMainScreen() {
        println("Attempting to switch screens...")
        let viewController:UIViewController = UIStoryboard(name: "Main", bundle: nil).instantiateViewControllerWithIdentifier("main") as UIViewController
        // .instantiatViewControllerWithIdentifier() returns AnyObject! this must be downcast to utilize it
        self.presentViewController(viewController, animated: true, completion: nil)
    }
    
    
    /*
    * Recursive login function that calls SendLogin (in the networking folder)
    * to send the request. Retrieves a code and a cookie then handles
    * success or fail. Recursion is used for a retry.
    */
    @IBAction func login(sender : AnyObject ) {
        
        loadingAnimation()
        
        let concurrentPhotoQueue = dispatch_queue_create(
            "com.ModerLLC.Moder.login", DISPATCH_QUEUE_CONCURRENT)
        
        //Do shit on a new thread. Because that needs to happen.
        dispatch_barrier_async(concurrentPhotoQueue) {
            self.loginNetwork()
        }
    }
    
    var GlobalMainQueue: dispatch_queue_t {
        return dispatch_get_main_queue()
    }
    
    
    func loginNetwork() {
        
        var task : SendLogin
        task = SendLogin()
        var taskDict = task.sendRequest(loginTextField.text!, pwd: pwdTextField.text!);
        
        let code = taskDict["Code"] as? Int
        
        if (code == SUCCESS_CODE) {
            //Save it to appdelegates cookie value
            
            
            if let name = taskDict["Name"] as? String {
                //AppDelegate.cookie.name = name
            } else {
                println("Cookie Name Nil!")
            }
            if let value = taskDict["Value"] as? String {
                //AppDelegate.cookie.value = value
            } else {
                println("Cookie Value Nil!")
            }
            
            dispatch_async(GlobalMainQueue) {
                self.switchToMainScreen()
            }
            self.loginButton.layer.removeAllAnimations()
            return //Recursive break
        } else if (code == MISSING_INFO_CODE) {
            // TODO: Handle missing info
            dispatch_async(GlobalMainQueue) {
                let alert = UIAlertView()
                alert.title = "Incorrect Login"
                alert.message = "One or more pieces of information is missing!"
                alert.addButtonWithTitle("Dismiss")
                alert.show()
                self.loginTextField.text = "" //Reset text
                self.pwdTextField.text = ""
            }
            self.loginButton.layer.removeAllAnimations()
            return
        } else if (code == RETRY_CODE) {
            if (trys >= 3) {
                println("140 Code hit; The server may have been inactive. Retrying connection...")
                trys++
                loginNetwork()
            } else {
                dispatch_async(GlobalMainQueue) {
                    let alert = UIAlertView()
                    alert.title = "Oops."
                    alert.message = "Something went wrong with the server and we're not quite sure why. This is definitely our fault."
                    alert.addButtonWithTitle("Dismiss")
                    alert.show()
                    self.loginTextField.text = "" //Reset text
                    self.pwdTextField.text = ""
                }
                self.loginButton.layer.removeAllAnimations()
                return //Recursive break
            }
        } else {
            dispatch_async(GlobalMainQueue) {
                let alert = UIAlertView()
                alert.title = "Oops."
                alert.message = "Something went wrong. Make sure your internet connection is active!"
                alert.addButtonWithTitle("Dismiss")
                alert.show()
                self.loginTextField.text = "" //Reset text
                self.pwdTextField.text = ""
            }
            self.loginButton.layer.removeAllAnimations()
            return //Recursive break
        }
    }
    
    func loadingAnimation() {
        UIView.animateWithDuration(0.7, delay: 0, options: .CurveEaseOut, animations: {
            self.loginButton.transform = CGAffineTransformMakeScale(0.50, 1)
            self.loginButton.transform = CGAffineTransformMakeScale(1, 1)
            }, completion: { finished in
                println("Loading Animation...")
        })
    }

    override func viewDidLoad() {
        super.viewDidLoad()
        println("Starting load...")
        trys = 0
        
        loginButton.layer.cornerRadius = 5
        loginButton.layer.masksToBounds = true
        registerButton.layer.cornerRadius = 5
        registerButton.layer.masksToBounds = true

    }
    
    //Called when phone is almost out of battery
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
}