//
//  GetPhotoBetter.swift
//  Moder
//
//  Created by Evan Conrad on 4/17/15.
//  Copyright (c) 2015 Evan Conrad. All rights reserved.
//

import Foundation
import Skeets
import SwiftHTTP
import JSONJoy
import AwesomeCache

class GetPhoto {
    
    struct Photo {
        var image : UIImage?
        var id : String?
        init (image : UIImage!, id : String!) {
            self.id = id
            self.image = image
        }
    }
    
    private struct GetRateStruct {
        var id : String?
        var code : Int?
        init(_ decoder: JSONDecoder) {
            id = decoder["PhotoID"].string
            code = decoder["ResponseCode"].integer
        }
    }
    
    func get() -> Photo? {
        let defaults = NSUserDefaults.standardUserDefaults()
        var current = defaults.integerForKey("CurrentPhotoIndex") //Index of photos
        let cache = Cache<UIImage>(name: "PhotoCache")
        
        if (current >= 10) {
            buffer() //resets current to 1
        }
    
        //Run the task
        var grResponse : GetRateStruct? = getPhotoID()
        var photo : Photo?
        
        //Assign photo
        if (grResponse != nil) {
            photo = Photo(image: cache[String(current)], id: grResponse?.id)
            
            current++
            defaults.setInteger(current, forKey: "CurrentPhotoIndex")
        } else {
            println("GrResponse nil!")
            photo = nil
        }
        
        return photo
    }
    
    
    // ----- Helpers ------ //
    

    private func buffer() {
        let cache = Cache<UIImage>(name: "PhotoCache")
        let defaults = NSUserDefaults.standardUserDefaults()
        defaults.setInteger(1, forKey: "CurrentPhotoIndex")
        
        for index in 1...10 {
            var id : String! = getPhotoID()!.id
            cache[String(index)] = getPhoto(id)
        }
    }
    
    private func getPhoto(id : String!) -> UIImage? {
        var image : UIImage?
        var done : Bool = false
        println("Starting to get a photo...")
        
        ImageManager.fetch("http://moderapp.com/images/" + id,
            progress: { (status: Double) in
                //println("updating some UI for this: \(status)") //useful if you have some kind of progress dialog as the image loads
            },success: { (data: NSData) in
                println("got an image!")
                image = UIImage(data: data) //set the image data
                done = true;
            }, failure: { (error: NSError) in
                println("failed to get an image: \(error)")
                done = true;
        })
        
        while (!done) {
            // Waits
        }
        
        println("Photo finishing")
        
        return image
    }
    
    private func getPhotoID() -> GetRateStruct? {
        var request = HTTPTask()
        request.requestSerializer = HTTPRequestSerializer()
        request.requestSerializer.headers[AppDelegate.cookie.name] = AppDelegate.cookie.value
        var done : Bool = false
        var structResponse : GetRateStruct?
        
        //Send GET
        request.GET("http://moderapp.com/getrate", parameters: nil, success: {(response: HTTPResponse) in
            if let data = response.responseObject as? NSData {
                structResponse = GetRateStruct(JSONDecoder(data))
                let str = NSString(data: data, encoding: NSUTF8StringEncoding)
                println(str)
                done = true;
                
            }
            },failure: {(error: NSError, response: HTTPResponse?) in
                println("error: \(error)")
                done = true;
        })
        
        while(!done) {
            //Waits
        }
        
        println("finished getID")
        
        return structResponse
    }
}