'use strict';

const ReactNative = require('react-native');
const {
    NativeModules,
} = ReactNative;

const Rfid = {};



Rfid.init = function () {
};

Rfid.show = (msg) => {
    NativeModules.Rfid.Toast(msg);
}


module.exports = Rfid;
