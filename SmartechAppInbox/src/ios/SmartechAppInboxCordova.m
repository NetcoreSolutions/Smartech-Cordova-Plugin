#import "SmartechAppInboxCordova.h"
#import <SmartechAppInbox/SmartechAppInbox.h>

NSString *const kNotificationCategoryCarouselPortrait = @"SmartechCarouselPortraitNotification";
NSString *const kNotificationCategoryCarouselLandscape = @"SmartechCarouselLandscapeNotification";
NSString *const kNotificationCategoryCarouselFallback = @"SmartechCarouselFallbackNotification";

static NSUInteger units =
NSCalendarUnitYear |
NSCalendarUnitMonth |
NSCalendarUnitDay |
NSCalendarUnitWeekday |
NSCalendarUnitWeekdayOrdinal |
kCFCalendarUnitHour |
kCFCalendarUnitMinute |
kCFCalendarUnitSecond;

@implementation SmartechAppInboxCordova

- (void)getAppInboxCategoryList:(CDVInvokedUrlCommand *)command {
    NSMutableArray *categoryData = [[NSMutableArray alloc] init];
    [self.commandDelegate runInBackground:^{
        @try {
            NSArray *appInboxCategoryArray  = [[NSArray alloc] initWithArray:[[SmartechAppInbox sharedInstance] getAppInboxCategoryList]];
            for (SMTAppInboxCategoryModel *catObj in appInboxCategoryArray) {
                NSMutableDictionary *categoryDict = [[NSMutableDictionary alloc] initWithDictionary:@{ @"categoryName": [catObj categoryName],
                                                                                                       @"isSelected": @([catObj isSelected]) }];
                [categoryData addObject:categoryDict];
            }
        } @catch (NSException *exception) {
            NSLog(@"🟥 SMTLogger(FATAL): Smartech AppInbox: getAppInboxCategoryList Exception: %@", exception);
        } @finally {
            [self sendCordovaSuccessPluginResultWithArray:categoryData andCommand:command];
        }
    }];
}

- (void)getAppInboxMessagesWithCategory:(CDVInvokedUrlCommand *)command {
    __block NSArray *appInboxCategoryArray = [[NSMutableArray alloc] init];
    __block NSArray <SMTAppInboxMessage *> *appInboxMessagesArray = @[];
    [self.commandDelegate runInBackground:^{
        @try {
            appInboxCategoryArray = [command argumentAtIndex:0 withDefault:@[]];
            NSMutableArray <SMTAppInboxCategoryModel *> *appInboxArray = [[NSMutableArray alloc] init];
            
            for (int i = 0; i< appInboxCategoryArray.count; i++) {
                SMTAppInboxCategoryModel *notificationPayload = [[SMTAppInboxCategoryModel alloc] init];
                notificationPayload.categoryName = [[appInboxCategoryArray objectAtIndex:i] objectForKey:@"categoryName"];
                notificationPayload.isSelected = [NSNumber numberWithBool:[[appInboxCategoryArray objectAtIndex:i] objectForKey:@"isSelected"]];
                [appInboxArray addObject:notificationPayload];
            }
            appInboxMessagesArray = [[SmartechAppInbox sharedInstance] getAppInboxMessageWithCategory:appInboxArray];
        } @catch (NSException *exception) {
            NSLog(@"🟥 SMTLogger(FATAL): Smartech AppInbox: getAppInboxMessagesWithCategory Exception: %@", exception);
        } @finally {
            [self sendCordovaSuccessPluginResultWithArray:appInboxMessagesArray andCommand:command];
        }
    }];
}

- (void)getAppInboxMessages:(CDVInvokedUrlCommand *)command {
    __block NSArray *formatedMessageArray = @[];
    [self.commandDelegate runInBackground:^{
        @try {
            NSNumber *messageType = [command argumentAtIndex:0 withDefault:@(1)];
            NSArray *appInboxMessagesArray = @[];
            switch ([messageType integerValue]) {
                case 2:
                    appInboxMessagesArray = [[NSArray alloc] initWithArray:[[SmartechAppInbox sharedInstance] getAppInboxMessages:SMTAppInboxMessageTypeRead]];
                    break;
                case 3:
                    appInboxMessagesArray = [[NSArray alloc] initWithArray:[[SmartechAppInbox sharedInstance] getAppInboxMessages:SMTAppInboxMessageTypeUnread]];
                    break;
                    
                default:
                    appInboxMessagesArray = [[NSArray alloc] initWithArray:[[SmartechAppInbox sharedInstance] getAppInboxMessages:SMTAppInboxMessageTypeDismiss]];
                    break;
            }
            formatedMessageArray = [self createAppInboxMessages:appInboxMessagesArray];
        }  @catch (NSException *exception) {
            NSLog(@"🟥 SMTLogger(FATAL): Smartech AppInbox: getAppInboxMessages Exception: %@", exception);
        } @finally {
            [self sendCordovaSuccessPluginResultWithArray:formatedMessageArray andCommand:command];
        }
    }];
}

