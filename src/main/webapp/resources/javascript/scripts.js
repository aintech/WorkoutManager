var pressed = '';
document.addEventListener('keydown', function (event) {
//                pressed += event.key;
    if (event.key === 'Enter') {
        document.getElementById('onEnterForm').submit();
    }
});

function fireTimer (secs) {
    var initial = secs;
    
    var seconds = Math.floor(initial % 60);
    var minutes = Math.floor((initial / 60) % 60);
    var hours = Math.floor((initial / 3600) % 60);

    setTimerExterior(hours, minutes, seconds);

    function tick () {
        initial--;
        if (initial <= 0) {
            stepOffTimeout(timerId);
        }
        seconds = Math.floor(initial % 60);
        minutes = Math.floor((initial / 60) % 60);
        hours = Math.floor((initial / 3600) % 60);
        
        setTimerExterior(hours, minutes, seconds);
    }

    timerId = setInterval(tick, 1000);
}

function setTimerExterior (hours, minutes, seconds) {
    var timer = document.getElementById('currentTimer');
    
//    timer.innerHTML = (10 > hours ? "0" : "") + hours + ":" + (10 > minutes ? "0" : "") + minutes + ":" + (10 > seconds ? "0" : "") + seconds;
    timer.innerHTML = (10 > minutes ? "0" : "") + minutes + ":" + (10 > seconds ? "0" : "") + seconds;
}

function stepOffTimeout (timerId) {
//    document.getElementById('onEnterForm').submit();
    clearInterval(timerId);
    playSignal();
    setTimerExterior(0, 0, 0);
}

function playSignal () {
    var audio = document.getElementById('signal');
    audio.play();
}