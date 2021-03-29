solve();
function solve(){
console.log("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
const doctorList = document.getElementById('doctorsRow')
const searchBar = document.getElementById('searchDoctor')

const allDoctors = [];

fetch("http://localhost:8080/doctors/allRest").
then(response => response.json()).
then(data => {
  for (let doctor of data) {
    allDoctors.push(doctor);
  }
})


console.log(searchBar);


searchBar.addEventListener('keyup', (e) => {
  const searchingCharacters = searchBar.value.toLowerCase();
  let filteredAlbums = allDoctors.filter(album => {
    return album.fullName.toLowerCase().includes(searchingCharacters);
  });
  console.log(filteredAlbums)
  displayAlbums(filteredAlbums);
})


const displayAlbums = (albums) => {
doctorList.innerHTML =""
  doctorList.innerHTML = albums
      .map((a) => {
         return `<div class="col-lg-4 col-md-6 hvr-bob margin-bottom-45px">
                                                  <div class="background-white box-shadow">
                                                      <div class="thum">

                                                          <a href="/doctors/doctor/${a.id}">
                                                              <!-- //if no image ->img src="http://placehold.it/400x400" -->
                                                              <img src="${a.photo}"
                                                                   alt="..." class=" i   " style=""></a>
                                                      </div>
                                                      <div class="padding-30px">
                                                          <span class="text-grey-2">Department: ${a.medicalBranchesEnum}</span>
                                                          <h5 class="margin-tb-15px"><a class="text-dark" href="/doctors/doctor/${a.id}">Dr. ${a.fullName}</a></h5>
                                                          <div class="rating clearfix">
                                                              <ul class="float-left">
                                                                  <li class="active"></li>
                                                                  <li class="active"></li>
                                                                  <li class="active"></li>
                                                                  <li class="active"></li>
                                                                  <li></li>
                                                              </ul>
                                                          </div>
                                                      </div>
                                                  </div>
                                              </div>
                                              </div>

                      `
              })
              .join('');

        }

}


