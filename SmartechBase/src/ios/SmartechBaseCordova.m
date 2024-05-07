#import "SmartechBaseCordova.h"
#import <Smartech/Smartech.h>

@implementation SmartechBaseCordova

#pragma mark - Cordova Plugin Lifecycle

- (void)pluginInitialize {
    [super pluginInitialize];
    [self setupObserver];
}


#pragma mark - Event Tracking Methods

- (void)trackAppInstall:(CDVInvokedUrlCommand *)command {
    [self.commandDelegate runInBackground:^{
        @try {
            [[Smartech sharedInstance] trackAppInstall];
        } @catch (NSException *exception) {
            NSLog(@"🟥 SMTLogger(FATAL): Smartech Base: trackAppInstall Exception: %@", exception);
        }
    }];
}

- (void)trackAppUpdate:(CDVInvokedUrlCommand *)command {
    [self.commandDelegate runInBackground:^{
        @try {
            [[Smartech sharedInstance] trackAppUpdate];
        } @catch (NSException *exception) {
            NSLog(@"🟥 SMTLogger(FATAL): Smartech Base: trackAppUpdate Exception: %@", exception);
        }
    }];
}

- (void)trackAppInstallUpdateBySmartech:(CDVInvokedUrlCommand *)command {
    [self.commandDelegate runInBackground:^{
        @try {
            [[Smartech sharedInstance] trackAppInstallUpdateBySmartech];
        } @catch (NSException *exception) {
            NSLog(@"🟥 SMTLogger(FATAL): Smartech Base: trackAppInstallUpdateBySmartech Exception: %@", exception);
        }
    }];
}

- (void)trackEvent:(CDVInvokedUrlCommand *)command {
    [self.commandDelegate runInBackground:^{
        @try {
            NSString *eventName = [command argumentAtIndex:0 withDefault:nil];
            NSDictionary *eventPayload = [command argumentAtIndex:1 withDefault:nil];
            if (eventPayload == nil) {
                eventPayload = @{};
            }
            if (eventName != nil && [eventName isKindOfClass:[NSString class]] && eventPayload != nil && [eventPayload isKindOfClass:[NSDictionary class]]) {
                [[Smartech sharedInstance] trackEvent:eventName andPayload:eventPayload];
            }
        } @catch (NSException *exception) {
            NSLog(@"🟥 SMTLogger(FATAL): Smartech Base: trackEvent Exception: %@", exception);
        }
    }];
}

- (void)login:(CDVInvokedUrlCommand *)command {
    [self.commandDelegate runInBackground:^{
        @try {
            NSString *userIdentity = [command argumentAtIndex:0 withDefault:nil];
            if (userIdentity != nil && [userIdentity isKindOfClass:[NSString class]]) {
                [[Smartech sharedInstance] login:userIdentity];
            }
        } @catch (NSException *exception) {
            NSLog(@"🟥 SMTLogger(FATAL): Smartech Base: login Exception: %@", exception);
        }
    }];
}

- (void)logoutAndClearUserIdentity:(CDVInvokedUrlCommand *)command {
    [self.commandDelegate runInBackground:^{
        @try {
            BOOL clearIdentityStatus = [[command argumentAtIndex:0 withDefault:@0] boolValue];
            [[Smartech sharedInstance] logoutAndClearUserIdentity:clearIdentityStatus];
        } @catch (NSException *exception) {
            NSLog(@"🟥 SMTLogger(FATAL): Smartech Base: logoutAndClearUserIdentity Exception: %@", exception);
        }
    }];
}

- (void)setUserIdentity:(CDVInvokedUrlCommand *)command {
    [self.commandDelegate runInBackground:^{
        @try {
            NSString *userIdentity = [command argumentAtIndex:0 withDefault:nil];
            if (userIdentity != nil && [userIdentity isKindOfClass:[NSString class]]) {
                [[Smartech sharedInstance] setUserIdentity:userIdentity];
            }
        } @catch (NSException *exception) {
            NSLog(@"🟥 SMTLogger(FATAL): Smartech Base: setUserIdentity Exception: %@", exception);
        }
    }];
}

