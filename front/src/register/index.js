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
    ScrollView,
    Keyboard,
  } from 'react-native';
import styles from './styles';

import { TextInput } from 'react-native-paper';
import { KeyboardAwareScrollView } from 'react-native-keyboard-aware-scroll-view';

import Icon from 'react-native-vector-icons/Octicons';
import LoginEnter from '../../assets/initial/register_img.png';
import { useEffect } from 'react/cjs/react.production.min';

function Register({ navigation }){
    
    const [name, setName] = React.useState('');
    const [email, setEmail] = React.useState('');
    const [pass, setPass] = React.useState('');
    const [pass2, setPass2] = React.useState('');

    const [keyboardStatus, setKeyboardStatus] = React.useState(false);

    React.useEffect(() =>{
        
        const showSubscription = Keyboard.addListener("keyboardDidShow", () => {
            setKeyboardStatus(true);
          });
        const hideSubscription = Keyboard.addListener("keyboardDidHide", () => {
            setKeyboardStatus(false);
          });
      
          return () => {
            showSubscription.remove();
            hideSubscription.remove();
          };
    },[]);
    return(
        <KeyboardAvoidingView style={styles.container} behavior="height" 
        keyboardVerticalOffset={0}>
                <StatusBar backgroundColor={"#5ab349"} animated={true}/>
                <SafeAreaView style={{flex: 1, width: "100%", height: "100%"}}>
                <ScrollView style={{flex: 1, width: "100%", height: "100%",}} 
                scrollEnabled={keyboardStatus}>
                <View style={styles.top}>
                    <View style={styles.topLeft}>
                        <Icon name="arrow-left" size={50} color="white" style={styles.topLeftIcon} 
                            onPress={() => navigation.navigate('Initial')}/>
                        <Text style={styles.topLeftText}>Cadastrar</Text>
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
                            label="Nome e sobrenome"
                            placeholder="Digite seu nome"
                            value={name}
                            onChangeText={name => setName(name)}
                            
                            style={styles.bottomMidInput}
                            outlineColor="gray"
                            activeOutlineColor="#5ab349"
                            theme={{ colors: { placeholder: "gray", text: "black" } }}
                        />
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
    
                            secureTextEntry
    
                            style={styles.bottomMidInput}
                            outlineColor="gray"
                            activeOutlineColor="#5ab349"
                            theme={{ colors: { placeholder: "gray", text: "black",  } }}
                        />
                        <TextInput
                            mode="outlined"
                            label="Confirmar senha"
                            placeholder="Digite sua senha"
                            value={pass2}
                            onChangeText={pass2 => setPass2(pass2)}
    
                            secureTextEntry
                            
                            style={styles.bottomMidInput}
                            outlineColor="gray"
                            activeOutlineColor="#5ab349"
                            theme={{ colors: { placeholder: "gray", text: "black",  } }}
                        />
    
                    </View>
                    <View style={styles.bottomBot}>
                        <TouchableOpacity  style={styles.bottomBotB} onPress={() => navigation.navigate('Login')}>
                            <Text style={styles.bottomBotBText}>Cadastrar</Text>
                        </TouchableOpacity>
                    </View>
                </View>
                </ScrollView>
                </SafeAreaView>
            </KeyboardAvoidingView>
        )
}

export default Register;