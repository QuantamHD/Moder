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
    
    
    
    @IBAction func switchToMainScreen(sender : AnyObject) {
        println("Attempting to switch screens...")
        
    }

    override func viewDidLoad() {
        super.viewDidLoad()
        println("Starting load...")
        var task : SendLogin
        task = SendLogin()
        task.sendRequest();
    }
    
    //Called when phone is almost out of battery
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
}