/**
 * Created by Jay on 9/24/2014.
 */
 'use strict';

app.factory('loginService', function($http){
    return{
        login:function(user){
            console.log("enter login service");
            var $promise=$http.post('/api/login',JSON.stringify(user)); //send data to the api

            //testing only
            $promise.then(function(msg){
                if(msg.data=='succes') console.log('succes login');
                else console.log('error login');
            });

        }
    }
});