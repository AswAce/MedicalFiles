solve();
function solve(){

const searchPatient = document.getElementById('searchPatient')
const patients = document.getElementById('allPatients')

const allPatients = [];

fetch("http://localhost:8080/admin/allPatients").
then(response => response.json()).
then(data => {
  for (let patient of data) {
    allPatients.push(patient);
  }
})
console.log(allPatients)
searchPatient.addEventListener('keyup', (e) => {
  const searchingCharacters = searchPatient.value.toLowerCase();
  let filteredPatients = allPatients.filter(patient => {

    return (patient.fullName.toLowerCase().includes(searchingCharacters)
    );
  });

  displayPatients(filteredPatients);
})

const displayPatients = (p) => {
patients.innerHTML =""
  patients.innerHTML = p
      .map((a) => {
         return `       <div class="col-lg-6 margin-bottom-45px">
                                     <div class="background-white thum-hover box-shadow hvr-float">
                                         <div class="padding-30px">
                                                                                       <div class="margin-left-85px">
                                                 <a class="d-inline-block text-dark text-medium margin-right-20px" href="#">${a.fullName} </a>
                                                    <a class="d-inline-block text-dark text-medium margin-right-20px" href="#">Age:${a.age} </a>
                                                 <a href="/admin/delete-patient/${a.userId}" class="d-inline-block margin-lr-20px text-grey-2 text-up-small"><i
                                                         class="far fa-window-close"></i> Delete</a>

                                             </div>
                                         </div>
                                     </div>
                                 </div>
                      `
              })
              .join('');

        }






}