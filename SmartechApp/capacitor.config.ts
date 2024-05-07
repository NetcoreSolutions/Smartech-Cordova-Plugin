import { CapacitorConfig } from '@capacitor/cli';

const config: CapacitorConfig = {
  appId: 'com.netcore.SmartechCordovaApp',
  appName: 'Smartech Cordova App',
  webDir: 'dist',
  server: {
    androidScheme: 'https'
  },
  ios: {
    handleApplicationNotifications: false
  }
};

export default config;
