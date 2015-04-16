//
//  SendRate.swift
//  Moder
//
//  Created by Evan Conrad on 3/29/15.
//  Copyright (c) 2015 Evan Conrad. All rights reserved.
//

import Foundation

class SendRate {
    
    /*
    
    func sendRequest(rate : Bool, photoID: String, cookie: String) -> Dictionary<String, AnyObject> {
        
        var code = -2 //Response code to check success, -2 is not found yet
        
        let url = NSURL(string: "http://moderapp.com/rate?photoID=" + photoID + "&choice=" + cookie) //Where everything actually changes
        var session = NSURLSession.sharedSession()
        let request = NSMutableURLRequest(URL: url!)
        request.HTTPMethod = "POST" //set http method as POST
        
        
        var parameters = ["Satisfying": "Demands"] as Dictionary<String, String> //Not actually necessary. Dead code. Evil Code. Soulless code.
        var err: NSError?
        request.HTTPBody = NSJSONSerialization.dataWithJSONObject(parameters, options: nil, error: &err) // pass dictionary to nsdata object and set it as request body
        
        request.addValue("application/json", forHTTPHeaderField: "Content-Type")
        request.addValue("application/json", forHTTPHeaderField: "Accept")
        request.addValue(cookie, forHTTPHeaderField: "UniqueID") // Probably not gonna work 'cause fuck you
        
        
        
        //create dataTask using the session object to send data to the server
        var task = session.dataTaskWithRequest(request, completionHandler: {data, response, error -> Void in
            
            
            
            var strData = NSString(data: data, encoding: NSUTF8StringEncoding)
            
            var err: NSError?
            var json = NSJSONSerialization.JSONObjectWithData(data, options: .MutableLeaves, error: &err) as? NSDictionary
            
            
            
            // Did the JSONObjectWithData constructor return an error? If so, log the error to the console
            if(err != nil) {
                println(err!.localizedDescription)
                let jsonStr = NSString(data: data, encoding: NSUTF8StringEncoding)
                println("Error1 could not parse JSON: '\(jsonStr)'")
                code = -1 //Set error code
            }
            else {
                // The JSONObjectWithData constructor didn't return an error. But, we should still
                // check and make sure that json has a value using optional binding.
                if let parseJSON = json {
                    // Okay, the parsedJSON is here, let's get the value for 'success' out of it
                    code = parseJSON["ResponseCode"] as Int! //Set code for correct value
                }
                else {
                    // Woa, okay the json object was nil, something went worng. Maybe the server isn't running?
                    let jsonStr = NSString(data: data, encoding: NSUTF8StringEncoding)
                    println("Error3 could not parse JSON: \(jsonStr)")
                    code = -1 //set another error code
                }
            }
        })
        task.resume() //Actually start the task
        
        
        while (code == -2) {
            //Wait for the code to be found/task to finish
        }

        
    }

*/
    
}