const correctPass = "TrustNo1";
const inputs = document.getElementsByTagName("input");
//     0        1            2-7      8-12            13
// "pwd", "btn_ok", "check-boxN", "sliderN", "btn_launch"
const checkboxes = document.getElementsByClassName("check-buttons_item");
const levers = document.getElementsByClassName("levers_item");

function disableInputs() {
    for (let i = 2; i <= 13; i++) {
        inputs[i].setAttribute("disabled", "");
    }
}

function enableInputs() {
    for (let i = 2; i <= 12; i++) {
        inputs[i].removeAttribute("disabled");
    }
}

function validatePass() {
    let pass = document.getElementById("pwd").value;
    if (pass === correctPass) {
        enableInputs();
        checkLaunch();
    } else {
        console.log("incorrect password");
        disableInputs();
        document.getElementById("pwd").value = "";
    }
}

function checkLaunch() {
    let launchButton = document.getElementById("btn_launch");
    if (areControlsOn()) {
        launchButton.removeAttribute("disabled");
    } else {
        launchButton.setAttribute("disabled", "");
    }
}

function areControlsOn() {
    let nb_cbi_checked = 0;
    for (let i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i].checked) {
            nb_cbi_checked++;
        }
    }

    let nb_li_max_set = 0;
    for (let i = 0; i < levers.length; i++) {
        if (levers[i].value === "100") {
            nb_li_max_set++;
        }
    }

    console.log(nb_cbi_checked);
    console.log(nb_li_max_set);
    return (nb_cbi_checked === checkboxes.length) && (nb_li_max_set === levers.length);
}

function launch() {
    let rocket = document.querySelector(".rocket");
    let x = Number(getComputedStyle(rocket).left
        .substr(0, getComputedStyle(rocket).left
            .length - 2));
    let y = Number(getComputedStyle(rocket).bottom
        .substr(0, getComputedStyle(rocket).bottom
            .length - 2));
    let moves = 0;
    let rocketMoving = setInterval(function(dist) {
        // move horizontally
        x += dist - 2;
        rocket.style.left = x + "px";
        // move vertically
        y += dist;
        rocket.style.bottom = y + "px";
        moves++;
        if (moves > 100) {
            clearInterval(rocketMoving);
        }
    }, 25, 5);
}

function initialize() {
    disableInputs();

    // event listener for password input
    document.getElementById("btn_ok")
        .addEventListener("click", validatePass);

    // event listener for all checkboxes and levers
    for (let i = 2; i <= 12 ; i++) {
        inputs[i].onchange = checkLaunch;
    }

    // event listener for launch button
    let launchButton = document.getElementById("btn_launch");
    launchButton.addEventListener("click", launch);
    launchButton.addEventListener("mousedown", function() {launchButton.style.backgroundColor = "blue";});
    launchButton.addEventListener("mouseup", function() {launchButton.style.backgroundColor = "red";});
}

window.addEventListener("load", initialize);