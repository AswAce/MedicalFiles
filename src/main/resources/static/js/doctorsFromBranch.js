solve();
function solve(){

const department = document.getElementById('department')
const addDoctor = document.getElementById('doctorsFromDepartment')
const button = document.getElementById('click')


const allDoctors = [];

fetch("http://localhost:8080/doctors/allRest").
then(response => response.json()).
then(data => {
  for (let doctor of data) {
    allDoctors.push(doctor);
  }
})





button.addEventListener('click', (e) => {
console.log("one value")


let doctorsFromDepartment = allDoctors.filter(doctor => {
    return doctor.medicalBranchesEnum==department.value;
  });
if(doctorsFromDepartment.length===0){
addDoctor.innerHTML =` <option  th:value=''  >Still no doctors for this department</option>`
}
else {
addDoctor.innerHTML = doctorsFromDepartment.map((d) => {
return ` <option  th:value=${d.fullName}  >${d.fullName}-${d.id}</option>`
}).join('');
}


})




}
