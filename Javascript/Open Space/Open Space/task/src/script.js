const cbi = document.getElementsByClassName("check-buttons_item");
const li = document.getElementsByClassName("levers_item");
const rocket = document.querySelector(".rocket");

function desactive() {
    for (let i = 0; i < cbi.length; i++) {
        cbi[i].disabled = true;
    }
    for (let i = 0; i < li.length; i++) {
        li[i].disabled = true;
    }
    document.getElementById("btn_launch").disabled = true;
    document.getElementById("pwd").value = "";
}

function move_ok() {
    let nb_cbi_checked = 0;
    for (let i = 0; i < cbi.length; i++) {
        if (cbi[i].checked) {
            nb_cbi_checked++;
        }
    }
    let nb_li_max_set = 0;
    for (let i = 0; i < li.length; i++) {
        if (li[i].value === "100") {
            nb_li_max_set++;
        }
    }
    console.log(nb_cbi_checked);
    console.log(nb_li_max_set);
    if ((nb_cbi_checked === cbi.length) && (nb_li_max_set === li.length)) {
        document.getElementById("btn_launch").disabled = false;
        document.getElementById("btn_launch").addEventListener("click", function() {
            setInterval(function(){
                move();
            }, 100);
        });
    }
}

function move() {
     console.log("Go!");
     let x = Number(getComputedStyle(rocket).left.substr(0, getComputedStyle(rocket).left.length - 2));
     rocket.style.left = 10 + x + "px";
     let y = Number(getComputedStyle(rocket).bottom.substr(0, getComputedStyle(rocket).bottom.length - 2));
     rocket.style.bottom = 10 + y + "px";
}

window.addEventListener("load", function() {
    desactive();
    document.getElementById("pwd").enabled = true;
    document.getElementById("btn_ok").enabled = true;
    document.getElementById("btn_ok").addEventListener("click", function() {
        if (document.getElementById("pwd").value === "TrustNo1") {
            for (let i = 0; i < cbi.length; i++) {
                cbi[i].disabled = false;
                cbi[i].onchange = function() { move_ok(); };
            }
            for (let i = 0; i < li.length; i++) {
                li[i].disabled = false;
                li[i].onchange = function() { move_ok(); };
            }
        } else {
            desactive();
        }
    });
});
