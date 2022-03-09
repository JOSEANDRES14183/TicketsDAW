//Constats
//Without '/' at the end
const API_URL = "https://api.arteux.me"

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

// Wait for the deviceready event before using any of Cordova's device APIs.
// See https://cordova.apache.org/docs/en/latest/cordova/events/events.html#deviceready


document.addEventListener('deviceready', onDeviceReady, false);

function onDeviceReady() {
    // Cordova is now initialized. Have fun!
    console.log('Running cordova-' + cordova.platformId + '@' + cordova.version);
    document.getElementById('deviceready').classList.add('ready');

    var modal = document.querySelector("#modal");
    var span = document.querySelector("#modal .close");
    let resultsBox = document.querySelector("#results");
    let app = document.querySelector("div.app");

    createListeners();

    function createListeners() {
        span.onclick = function() {
            modal.style.display = "none";
        }

        window.onclick = function(event) {
            if (event.target == modal) {
                modal.style.display = "none";
            }
        }

        var btn = document.querySelector("#testBtn");

        // btn.onclick = ()=>{
        //     processQR(prompt())
        // };
        btn.onclick = scanQR;


    }

    function scanQR(){
        cordova.plugins.barcodeScanner.scan(
            function (result) {
                if(!result.cancelled)
                    processQR(result.text)
                // alert("We got a barcode\n" +
                //     "Result: " + result.text + "\n" +
                //     "Format: " + result.format + "\n" +
                //     "Cancelled: " + result.cancelled);
            },
            function (error) {
                processError(error)
            },
            {
                preferFrontCamera : false, // iOS and Android
                showFlipCameraButton : true, // iOS and Android
                showTorchButton : true, // iOS and Android
                torchOn: true, // Android, launch with the torch switched on (if available)
                saveHistory: true, // Android, save scan history (default false)
                prompt : "Place a barcode inside the scan area", // Android
                resultDisplayDuration: 500, // Android, display scanned text for X ms. 0 suppresses it entirely, default 1500
                formats : "QR_CODE,PDF_417", // default: all but PDF_417 and RSS_EXPANDED
                orientation : "portrait", // Android only (portrait|landscape), default unset so it rotates with the device
                disableAnimations : true, // iOS
                disableSuccessBeep: false // iOS and Android
            }
        );
    }

    function processQR(text){
        var xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function () {
            var DONE = 4; // readyState 4 means the request is done.
            var OK = 200; // status 200 is a successful return.
            if (xhr.readyState === DONE) {
                if (xhr.status === OK) {
                    processSuccess(JSON.parse(xhr.responseText)); // 'This is the returned text.'
                } else {
                    processError('Error: ' + xhr.status); // An error occurred during the request.
                }
            }
        };
        xhr.open('GET', API_URL + "/api/eventos/check/" + text);
        xhr.send(null);
        // fetch()
        //     .then(response => response.json())
        //     .then(data => {
        //         if(data.success){
        //             ;
        //         }
        //         else{
        //             processError("Ha ocurrido un error")
        //         }
        //     })

    }

    function processError(error){
        modal.style.display = "block";
        resultsBox.innerHTML = error;
    }

    function processSuccess(data){
        modal.style.display = "block";

        if(!data.success)
            return processError("Ticket invalido")

        let text = "";

        text += "<p>" + (data.sesion_numerada ? data.sesion_numerada.evento.titulo : data.tipo_entrada.sesion.evento.titulo) + "</p>"
        text += "<p>" +( data.sesion_numerada ? "Butaca: " + data.butaca.num_butaca : data.tipo_entrada.nombre) + "</p>"
        text += "<p> Direccion: " + (data.sesion_numerada ? data.sesion_numerada.sala.direccion + ", " + data.sesion_numerada.sala.ciudad.nombre + ", " + data.sesion_numerada.sala.nombre :
            data.tipo_entrada.sesion.sala.direccion + ", " + data.tipo_entrada.sesion.sala.ciudad.nombre + ", " + data.tipo_entrada.sesion.sala.nombre) + "</p>"
        text += "<p>" + (data.sesion_numerada ? + data.sesion_numerada.fecha_ini : data.tipo_entrada.sesion.fecha_ini) + "</p>"

        resultsBox.innerHTML = text;
    }
}
