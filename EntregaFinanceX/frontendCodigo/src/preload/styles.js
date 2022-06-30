import {StyleSheet} from 'react-native'

export default StyleSheet.create({

container: {
    flex: 1,
    position: 'relative',

    backgroundColor: "#5ab349",
    width: "100%",
    height: "100%",
},

imageContainer: {
    flex: 1,
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    width: "100%",
    height: "auto",
    paddingTop: 70,
},

image: {
    flex: 1,
    width: "50%",
    height: "auto",
    resizeMode: 'contain',
},

textImage: {
    flex: 1,
    fontSize: 40,
    color: "white",
    fontWeight: '700',
    justifyContent: 'center',
    alignItems: 'center',
},

loading: {
    flex: 1,
},

});