- (void)getAppInboxMessageCount:(CDVInvokedUrlCommand *)command {
    [self.commandDelegate runInBackground:^{
        @try {
            NSNumber *messageType = [command argumentAtIndex:0 withDefault:@1];
            NSUInteger messageCount = 0;
            switch ([messageType integerValue]) {
                case 2:
                    messageCount = [[SmartechAppInbox sharedInstance] getAppInboxMessageCount:SMTAppInboxMessageTypeRead];
                    break;
                case 3:
                    messageCount = [[SmartechAppInbox sharedInstance] getAppInboxMessageCount:SMTAppInboxMessageTypeUnread];
                    break;
                    
                default:
                    messageCount = [[SmartechAppInbox sharedInstance] getAppInboxMessageCount:SMTAppInboxMessageTypeDismiss];
                    break;
            }
            
            [self sendCordovaSuccessPluginResultWithInt:messageCount andCommand:command];
        } @catch (NSException *exception) {
            NSLog(@"🟥 SMTLogger(FATAL): Smartech AppInbox: getAppInboxMessageCount Exception: %@", exception);
        }
    }];
}

- (void)getAppInboxMessagesByApiCall:(CDVInvokedUrlCommand *)command {
    __block NSArray <SMTAppInboxMessage *> *appInboxMessagesArray = @[];
    __block NSArray *formatedMessageArray = @[];
    [self.commandDelegate runInBackground:^{
        @try {
            NSNumber *messageLimit = [command argumentAtIndex:0 withDefault:@(0)];
            NSNumber *messageType = [command argumentAtIndex:1 withDefault:@(0)];
            NSArray *appInboxCategoryArray = [command argumentAtIndex:2 withDefault:@[]];
            
            SMTAppInboxFilter *inboxFilter = [[SmartechAppInbox sharedInstance] getPullToRefreshParameter];
            inboxFilter.limit = [messageLimit integerValue];
            NSString *inboxDataType;
            switch ([messageType integerValue]) {
                default:
                    inboxDataType = @"ALL";
                    break;
                case 2:
                    inboxDataType = @"LATEST";
                    break;
                case 3:
                    inboxDataType = @"EARLIEST";
                    break;
            }
            
            inboxFilter.direction = inboxDataType.lowercaseString;
            [[SmartechAppInbox sharedInstance] getAppInboxMessage:inboxFilter withCompletionHandler:^(NSError *error,BOOL status) {
                if (appInboxCategoryArray.count == 0){
                    appInboxMessagesArray = [[SmartechAppInbox sharedInstance] getAppInboxMessageWithCategory:[[[SmartechAppInbox sharedInstance] getAppInboxCategoryList] mutableCopy]];
                    formatedMessageArray = [self createAppInboxMessages:appInboxMessagesArray];
                }
                else{
                    NSMutableArray <SMTAppInboxCategoryModel *> *appInboxArray  = [[NSMutableArray alloc] init];
                    for (int i = 0; i< appInboxCategoryArray.count; i++) {
                        SMTAppInboxCategoryModel *notificationPayload = [[SMTAppInboxCategoryModel alloc] init];
                        notificationPayload.categoryName = [[appInboxCategoryArray objectAtIndex:i] objectForKey:@"categoryName"];
                        notificationPayload.isSelected = [NSNumber numberWithBool:[[appInboxCategoryArray objectAtIndex:i] objectForKey:@"isSelected"]];
                        [appInboxArray addObject:notificationPayload];
                    }
                    appInboxMessagesArray = [[SmartechAppInbox sharedInstance] getAppInboxMessageWithCategory:appInboxArray];
                    formatedMessageArray = [self createAppInboxMessages:appInboxMessagesArray];
                }
                [self sendCordovaSuccessPluginResultWithArray:formatedMessageArray andCommand:command];
            }];
        } @catch (NSException *exception) {
            NSLog(@"🟥 SMTLogger(FATAL): Smartech AppInbox: getAppInboxMessagesByApiCall Exception: %@", exception);
        }
    }];
}

