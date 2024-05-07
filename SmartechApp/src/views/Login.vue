<template>
  <ion-page>
    <ion-header>
      <ion-toolbar>
        <ion-title class="ion-text-center">
          Login
        </ion-title>
      </ion-toolbar>
    </ion-header>

    <ion-content>

      <div class="login-container">

        <ion-label>
          <h1>Login to Your Account</h1>
          <p>When you do login to your application you should call the login method of SmartechSDK</p>
        </ion-label>

        <ion-item style="margin-top: 30px;">
          <ion-label position="floating">Enter Primary Key</ion-label>
          <ion-input v-model="primaryKey" @ionInput="validatePrimaryKey"></ion-input>
        </ion-item>

        <ion-button class="custom-button" style="margin-top: 40px;" expand="full" @click="login">Login</ion-button>
        <ion-button expand="full" @click="createAccount">Create An Account</ion-button>
        <ion-label class="bold-label" @click="continueAsGuest">Continue as Guest</ion-label>
        <ion-label class="bold-label" @click="continueAsQA">All SDK Methods</ion-label>
      </div>
    </ion-content>
  </ion-page>
</template>

<script lang="ts">
import { IonContent, IonHeader, IonPage, IonInput, IonButton, IonToolbar, IonLabel, alertController } from '@ionic/vue';
import { ref, onMounted, inject } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { isLoggedInAsGuestFlag } from '@/main';
import { Preferences } from '@capacitor/preferences';
import SmartechBaseCordova from 'smartech-base-cordova';


export default {

  components: { IonContent, IonHeader, IonToolbar, IonPage, IonInput, IonButton, IonLabel },

  setup() {
    const primaryKey = ref('');
    const isValid = ref<boolean>(false);

    const router = useRouter();

    const login = async () => {

      if (isValid.value) {
        // set the key in preferences
        await Preferences.set({
          key: 'primaryKey',
          value: primaryKey.value,
        });


        await Preferences.set({
          key: 'isLoggedIn',
          value: 'true',
        });

        doLogin(primaryKey.value)

        console.log('Login clicked  ' + primaryKey.value);
        router.push('/home');
      } else {
        showErrorDialog()
      }
    };

    const doLogin = (identity: string) => {
      SmartechBaseCordova.login(identity)
    }

    const showErrorDialog = async () => {
      const alert = await alertController.create({
        header: 'Error',
        message: 'Enter a valid primary key.',
        buttons: ['OK']
      });
      await alert.present();
    };

    const validatePrimaryKey = (ev) => {
      const value = ev.target.value;
      // Perform validation logic here
      isValid.value = value.length > 0;
    };

    const createAccount = () => {
      router.push('/registration')
    }

    const continueAsGuest = async () => {
      await Preferences.set({
        key: 'primaryKey',
        value: "",
      });
      console.log("value of globalflag is " + isLoggedInAsGuestFlag.value);
      isLoggedInAsGuestFlag.value = true;
      SmartechBaseCordova.clearUserIdentity();
      router.push('/home');
    }

    const onInput = () => {
      // Handle input changes if needed
    };

    const continueAsQA = async () => {
      await Preferences.set({
        key: 'primaryKey',
        value: "",
      });
      router.push('/SDKGrid');
    }

    onMounted(() => {
      // Set focus on the username input when the component is mounted
      // (document.getElementById('usernameInput') as HTMLIonInputElement).setFocus();
    });

    return { primaryKey, login, continueAsGuest, createAccount, validatePrimaryKey, continueAsQA };
  },
};
</script>

<style scoped>
.login-container {
  display: flex;
  padding: 20px;
  flex-direction: column;
  align-items: center;
}

.custom-button {
  --color: white;
  --background: #FF7F50;
  --ripple-color: deeppink;
  --box-shadow: 0 2px 6px 0 rgb(0, 0, 0, 0.25);
}

ion-item {
  width: 100%;
}

ion-input {
  width: 100%;
}

ion-button {
  width: 80%;
  --box-shadow: 0 2px 6px 0 rgb(0, 0, 0, 0.25);
}

.bold-label {
  font-weight: bold;
  font-size: 1.2rem;
  /* Adjust the size as needed */
  margin-top: 20px;
}
</style>