- (void)getUserIdentity:(CDVInvokedUrlCommand *)command {
    [self.commandDelegate runInBackground:^{
        @try {
            NSString *userIdentity = [[Smartech sharedInstance] getUserIdentity];
            userIdentity = userIdentity ? userIdentity : @"";
            [self sendCordovaSuccessPluginResultWithString:userIdentity andCommand:command];
        } @catch (NSException *exception) {
            NSLog(@"🟥 SMTLogger(FATAL): Smartech Base: getUserIdentity Exception: %@", exception);
        }
    }];
}

- (void)clearUserIdentity:(CDVInvokedUrlCommand *)command {
    
    [self.commandDelegate runInBackground:^{
        @try {
            [[Smartech sharedInstance] clearUserIdentity];
        } @catch (NSException *exception) {
            NSLog(@"🟥 SMTLogger(FATAL): Smartech Base: clearUserIdentity Exception: %@", exception);
        }
    }];
}

- (void)updateUserProfile:(CDVInvokedUrlCommand *)command {
    [self.commandDelegate runInBackground:^{
        @try {
            NSDictionary *userProfile = [command argumentAtIndex:0 withDefault:nil];
            if (userProfile != nil && [userProfile isKindOfClass:[NSDictionary class]]) {
                [[Smartech sharedInstance] updateUserProfile:userProfile];
            }
        } @catch (NSException *exception) {
            NSLog(@"🟥 SMTLogger(FATAL): Smartech Base: updateUserProfile Exception: %@", exception);
        }
    }];
}


#pragma mark - GDPR Methods

- (void)optTracking:(CDVInvokedUrlCommand *)command {
    [self.commandDelegate runInBackground:^{
        @try {
            BOOL optTrackingStatus = [[command argumentAtIndex:0 withDefault:@0] boolValue];
            [[Smartech sharedInstance] optTracking:optTrackingStatus];
        } @catch (NSException *exception) {
            NSLog(@"🟥 SMTLogger(FATAL): Smartech Base: optTracking Exception: %@", exception);
        }
    }];
}

- (void)hasOptedTracking:(CDVInvokedUrlCommand *)command {
    [self.commandDelegate runInBackground:^{
        @try {
            BOOL optTrackingStatus = [[Smartech sharedInstance] hasOptedTracking];
            [self sendCordovaSuccessPluginResultWithBool:optTrackingStatus andCommand:command];
        } @catch (NSException *exception) {
            NSLog(@"🟥 SMTLogger(FATAL): Smartech Base: hasOptedTracking Exception: %@", exception);
        }
    }];
}

- (void)optInAppMessage:(CDVInvokedUrlCommand *)command {
    [self.commandDelegate runInBackground:^{
        @try {
            BOOL optInAppMessageStatus = [[command argumentAtIndex:0 withDefault:@0] boolValue];
            [[Smartech sharedInstance] optInAppMessage:optInAppMessageStatus];
        } @catch (NSException *exception) {
            NSLog(@"🟥 SMTLogger(FATAL): Smartech Base: optInAppMessage Exception: %@", exception);
        }
    }];
}

- (void)hasOptedInAppMessage:(CDVInvokedUrlCommand *)command {
    [self.commandDelegate runInBackground:^{
        @try {
            BOOL optInAppMessageStatus = [[Smartech sharedInstance] hasOptedInAppMessage];
            [self sendCordovaSuccessPluginResultWithBool:optInAppMessageStatus andCommand:command];
        } @catch (NSException *exception) {
            NSLog(@"🟥 SMTLogger(FATAL): Smartech Base: hasOptedInAppMessage Exception: %@", exception);
        }
    }];
}


#pragma mark - Location Methods

