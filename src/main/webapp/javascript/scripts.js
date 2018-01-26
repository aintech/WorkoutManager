var pressed = '';
document.addEventListener('keydown', function (event) {
//                pressed += event.key;
    if (event.key === 'Enter') {
        document.getElementById('onEnterForm').submit();
    }
});

function fireTimer (secs) {
    var start = new Date();
    var end = new Date();
    end.setTime(start.getTime() + (secs * 1000));

    var seconds = Math.floor((end - start) / 1000);
    var minutes = Math.floor(seconds / 60);
    var hours = Math.floor(minutes / 60);

    setTimerExterior(hours, minutes, seconds);

    function tick () {
        if (seconds === 0) {
            if (minutes === 0) {
                if (hours === 0) {
                    stepOffTimeout(timerId);
                } else {
                    hours--;
                    minutes = 59;
                    seconds = 59;
                }
            } else {
                minutes--;
                seconds = 59;
            }
        } else {
            seconds--;
        }
        setTimerExterior(hours, minutes, seconds);
    }

    timerId = setInterval(tick, 1000);
}

function setTimerExterior (hours, minutes, seconds) {
    var timer = document.getElementById('timer');
    timer.innerHTML = (10 > hours ? "0" : "") + hours + ":" + (10 > minutes ? "0" : "") + minutes + ":" + (10 > seconds ? "0" : "") + seconds;
}

function stepOffTimeout (timerId) {
//    document.getElementById('onEnterForm').submit();
    clearInterval(timerId);
    playSignal();
}

function playSignal () {
    var audio = document.getElementById('signal');
    audio.play();
}