
const zoom = document.getElementById('setZoomPhoto')

const newPhoto = document.getElementById('popup')
const alertBackground = document.getElementById('alertBackground')

const alertMessage = document.getElementById('alertMessage')
async function zoomPhoto(e){
e.preventDefault();
console.log("test")
zoom.style.display = "block";
newPhoto.style.display = "block";
newPhoto.innerHTML=`<img src="${e.target.src}"  alt="..." class=" i   " style="">`




}
async function closeMe(){
zoom.style.display = "none";
newPhoto.style.display = "none";
alertBackground.style.display = "none";
           alertMessage.style.display = "none";
}
async function closeAlert(){

alertBackground.style.display = "none";
           alertMessage.style.display = "none";
}

setTimeout(function(e){
           alertBackground.style.display = "block";
           alertMessage.style.display = "block";
           }, 5000);