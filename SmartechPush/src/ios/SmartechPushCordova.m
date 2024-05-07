#import "SmartechPushCordova.h"
#import "SmartPush/SmartPush.h"
#import "Smartech/Smartech.h"

@implementation SmartechPushCordova

#pragma mark - GDPR Methods

- (void)optPushNotification:(CDVInvokedUrlCommand *)command {
    [self.commandDelegate runInBackground:^{
    @try {
        BOOL optPushNotificationStatus = [[command argumentAtIndex:0 withDefault:@0] boolValue];
        [[SmartPush sharedInstance] optPushNotification:optPushNotificationStatus];
    } @catch (NSException *exception) {
        NSLog(@"🟥 SMTLogger(FATAL): Smartech Base: optPushNotification Exception: %@", exception);
    }
    }];
}

- (void)hasOptedPushNotification:(CDVInvokedUrlCommand *)command {
    [self.commandDelegate runInBackground:^{
    @try {
        BOOL optPushNotificationStatus = [[SmartPush sharedInstance] hasOptedPushNotification];
        [self sendCordovaSuccessPluginResultWithBool:optPushNotificationStatus andCommand:command];
    } @catch (NSException *exception) {
        NSLog(@"🟥 SMTLogger(FATAL): Smartech Base: hasOptedPushNotification Exception: %@", exception);
    }
    }];
}

- (void)registerForPushNotificationWithDefaultAuthorizationOptions:(CDVInvokedUrlCommand *)command {
    [self.commandDelegate runInBackground:^{
    @try {
        [[SmartPush sharedInstance] registerForPushNotificationWithDefaultAuthorizationOptions];
    } @catch (NSException *exception) {
        NSLog(@"🟥 SMTLogger(FATAL): Smartech Base: registerForPushNotificationWithDefaultAuthorizationOptions Exception: %@", exception);
    }
    }];
}

- (void)registerForPushNotificationWithAuthorizationOptions:(CDVInvokedUrlCommand *)command {
    [self.commandDelegate runInBackground:^{
    @try {
        BOOL alert = [[command argumentAtIndex:0 withDefault:@0] boolValue];
        BOOL badge = [[command argumentAtIndex:1 withDefault:@0] boolValue];
        BOOL sound = [[command argumentAtIndex:2 withDefault:@0] boolValue];
        
        UNAuthorizationOptions options = UNAuthorizationOptionNone;
        if (alert) {
            options += UNAuthorizationOptionAlert;
        }
        if (badge) {
            options += UNAuthorizationOptionBadge;
        }
        if (sound) {
            options += UNAuthorizationOptionSound;
        }
        [[SmartPush sharedInstance] registerForPushNotificationWithAuthorizationOptions:options];
    } @catch (NSException *exception) {
        NSLog(@"🟥 SMTLogger(FATAL): Smartech Base: registerForPushNotificationWithDefaultAuthorizationOptions Exception: %@", exception);
    }
    }];
}

- (void)setDevicePushToken:(CDVInvokedUrlCommand *)command {
    [self sendCordovaSuccessPluginResultWithString:@"" andCommand:command];
}

- (void)getDevicePushToken:(CDVInvokedUrlCommand *)command {
    [self.commandDelegate runInBackground:^{
    @try {
        NSString *deviceToken = [[Smartech sharedInstance] getDevicePushToken];
        deviceToken = deviceToken ? deviceToken : @"";
        [self sendCordovaSuccessPluginResultWithString:deviceToken andCommand:command];
    } @catch (NSException *exception) {
        NSLog(@"🟥 SMTLogger(FATAL): Smartech Base: getDevicePushToken Exception: %@", exception);
    }
    }];
}

