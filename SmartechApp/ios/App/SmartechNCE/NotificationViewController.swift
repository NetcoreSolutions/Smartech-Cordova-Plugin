//
//  NotificationViewController.swift
//  SmartechNCE
//
//  Created by Jobin Kurian on 27/02/24.
//

import UIKit
import SmartPush

class NotificationViewController: SMTCustomNotificationViewController {
    
    @IBOutlet var customPNView: UIView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.customView = customPNView
    }

}
