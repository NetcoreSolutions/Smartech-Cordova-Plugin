#import <Cordova/CDV.h>
#import <Cordova/CDVPlugin.h>

@interface SmartechAppInboxCordova : CDVPlugin {
  // Member variables go here.
}

- (void)getAppInboxCategoryList:(CDVInvokedUrlCommand *)command;

- (void)getAppInboxMessagesWithCategory:(CDVInvokedUrlCommand *)command;

- (void)getAppInboxMessages:(CDVInvokedUrlCommand *)command;

- (void)getAppInboxMessageCount:(CDVInvokedUrlCommand *)command;

- (void)getAppInboxMessagesByApiCall:(CDVInvokedUrlCommand *)command;

- (void)markMessageAsViewed:(CDVInvokedUrlCommand *)command;

- (void)markMessageAsDismissed:(CDVInvokedUrlCommand *)command;

- (void)markMessageAsClicked:(CDVInvokedUrlCommand *)command;

- (void)sendCordovaErrorPluginResultWithString:(NSString *)resultMessage andCommand:(CDVInvokedUrlCommand *)command;

- (void)sendCordovaSuccessPluginResultWithString:(NSString *)resultMessage andCommand:(CDVInvokedUrlCommand *)command;

- (void)sendCordovaSuccessPluginResultWithInt:(NSUInteger)resultMessage andCommand:(CDVInvokedUrlCommand *)command;

- (void)sendCordovaSuccessPluginResultWithDouble:(double)resultMessage andCommand:(CDVInvokedUrlCommand *)command;

- (void)sendCordovaSuccessPluginResultWithBool:(BOOL)resultMessage andCommand:(CDVInvokedUrlCommand *)command;

- (void)sendCordovaSuccessPluginResultWithArray:(NSArray *)resultMessage andCommand:(CDVInvokedUrlCommand *)command;

- (void)sendCordovaSuccessPluginResultWithDictionary:(NSDictionary *)resultMessage andCommand:(CDVInvokedUrlCommand *)command;

- (void)sendCordovaSuccessPluginResultAsNull:(CDVInvokedUrlCommand *)command;

@end
