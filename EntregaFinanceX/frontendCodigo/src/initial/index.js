import React from 'react';
import {
    SafeAreaView,
    ScrollView,
    StatusBar,
    StyleSheet,
    Button,
    TouchableOpacity,
    Text,
    Image,
    useColorScheme,
    View,
  } from 'react-native';
import styles from './styles';

const initial_data = require('../../assets/initial/teste.png');

function InitialPage({ navigation }){
    return(
        <View style={styles.container}>
            <StatusBar backgroundColor={"#5ab349"} animated={true}/>
            <View style={styles.imageContainer}>
                <Image style={styles.image} source={initial_data}/>
            </View>
            
            <View style={styles.texts}>
                <Text style={styles.textsTittle}>Bem Vindo</Text>
                <View style={styles.pContainer}>
                    <Text style={styles.p}>Organize suas despesas</Text>
                    <Text style={styles.p2}>Simples e intuitivo</Text>
                </View> 
                
            </View>

            <View style={styles.buttons}>
                <TouchableOpacity  style={styles.b1} onPress={() => navigation.navigate('Login')}>
                    <Text style={styles.b1Text}>Entrar</Text>
                </TouchableOpacity>

                <TouchableOpacity  style={styles.b2} onPress={() => navigation.navigate('Register')}>
                    <Text style={styles.b2Text}>Cadastrar</Text>
                </TouchableOpacity>
            </View>  

        </View>
    )
}

export default InitialPage;