//
//  SendRate.swift
//  Moder
//
//  Created by Evan Conrad on 3/29/15.
//  Copyright (c) 2015 Evan Conrad. All rights reserved.
//

import Foundation
import SwiftHTTP

class SendRate {
    
    func send(choice : Bool, id : String) {
        var request = HTTPTask()
        
        var yesNo :String
        if (choice) {
            yesNo = "yes"
        } else {
            yesNo = "no"
        }
        
        request.GET("http://moderapp.com/SubmitRating", parameters: ["choice" : yesNo, "photoID" : id], success: {(response: HTTPResponse) in
            if let data = response.responseObject as? NSData {
                let str = NSString(data: data, encoding: NSUTF8StringEncoding)
            }
            },failure: {(error: NSError, response: HTTPResponse?) in
                println("error: \(error)")
                println(response?.URL?.description)
        })
    }
}