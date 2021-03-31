run()
  function run() {
  console.log("Run functions");

const sal=document.getElementById("patientBody");
  var head = document.getElementsByClassName("head");
  let head_count = 0;

  var neck = document.getElementsByClassName("neck");
  let neck_count = 0;

  var right_shoulder = document.getElementsByClassName("right-shoulder");
  let right_shoulder_count = 0;

  var chest = document.getElementsByClassName("chest");
  let chest_count = 0;

  var left_shoulder = document.getElementsByClassName("left-shoulder");
  let left_shoulder_count = 0;

  var back = document.getElementsByClassName("back");
  let back_count = 0;

  var right_hand = document.getElementsByClassName("right-hand");
  let right_hand_count = 0;

  var stomach = document.getElementsByClassName("stomach");
  let stomach_count = 0;

  var left_hand = document.getElementsByClassName("left-hand");
  let left_hand_count = 0;

  var left_leg = document.getElementsByClassName("left-leg");
  let left_leg_count = 0;

  var right_leg = document.getElementsByClassName("right-leg");
  let right_leg_count = 0;

  var other = document.getElementsByClassName("others");
  let other_count = 0;


  const allExaminations = [];
  fetch("http://localhost:8080/patient/body-examinations").
       then(response => response.json()).
       then(data => {
         for (let examination of data) {
           allExaminations.push(examination);
         }
       })



sal.addEventListener('click',(e)=>{


   for (let e of allExaminations) {

      switch (e.part) {
        case "EYE":
        case "BRAIN":
        case "HEAD":
          head_count++;
          break;

        case "HEARTH":
        case "CHEST":
          chest_count++;

          break;

        case "STOMACH":
        case "LUNG":
        case "KIDNEY":
        case "BOWELS":
          stomach_count++;

          break;

        case "SKIN":
        case "BLOOD":
        case "BONE":
        case "GENITALS":
          other_count++;
          break;

        case "NECK":
          neck_count++;
          head_count++;
          break;

        case "BACK":
          back_count++;
          break;

        case "LEG":
          if (e.side == "left") {
            left_leg_count=left_leg_count+1;
            break;
          }
          if (e.side == "right") {
            right_leg_count++;
            break;
          } else {
            left_leg_count++;
            right_leg_count++;
          }
          break;

        case "SHOULDER":
          if (e.side == "left") {
            left_shoulder_count++;
            break;
          }
          if (e.side == "right") {
            right_shoulder_count++;
            break;
          } else {
            right_shoulder_count++;
            left_shoulder_count++;
          }
          break;

        case "HAND":
          if (e.side == "left") {
            left_hand_count++;
            break;
          }
          if (e.side == "right") {
            right_hand_count++;
            break;
          } else {
            left_hand_count++;
            right_hand_count++;
          }
          break;
      }

  }

addColor(head,head_count)


addColor(neck,neck_count)


addColor(right_shoulder,right_shoulder_count )


addColor(chest,chest_count)
addColor(left_shoulder,left_shoulder_count )
addColor(back,back_count)
addColor(stomach,stomach_count)
addColor(right_hand,right_hand_count)
addColor(left_hand,left_hand_count)
addColor(left_leg,left_leg_count)
console.log("left_leg:  "+left_leg_count)
addColor(right_leg,right_leg_count)
addColor(other,other_count)
})

    function addColor(part, number) {
      console.log("add color")
    if (number >= 3 && number < 6) {
      for (let item of part) {
        item.classList.add("little");
      }
    }
    if (number >= 6 && number <= 10) {
      for (let item of part) {
        item.classList.add("avarage");
      }
    }

    if (number > 10) {
      for (let item of part) {
        item.classList.add("danger");
      }
    }
  }

}

