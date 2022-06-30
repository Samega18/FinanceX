import React from 'react';
import {
    SafeAreaView,
    StatusBar,
    Button,
    TouchableOpacity,
    KeyboardAvoidingView,
    Text,
    Image,
    View,
  } from 'react-native';
import styles from './styles';

import { TextInput } from 'react-native-paper';

import Icon from 'react-native-vector-icons/Octicons';
import LoginEnter from '../../assets/initial/login_enter.png';

function Login({ navigation }){
    
    const [email, setEmail] = React.useState('');
    const [pass, setPass] = React.useState('');
    const [passView, setPassView] = React.useState('eye-off');

    return(
        <KeyboardAvoidingView style={styles.container} 
        behavior="height" >
            <StatusBar backgroundColor={"#5ab349"} animated={true}/>
            <View style={styles.top}>
                <View style={styles.topLeft}>
                    <Icon name="arrow-left" size={50} color="white" style={styles.topLeftIcon} 
                        onPress={() => navigation.navigate('Initial')}/>
                    <Text style={styles.topLeftText}>Entrar</Text>
                </View>
                <View style={styles.topRight}>
                    <Image source={LoginEnter} style={styles.topImage}/>
                </View>
            </View>
            <View style={styles.bottom}>
                <View style={styles.bottomTop}>
                    <Text style={styles.bottomTopT1}>Bem Vindo</Text>
                    <Text style={styles.bottomTopT2}>Digite seus dados nos campos abaixo</Text>
                </View>
                <View style={styles.bottomMid}>
                    <TextInput
                        mode="outlined"
                        label="Email"
                        placeholder="Digite seu email"
                        value={email}
                        onChangeText={email => setEmail(email)}
                        
                        style={styles.bottomMidInput}
                        outlineColor="gray"
                        activeOutlineColor="#5ab349"
                        theme={{ colors: { placeholder: "gray", text: "black" } }}
                    />
                    <TextInput
                        mode="outlined"
                        label="Senha"
                        placeholder="Digite sua senha"
                        value={pass}
                        onChangeText={pass => setPass(pass)}

                        secureTextEntry={passView == 'eye-off' ? true : false}
                        right={
                        <TextInput.Icon name={passView} color="gray" 
                        onPress={() => passView == 'eye-off' ? setPassView('eye') : setPassView('eye-off')}/>}
                        
                        style={styles.bottomMidInput}
                        outlineColor="gray"
                        activeOutlineColor="#5ab349"
                        theme={{ colors: { placeholder: "gray", text: "black",  } }}
                    />
                    <View style={styles.bottomMidEsqueceu}>
                        <Text style={styles.textEsqueceu}>Esqueceu a senha?</Text>
                    </View>
                </View>
                <View style={styles.bottomBot}>
                    <TouchableOpacity  style={styles.bottomBotB} onPress={() => navigation.navigate('Login')}>
                        <Text style={styles.bottomBotBText}>Entrar</Text>
                    </TouchableOpacity>
                </View>
            </View>
        </KeyboardAvoidingView>
    )
}

export default Login;