/**
 * 
 */
console.log("sessiontrack loaded!")

function getCookie(cname) {
    var name = cname + "=";
    var decodedCookie = decodeURIComponent(document.cookie);
    var ca = decodedCookie.split(';');
    for(var i = 0; i <ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}

function setCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays*24*60*60*1000));
    var expires = "expires="+ d.toUTCString();
    document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
}

function calcOffset() {
    var serverTime = getCookie('serverTime');
    serverTime = serverTime==null ? null : Math.abs(serverTime);
    console.log(serverTime);
    var clientTimeOffset = (new Date()).getTime() - serverTime;
    console.log(clientTimeOffset);
    setCookie('clientTimeOffset', clientTimeOffset);
}

function checkSession() {
    var sessionExpiry = Math.abs(getCookie('sessionExpiry'));
    var timeOffset = Math.abs(getCookie('clientTimeOffset'));
    var localTime = (new Date()).getTime();
    console.log("localTime - timeOffset : "+(localTime - timeOffset));
    console.log(sessionExpiry+15000);
    if (localTime - timeOffset > (sessionExpiry-15000)) {
//        window.close();
        //location.reload(true);
    	if(confirm("Your session is about to expire. Do you want to extend?")){
    		location.reload(true);
    	}else{
    		
    	}
//    	console.log("session expired!");
    } else {
        setTimeout('checkSession()', 10000);
    }
}

calcOffset();
checkSession();