- (void)isNotificationFromSmartech:(CDVInvokedUrlCommand *)command {
    [self.commandDelegate runInBackground:^{
    @try {
        NSDictionary *payload = [command argumentAtIndex:0 withDefault:@{}];
        BOOL isNotificationFromSmartech = [[SmartPush sharedInstance] isNotificationFromSmartech:payload];
        [self sendCordovaSuccessPluginResultWithBool:isNotificationFromSmartech andCommand:command];
    } @catch (NSException *exception) {
        NSLog(@"🟥 SMTLogger(FATAL): Smartech Base: isNotificationFromSmartech Exception: %@", exception);
    }
    }];
}


#pragma mark - Cordova Helper Methods

- (void)sendCordovaErrorPluginResultWithString:(NSString *)resultMessage andCommand:(CDVInvokedUrlCommand *)command {
    @try {
        CDVPluginResult *pluginResult = nil;
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:resultMessage];
        [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    } @catch (NSException *exception) {
        NSLog(@"🟥 SMTLogger(FATAL): Smartech Base: sendCordovaErrorPluginResultWithString Exception: %@", exception);
    }
}

- (void)sendCordovaSuccessPluginResultWithString:(NSString *)resultMessage andCommand:(CDVInvokedUrlCommand *)command {
    @try {
        CDVPluginResult *pluginResult = nil;
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:resultMessage];
        [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    } @catch (NSException *exception) {
        NSLog(@"🟥 SMTLogger(FATAL): Smartech Base: sendCordovaSuccessPluginResultWithString Exception: %@", exception);
    }
}

- (void)sendCordovaSuccessPluginResultWithInt:(NSUInteger)resultMessage andCommand:(CDVInvokedUrlCommand *)command {
    @try {
        CDVPluginResult *pluginResult = nil;
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsInt:(int)resultMessage];
        [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    } @catch (NSException *exception) {
        NSLog(@"🟥 SMTLogger(FATAL): Smartech Base: sendCordovaSuccessPluginResultWithInt Exception: %@", exception);
    }
}

- (void)sendCordovaSuccessPluginResultWithDouble:(double)resultMessage andCommand:(CDVInvokedUrlCommand *)command {
    @try {
        CDVPluginResult *pluginResult = nil;
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDouble:resultMessage];
        [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    } @catch (NSException *exception) {
        NSLog(@"🟥 SMTLogger(FATAL): Smartech Base: sendCordovaSuccessPluginResultWithDouble Exception: %@", exception);
    }
}

- (void)sendCordovaSuccessPluginResultWithBool:(BOOL)resultMessage andCommand:(CDVInvokedUrlCommand *)command {
    @try {
        CDVPluginResult *pluginResult = nil;
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsBool:resultMessage];
        [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    } @catch (NSException *exception) {
        NSLog(@"🟥 SMTLogger(FATAL): Smartech Base: sendCordovaSuccessPluginResultWithBool Exception: %@", exception);
    }
}

- (void)sendCordovaSuccessPluginResultWithArray:(NSArray *)resultMessage andCommand:(CDVInvokedUrlCommand *)command {
    @try {
        CDVPluginResult *pluginResult = nil;
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsArray:resultMessage];
        [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    } @catch (NSException *exception) {
        NSLog(@"🟥 SMTLogger(FATAL): Smartech Base: sendCordovaSuccessPluginResultWithArray Exception: %@", exception);
    }
}

- (void)sendCordovaSuccessPluginResultWithDictionary:(NSDictionary *)resultMessage andCommand:(CDVInvokedUrlCommand *)command {
    @try {
        CDVPluginResult *pluginResult = nil;
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:resultMessage];
        [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    } @catch (NSException *exception) {
        NSLog(@"🟥 SMTLogger(FATAL): Smartech Base: sendCordovaSuccessPluginResultWithDictionary Exception: %@", exception);
    }
}

- (void)sendCordovaSuccessPluginResultAsNull:(CDVInvokedUrlCommand *)command {
    @try {
        CDVPluginResult *pluginResult = nil;
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:(NSString *)[NSNull null]];
        [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    } @catch (NSException *exception) {
        NSLog(@"🟥 SMTLogger(FATAL): Smartech Base: sendCordovaSuccessPluginResultAsNull Exception: %@", exception);
    }
}

@end
