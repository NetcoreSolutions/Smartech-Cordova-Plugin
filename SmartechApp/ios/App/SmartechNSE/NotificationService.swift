//
//  NotificationService.swift
//  SmartechNSE
//
//  Created by Jobin Kurian on 27/02/24.
//

import UserNotifications
import SmartPush

class NotificationService: UNNotificationServiceExtension {
    
    let smartechServiceExtension = SMTNotificationServiceExtension()
    
    override func didReceive(_ request: UNNotificationRequest, withContentHandler contentHandler: @escaping (UNNotificationContent) -> Void) {
        if SmartPush.sharedInstance().isNotification(fromSmartech:request.content.userInfo) {
            smartechServiceExtension.didReceive(request, withContentHandler: contentHandler)
        }
    }
    
    override func serviceExtensionTimeWillExpire() {
        smartechServiceExtension.serviceExtensionTimeWillExpire()
    }
    
}
