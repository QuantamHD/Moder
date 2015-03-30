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
    @IBOutlet var loginTextField : UITextField!
    @IBOutlet var pwdTextField : UITextField!
    
    var SUCCESS_CODE = 300
    var MISSING_INFO_CODE = 100
    var RETRY_CODE = 140
    var trys = 0
    
    
    func switchToMainScreen() {
        //println("Attempting to switch screens...")
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
        var task : SendLogin
        task = SendLogin()
        var taskDict = task.sendRequest(loginTextField.text!, pwd: pwdTextField.text!);
        //println("Here's the code: ")
        //println(code)
        
        let code = taskDict["Code"] as? Int
        
        
        if (code == SUCCESS_CODE) {
            //Save it to appdelegates cookie value
            
            if let name = taskDict["Name"] as? String {
                AppDelegate.cookie.name = name
            } else {
                println("Cookie Name Nil!")
            }
            if let value = taskDict["Value"] as? String {
                AppDelegate.cookie.value = value
            } else {
                println("Cookie Value Nil!")
            }
            switchToMainScreen()
            return //Recursive break
        } else if (code == MISSING_INFO_CODE) {
            // TODO: Handle missing info
            let alert = UIAlertView()
            alert.title = "Incorrect Login"
            alert.message = "One or more pieces of information is missing!"
            alert.addButtonWithTitle("Dismiss")
            alert.show()
            return
        } else if (code == RETRY_CODE) {
            if (trys >= 3) {
                println("140 Code hit; The server may have been inactive. Retrying connection...")
                login(sender)
                trys++
            } else {
                let alert = UIAlertView()
                alert.title = "Oops."
                alert.message = "Something went wrong with the server and we're not quite sure why. Make yourself some mint tea and email a developer."
                alert.addButtonWithTitle("Dismiss")
                alert.show()
                return //Recursive break
            }
        } else {
            // TODO: Handle unknown error
            let alert = UIAlertView()
            alert.title = "Oops."
            alert.message = "Something went wrong and we're not quite sure why. Make yourself some chai tea and email a developer."
            alert.addButtonWithTitle("Dismiss")
            alert.show()
            return //Recursive break
        }
    }

    override func viewDidLoad() {
        super.viewDidLoad()
        println("Starting load...")
        trys = 0
    }
    
    //Called when phone is almost out of battery
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
}