- (void)setUserLocation:(CDVInvokedUrlCommand *)command {
    [self.commandDelegate runInBackground:^{
        @try {
            double lat = [[command argumentAtIndex:0] doubleValue];
            double lon = [[command argumentAtIndex:1] doubleValue];
            CLLocationCoordinate2D userLocationCordinate = CLLocationCoordinate2DMake(lat, lon);
            [[Smartech sharedInstance] setUserLocation:userLocationCordinate];
        } @catch (NSException *exception) {
            NSLog(@"🟥 SMTLogger(FATAL): Smartech Base: setUserLocation Exception: %@", exception);
        }
    }];
}


#pragma mark - Helper Methods

- (void)getAppId:(CDVInvokedUrlCommand *)command {
    [self.commandDelegate runInBackground:^{
        @try {
            NSString *smartechAppId = [[Smartech sharedInstance] getAppId];
            [self sendCordovaSuccessPluginResultWithString:smartechAppId andCommand:command];
        } @catch (NSException *exception) {
            NSLog(@"🟥 SMTLogger(FATAL): Smartech Base: getAppId Exception: %@", exception);
        }
    }];
}

- (void)getDeviceGuid:(CDVInvokedUrlCommand *)command {
    [self.commandDelegate runInBackground:^{
        @try {
            NSString *deviceGuid = [[Smartech sharedInstance] getDeviceGuid];
            [self sendCordovaSuccessPluginResultWithString:deviceGuid andCommand:command];
        } @catch (NSException *exception) {
            NSLog(@"🟥 SMTLogger(FATAL): Smartech Base: getDeviceGuid Exception: %@", exception);
        }
    }];
}

- (void)getSDKVersion:(CDVInvokedUrlCommand *)command {
    [self.commandDelegate runInBackground:^{
        @try {
            NSString *sdkVersion = [[Smartech sharedInstance] getSDKVersion];
            [self sendCordovaSuccessPluginResultWithString:sdkVersion andCommand:command];
        } @catch (NSException *exception) {
            NSLog(@"🟥 SMTLogger(FATAL): Smartech Base: getSDKVersion Exception: %@", exception);
        }
    }];
}


#pragma mark - Empty Methods Used In Android

- (void)setDeeplinkInit:(CDVInvokedUrlCommand *)command {
}

- (void)setDevicePushToken:(CDVInvokedUrlCommand *)command {
}


#pragma mark - Internal Helper Methods

- (void)setupObserver {
    // Add the observer for deeplink handling.
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(handleDeeplinkAndCustomPayload:) name:kSMTDeeplinkNotificationIdentifier object:nil];
}

- (NSString *)convertToJSONString:(id)data {
    // To convert the data into JSON format.
    NSString *jsonString = @"{}";
    @try {
        NSError *error;
        NSData *jsonData = [NSJSONSerialization dataWithJSONObject:data options:0 error:&error];
        if (jsonData != nil) {
            jsonString = [[NSString alloc] initWithData:jsonData encoding:NSUTF8StringEncoding];
        }
        else {
            NSLog(@"SMT : Could not convert to JSON due to exception : %@", error.localizedDescription);
        }
    }
    @catch (NSException *exception) {
        NSLog(@"SMT : Deeplink & custom payload conversion exception = %@", exception.reason);
    }
    @finally {
        return jsonString;
    }
}


#pragma mark - Deeplink Handling

- (void)handleDeeplinkAndCustomPayload:(NSNotification *)notification {
    
    NSString *jsonString = [self convertToJSONString:notification.userInfo];
    NSString *js = [NSString stringWithFormat:@"cordova.fireDocumentEvent('%@', %@);", kSMTDeeplinkJavaScriptCallbackIdentifier, jsonString];
    
    dispatch_after(dispatch_time(DISPATCH_TIME_NOW, 1 * NSEC_PER_SEC), dispatch_get_main_queue(), ^{
        [self.commandDelegate evalJs:js];
    });
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

