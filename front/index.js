/**
 * @format
 */
import React from 'react';
import {AppRegistry} from 'react-native';
import App from './App';
import Routes from './src/routes';
import {name as appName} from './app.json';
import { Provider as PaperProvider } from 'react-native-paper';
import Welcome from './src/Teste/teste';

export default function Main() {
    return (
      <PaperProvider>
        <Routes />
      </PaperProvider>
    );
}

AppRegistry.registerComponent(appName, () => Main);