- (void)markMessageAsViewed:(CDVInvokedUrlCommand *)command {
    [self.commandDelegate runInBackground:^{
        @try {
            NSDictionary *inboxDictionary = [command argumentAtIndex:0 withDefault:@{}];
            NSString *trid = inboxDictionary[@"trid"];
            if (trid) {
                SMTAppInboxMessage *appInboxMessage = [[SmartechAppInbox sharedInstance] getInboxMessageById:trid];
                [[SmartechAppInbox sharedInstance] markMessageAsViewed:appInboxMessage];
            }
        } @catch (NSException *exception) {
            NSLog(@"🟥 SMTLogger(FATAL): Smartech AppInbox: markMessageAsViewed Exception: %@", exception);
        }
    }];
}

- (void)markMessageAsDismissed:(CDVInvokedUrlCommand *)command {
    [self.commandDelegate runInBackground:^{
        @try {
            NSDictionary *inboxDictionary = [command argumentAtIndex:0 withDefault:@{}];
            NSString *trid = inboxDictionary[@"trid"];
            if (trid) {
                SMTAppInboxMessage *appInboxMessage = [[SmartechAppInbox sharedInstance] getInboxMessageById:trid];
                [[SmartechAppInbox sharedInstance] markMessageAsDismissed:appInboxMessage withCompletionHandler:^(NSError *error, BOOL status) {
                    if (status) {
                        [self sendCordovaSuccessPluginResultWithBool:status andCommand:command];
                    }
                }];
            }
        } @catch (NSException *exception) {
            NSLog(@"🟥 SMTLogger(FATAL): Smartech AppInbox: markMessageAsDismissed Exception: %@", exception);
        }
    }];
}

- (void)markMessageAsClicked:(CDVInvokedUrlCommand *)command {
    [self.commandDelegate runInBackground:^{
        @try {
            NSString *trid = [command argumentAtIndex:0 withDefault:@""];
            NSString *deeplink = [command argumentAtIndex:1 withDefault:@""];
            SMTAppInboxMessage *appInboxMessage = [[SmartechAppInbox sharedInstance] getInboxMessageById:trid];
            [[SmartechAppInbox sharedInstance] markMessageAsClicked:appInboxMessage withDeeplink:deeplink];
        } @catch (NSException *exception) {
            NSLog(@"🟥 SMTLogger(FATAL): Smartech AppInbox: markMessageAsClicked Exception: %@", exception);
        }
    }];
}

- (NSString *)timeIntervalStringFromDate:(NSDate *)date {
    
    NSDate *earlier = date;
    NSDate *today = [NSDate date];
    
    NSCalendar *gregorianCalendar = [[NSCalendar alloc] initWithCalendarIdentifier:NSCalendarIdentifierGregorian];
    NSDateComponents *components = [gregorianCalendar components:units fromDate:earlier toDate:today options:0];
    
    NSInteger years = [components year];
    
    if (years >= 1) {
        return [NSString stringWithFormat:@"%ld %@ ago",(long)years, ((years > 1)? @"years" : @"year")];
    }
    
    NSInteger months = [components month];
    
    if (months >= 1) {
        return [NSString stringWithFormat:@"%ld %@ ago",(long)months, ((months > 1)? @"months" : @"month")];
    }
    
    NSInteger days = [components day];
    
    if (days >= 1) {
        return [NSString stringWithFormat:@"%ld %@ ago",(long)days, ((days > 1)? @"days" : @"day")];
    }
    
    NSInteger hour = [components hour];
    
    if (hour >= 1) {
        return [NSString stringWithFormat:@"%ld %@ ago",(long)hour, ((hour > 1)? @"hours" : @"hour")];
    }
    
    NSInteger minute = [components minute];
    
    if (minute >= 1) {
        return [NSString stringWithFormat:@"%ld %@ ago",(long)minute, ((minute > 1)? @"minutes" : @"minute")];
    }
    
    NSInteger second = [components second];
    
    if (second >= 1 && second <= 59) {
        return [NSString stringWithFormat:@"%ld %@ ago",(long)second, ((second > 1)? @"second" : @"second")];
    }
    
    return @"";
}

