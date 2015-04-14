//
//  SendLogin.swift
//  Moder
//
//  Created by Evan Conrad on 2/15/15.
//  Copyright (c) 2015 Evan Conrad. All rights reserved.
//

import Foundation

class SendLogin{
    
    
    
    
    //Lovely frakenstien code
    func sendRequest(email : String, pwd : String) -> Dictionary<String, AnyObject>{
        
        var code = -2 //Response code to check success, -2 is not found yet
        var cookie = "BAD-COOKIE"
        
        let url = NSURL(string: "https://moderapp.com/login?email=" + email + "&pwd=" + pwd) //Where everything actually changes
        var session = NSURLSession.sharedSession()
        let request = NSMutableURLRequest(URL: url!)
        request.HTTPMethod = "POST" //set http method as POST
        
        
        var parameters = ["Satisfying": "Demands"] as Dictionary<String, String> //Not actually necessary. Dead code. Evil Code. Soulless code.
        var err: NSError?
        request.HTTPBody = NSJSONSerialization.dataWithJSONObject(parameters, options: nil, error: &err) // pass dictionary to nsdata object and set it as request body
        
        request.addValue("application/json", forHTTPHeaderField: "Content-Type")
        request.addValue("application/json", forHTTPHeaderField: "Accept")
        
        
        //create dataTask using the session object to send data to the server
        var task = session.dataTaskWithRequest(request, completionHandler: {data, response, error -> Void in
            
            var strData = NSString(data: data, encoding: NSUTF8StringEncoding)
            
            var err: NSError?
            var json = NSJSONSerialization.JSONObjectWithData(data, options: .MutableLeaves, error: &err) as? NSDictionary
            
            
            // Did the JSONObjectWithData constructor return an error? If so, log the error to the console
            if(err != nil) {
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
        
        //Split until you isolate the name and cookie
        
        var dictionary = [String: AnyObject]()
        
        if (code == 300) {
            let someResponse : NSHTTPURLResponse = task.response as NSHTTPURLResponse
            let responseString : String = someResponse.allHeaderFields["Set-Cookie"] as String
            var responseStringArr = split(responseString) {$0 == ","} as Array<NSString>
            var secondStringArr : Array<String>
            var name : String = ""
            var value : String = ""
            for i in 0...responseStringArr.count-1 {
                if (responseStringArr[i].containsString("UniqueID")){
                    var string : String = responseStringArr[i]
                    secondStringArr = split(string) {$0 == ";"}
                    var nameAndValueCookie = split(secondStringArr[0]) {$0 == "="}
                    nameAndValueCookie[0] = String(filter(nameAndValueCookie[0].generate()) { $0 != " "});
                    name = nameAndValueCookie[0]
                    value = nameAndValueCookie[1]
                    break; //Found it, no need to continue
                }
            }
            
            dictionary["Name"] = name
            dictionary["Value"] = value
        } else {
            dictionary["Name"] = "NO-NAME"
            dictionary["Value"] = "NO-VALUE"
        }
        
        dictionary["Code"] = code
        
        return dictionary;
        
    }
    

}