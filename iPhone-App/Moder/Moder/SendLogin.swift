//
//  SendLogin.swift
//  Moder
//
//  Created by Evan Conrad on 2/15/15.
//  Copyright (c) 2015 Evan Conrad. All rights reserved.
//

import Foundation

class SendLogin{
    
    
    func sendRequest() {
        println("This is happening")
        var url : NSURL! = NSURL(string: "http://moder.whelastic.net/login")
        let cachePolicy = NSURLRequestCachePolicy.ReloadIgnoringLocalCacheData
        var request = NSMutableURLRequest(URL: url!, cachePolicy: cachePolicy, timeoutInterval: 2.0)
        var cookieID: String = "User_ID=123iojewijroewriow"
        var cookieName: String = "Cookie"
        
        let boundaryConstant = "----------V2ymHFg03esomerandEthanAndInsertGirlHereSittingInATreeKISSINGfhbqgZCaKO6jy";
        let contentType = "multipart/form-data; boundary=" + boundaryConstant
        NSURLProtocol.setProperty(contentType, forKey: "Content-Type", inRequest: request)
        request.HTTPMethod = "POST"
        
        // set data
        var email = "bob@gmail.com"
        var pwd = "qwertyuiop"
        var dataString = "email="
        dataString += email
        dataString += "&pwd="
        dataString += pwd
        let requestBodyData = (dataString as NSString).dataUsingEncoding(NSUTF8StringEncoding)
        request.HTTPBody = requestBodyData
        
        let config = NSURLSessionConfiguration.defaultSessionConfiguration()
        let session = NSURLSession(configuration: config)
        let dataTask = session.dataTaskWithRequest(request, completionHandler: {(data, response, error) in
            var error: AutoreleasingUnsafeMutablePointer<NSError?> = nil
            
                let jsonResult: NSDictionary! =  NSJSONSerialization.JSONObjectWithData(data, options: nil, error: error) as? NSDictionary!
            
            
        });
        dataTask.resume()
        
    }
}