import React from 'react';
import {
    SafeAreaView,
    StatusBar,
    Button,
    TouchableOpacity,
    Text,
    Image,
    View,
  } from 'react-native';
import styles from './styles';

function BarNavigate({ navigation }){
    return(
        <View style={styles.container}>
            <StatusBar backgroundColor={"#5ab349"} animated={true}/>
            <Text>Login</Text>
        </View>
    )
}

export default BarNavigate;