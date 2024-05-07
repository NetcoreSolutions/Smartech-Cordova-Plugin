#import <Cordova/CDV.h>
#import <Cordova/CDVPlugin.h>

@interface SmartechPushCordova : CDVPlugin {
  // Member variables go here.
}

#pragma mark - GDPR Methods

/**
 @brief This method is used to opt push notifications.
 
 @discussion If you call this method then we will opt in or opt out the user of recieving push notifications.
 */
- (void)optPushNotification:(CDVInvokedUrlCommand *)command;

/**
 @brief This method is used to get the current status of opt push notification.
 
 @discussion If you call this method you will get the current status of the tracking which can be used to render the UI at app level.
 */
- (void)hasOptedPushNotification:(CDVInvokedUrlCommand *)command;

- (void)registerForPushNotificationWithDefaultAuthorizationOptions:(CDVInvokedUrlCommand *)command;

- (void)registerForPushNotificationWithAuthorizationOptions:(CDVInvokedUrlCommand *)command;



@end
