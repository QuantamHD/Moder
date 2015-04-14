//
//  GetPhoto.swift
//  Moder
//
//  Created by Evan Conrad on 3/29/15.
//  Copyright (c) 2015 Evan Conrad. All rights reserved.
//

import Foundation
import UIkit

class GetPhoto {
    
    func getCardStruct(cookie : String) -> Card.CardStruct? {
        
        let dict = getPhotoID(cookie)
        
        var bgImage : UIImage?
        
        if (dict["Code"] as Int == 300) {
            let photoID = dict["PhotoID"] as String
            println(photoID)
            let url = NSURL(string: "https://moderapp.com/images/" + photoID);
        
            var err: NSError?
            if let imageData = NSData(contentsOfURL: url!) {
                bgImage = UIImage(data:imageData)?
                var someStruct = Card.CardStruct(id: photoID, photo: bgImage)
                return someStruct
            } else {
                return nil
            }
            
        } else {
            return nil
        }
    }
    
    private func getPhotoID(cookie : String) -> Dictionary<String, AnyObject> {
    
        var code = -2 //Response code to check success, -2 is not found yet
        var photoID = "UNINITALIZED_PHOTO_ID"
        
        let url = NSURL(string: "https://moderapp.com/getrate") //Where everything actually changes
        var session = NSURLSession.sharedSession()
        let request = NSMutableURLRequest(URL: url!)
        request.HTTPMethod = "POST" //set http method as POST
        
        
        var parameters = ["Satisfying": "Demands"] as Dictionary<String, String> //Not actually necessary. Dead code. Evil Code. Soulless code.
        var err: NSError?
        request.HTTPBody = NSJSONSerialization.dataWithJSONObject(parameters, options: nil, error: &err) // pass dictionary to nsdata object and set it as request body
        
        request.addValue("application/json", forHTTPHeaderField: "Content-Type")
        request.addValue("application/json", forHTTPHeaderField: "Accept")
        request.setValue("Unique-ID=" + cookie, forHTTPHeaderField: "Set-Cookie");
        var dictionary = [String: AnyObject]()
        
        
        //create dataTask using the session object to send data to the server
        var task = session.dataTaskWithRequest(request, completionHandler: {data, response, error -> Void in
            
            var strData = NSString(data: data, encoding: NSUTF8StringEncoding)
            
            
            var err: NSError?
            var json = NSJSONSerialization.JSONObjectWithData(data, options: .MutableLeaves, error: &err)
            
            switch json {
                case is NSDictionary :
                // Okay, the parsedJSON is here, let's get the value out of it
                    var dict : NSDictionary = json as NSDictionary
                    code = dict["ResponseCode"] as Int! //Set code for correct value
                    if (code == 300) { photoID = dict["PhotoID"] as String }
                case is NSArray :
                    var array : NSArray = json as NSArray
                    code = array[1] as Int
                    photoID = array[0] as String
                default:
                    code = -1
                    photoID = "NO-PHOTOID"
            }
            
        })
        task.resume() //Actually start the task
        
        
        while (code == -2) {
            //Wait for the code to be found/task to finish
        }
        
        dictionary["Code"] = code
        dictionary["PhotoID"] = photoID
        
        return dictionary
    }
}
