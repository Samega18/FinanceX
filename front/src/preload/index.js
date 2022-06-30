import React,{ Component, useState, useEffect } from 'react';
import {Node} from 'react';
import {
  SafeAreaView,
  ScrollView,
  StatusBar,
  StyleSheet,
  ActivityIndicator,
  Text,
  Image,
  View,
} from 'react-native';
import styles from './styles';

import {useNavigation} from '@react-navigation/native';

import IconApp from '../../assets/initial/IconAndroid_SimbWhite.png';

function Preload(){

    const navigation = useNavigation();

    useEffect(() => {
        const checkToken = async() => {

            // if(valueMan == 0){
            //     //Sistema offline
            //     navigation.reset({
            //     routes: [{name: 'AppManut'}]
            //     });
            // }
            // else if(token != null && emailToken != '' && passToken != ''){
            //     //validar o token 

            //     if(await LogarFB(emailToken, passToken) ==  true){
            //         navigation.reset({
            //             routes: [{name: 'BarNavigate'}]
            //         });
            //     }else{
            //         console.log('Não está logado')
            //         navigation.reset({
            //             routes: [{name: 'Login'}]
            //         });
            //     }
            // } else {
            //     navigation.reset({
            //         routes: [{name: 'Login'}]
            //     });
            // }

            setTimeout(() =>{
                navigation.reset({
                     routes: [{name: 'Initial'}]
                     });
            }, 100)

        };

        checkToken();
    }, );

    return(
        <View style={styles.container}>
            <StatusBar backgroundColor={"#5ab349"} animated={true}/>
            <View style={styles.imageContainer}>
                <Image style={styles.image} source={IconApp}/>
                <Text style={styles.textImage}>FinanceX</Text>
            </View>
            <View style={styles.loading}>
                <ActivityIndicator size={60} color={"white"}/>
            </View>
        </View>
    )
}

export default Preload;