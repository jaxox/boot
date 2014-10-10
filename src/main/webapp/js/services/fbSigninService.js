/**
 * Created by Jay on 9/24/2014.
 */
 'use strict';

app.factory('fbSigninService', function($http){
    return{
        fb_signin:function(scope){
            console.log("enter fb sign in service");
            var $promise=$http.post('/signin/facebook/',JSON.stringify(scope)); //send data to the api

            //testing only
            $promise.then(function(msg){
                if(msg.data=='succes') console.log('succes login');
                else console.log('error login');
            });

        }
    }
});