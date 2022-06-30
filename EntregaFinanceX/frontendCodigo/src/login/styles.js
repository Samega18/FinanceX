import {StyleSheet} from 'react-native'

export default StyleSheet.create({

container: {
    flex: 1,
    position: 'relative',

    backgroundColor: "#5ab349",
    width: "100%",
    height: "100%",
},

top: {
    flex: 1.1,
    flexDirection: "row",
},

topLeft: {
    flex: 2,
    flexDirection: 'column',
    alignItems: 'flex-start',
    justifyContent: 'space-around',
},

topLeftIcon: {
    flex: 0,
    marginLeft: 20,
},

topLeftText: {
    flex: 0,
    marginLeft: 40,

    fontSize: 35,
    fontWeight: '600',
    color: "white",

},

topRight: {
    flex: 1,
},

topImage: {
    flex: 1,
    width: "75%",
    resizeMode: 'contain',
},

bottom: {
    flex: 3.6,
    flexDirection: 'column',

    backgroundColor: "white",
    borderTopLeftRadius: 25,
    borderTopRightRadius: 25,
},

bottomTop: {
    flex: 1,
    flexDirection: 'column',

    alignItems: 'flex-start',
    justifyContent: 'center',
},

bottomTopT1: {
    color: "green",
    marginLeft: 30,
    fontSize: 40,
    fontWeight: '700',
},

bottomTopT2: {
    color: "gray",
    marginLeft: 30,
    fontSize: 20,

},

bottomMid: {
    flex: 2,

    width: "100%",
    justifyContent: 'center',
    alignItems: 'center',
},

bottomMidInput: {
    width: "80%",
    backgroundColor: "white",
    marginTop: 5,
    marginBottom: 5,
    
},

bottomMidEsqueceu: {
    width: "75%",
    marginTop: 5,

    alignItems: 'flex-end',
},

textEsqueceu: {
    color: "#5ab349",
    fontSize: 17,
    fontWeight: '400',
},

bottomBot: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
},

bottomBotB:{
    elevation: 0,
    backgroundColor: "#5ab349",
    borderRadius: 20,
    paddingVertical: 10,
    paddingHorizontal: 12,
    width: "70%",

    alignItems: 'center',
    justifyContent: 'center',

    marginBottom: 20,
},

bottomBotBText: {
    fontSize: 24,
    color: "white",
    fontWeight: '700',
},

bottomBotVazio: {
    flex: 1,
}

});