- (NSArray *)createAppInboxMessages:(NSArray *)appInboxMessagesArray {
    NSMutableArray *formartedMessageArray = [[NSMutableArray alloc] init];
    @try {
        for (SMTAppInboxMessage *messageObj in appInboxMessagesArray) {
            SMTAppInboxMessageModel *notificationPayload = messageObj.payload;
            NSString *notificationCategory = notificationPayload.aps.category;
            NSString *publishDate = [self timeIntervalStringFromDate:messageObj.publishedDate];
            NSMutableDictionary *messageDict = [[NSMutableDictionary alloc] initWithDictionary:
                                                @{ @"title": (notificationPayload.aps.alert.title != nil) ?  notificationPayload.aps.alert.title : @"",
                                                   @"subtitle": (notificationPayload.aps.alert.subtitle != nil) ? notificationPayload.aps.alert.subtitle : @"",
                                                   @"description": ( notificationPayload.aps.alert.body != nil) ?  notificationPayload.aps.alert.body : @"",
                                                   @"notificationType": (notificationPayload.smtPayload.type != nil) ? notificationPayload.smtPayload.type : @"",
                                                   @"notificationCategory": (notificationPayload.smtPayload.appInBoxCategory != nil) ? notificationPayload.smtPayload.appInBoxCategory : @"",
                                                   @"trid": (notificationPayload.smtPayload.trid != nil) ? notificationPayload.smtPayload.trid : @"",
                                                   @"deeplink": (notificationPayload.smtPayload.deeplink != nil) ? notificationPayload.smtPayload.deeplink : @"",
                                                   @"mediaURL": (notificationPayload.smtPayload.mediaURL != nil) ? notificationPayload.smtPayload.mediaURL : @"",
                                                   @"status": (notificationPayload.smtPayload.status != nil) ? notificationPayload.smtPayload.status : @"",
                                                   @"publishedDate": publishDate
                                                }];
            
            if ([notificationCategory isEqualToString:kNotificationCategoryCarouselPortrait] || [notificationCategory isEqualToString:kNotificationCategoryCarouselLandscape] || [notificationCategory isEqualToString:kNotificationCategoryCarouselFallback]) {
                
                NSArray <SMTAICarousel *>  *carouselAppInboxArray = notificationPayload.smtPayload.carousel;
                NSMutableArray *carouselArray = [[NSMutableArray alloc] init];
                for (SMTAICarousel *carouselObj in carouselAppInboxArray) {
                    NSMutableDictionary * carouselDict = [[NSMutableDictionary alloc] initWithDictionary:@{ @"imgUrl": (carouselObj.imgUrl != nil) ? carouselObj.imgUrl : @"",
                                                                                                            @"imgUrlPath": (carouselObj.imgUrlPath != nil) ? carouselObj.imgUrlPath : @"",
                                                                                                            @"imgTitle": (carouselObj.imgTitle != nil) ? carouselObj.imgTitle : @"",
                                                                                                            @"imgMsg": (carouselObj.imgMsg != nil) ? carouselObj.imgMsg : @"",
                                                                                                            @"imgDeeplink": (carouselObj.imgDeeplink != nil) ? carouselObj.imgMsg : @""
                                                                                                         }];
                    [carouselArray addObject:carouselDict];
                }
                [messageDict setObject:carouselArray forKey:@"carousel"];
            }
            
            if (notificationPayload.smtPayload.actionButton.count > 0) {
                NSArray <SMTAIActionButton *> *actionButtonAppInboxArray = notificationPayload.smtPayload.actionButton;
                NSMutableArray *actionArray = [[NSMutableArray alloc] init];
                for (SMTAIActionButton *actionObj in actionButtonAppInboxArray) {
                    NSMutableDictionary *actionDict = [[NSMutableDictionary alloc] initWithDictionary:
                                                       @{ @"actionDeeplink": (actionObj.actionDeeplink != nil) ? actionObj.actionDeeplink : @"",
                                                          @"actionName": (actionObj.actionName != nil) ? actionObj.actionName : @"",
                                                          @"aTyp": (actionObj.actionType != nil) ? actionObj.actionType : @"",
                                                          @"callToAction": (actionObj.actionName != nil) ? actionObj.actionName : @"",
                                                          @"config_ctxt":(actionObj.actionConfig.count >   0) ?      [actionObj.actionConfig objectForKey:@"ctxt"] : @""
                                                       }];
                    [actionArray addObject:actionDict];
                }
                [messageDict setObject:actionArray forKey:@"actionButton"];
            }
            else{
                [messageDict setObject:@[] forKey:@"actionButton"];
            }
            
            NSDictionary *smtCustomPayload = notificationPayload.smtCustomPayload ? notificationPayload.smtCustomPayload : @{};
            [messageDict setObject:smtCustomPayload forKey:@"smtCustomPayload"];
            
            [formartedMessageArray addObject:messageDict];
        }
    }  @catch (NSException *exception) {
        NSLog(@"🟥 SMTLogger(FATAL): Smartech AppInbox: getAppInboxMessages Exception: %@", exception);
    } @finally {
        return formartedMessageArray;
    }
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
