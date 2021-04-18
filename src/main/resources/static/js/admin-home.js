solve();
function solve(){

const searchPatientOrDoctorExaminations = document.getElementById('searchExamination')
const searchedExaminations = document.getElementById('ExaminationName')

const allExaminations = [];

fetch("http://localhost:8080/admin/allExaminations").
then(response => response.json()).
then(data => {
  for (let examination of data) {
    allExaminations.push(examination);
  }
})

searchPatientOrDoctorExaminations.addEventListener('keyup', (e) => {
  const searchingCharacters = searchPatientOrDoctorExaminations.value.toLowerCase();
  let filteredExaminations = allExaminations.filter(examination => {

    return (examination.patientName.toLowerCase().includes(searchingCharacters)
    );
  });

  displayExaminations(filteredExaminations);
})

const displayExaminations = (e) => {
searchedExaminations.innerHTML =""
  searchedExaminations.innerHTML = e
      .map((a) => {
         return `
     <div class="col-lg-6 margin-bottom-45px ">
             <div class="background-white thum-hover box-shadow  hvr-float">
                 <div class="padding-30px full-width  width-350px max-width-350px height-300px ">
                     <img src="${a.patientPhoto}"
                          width="50"
                          height="50"
                          class="float-left margin-right-20px border-radius-60 margin-bottom-20px" alt="">
                     <div class="margin-left-85px">
                         <a class="d-block text-dark text-medium margin-bottom-5px"
                          >Patient: ${a.patientName} </a>
                         <div class="d-block padding-tb-5px">Date : <a href="#" class="text-main-color"
                                                                       >${a.localDateTime}</a></div>
                         <div class="d-block padding-tb-5px">Phone : <a href="#" class="text-main-color"
                                                                        >${a.phone}</a>
                         </div>
                     </div>
                     <div>
                         <textarea type="text" row="3" cols="30" class="margin-top-15px text-grey-2 overflow-auto"
                                 >Complain: ${a.complain} </textarea>

                     </div>

                     <a
                             href="/examinations/examination/${a.id}"
                             class="d-inline-block text-grey-2 text-up-small"><i
                             class="far fa-file-alt"></i> See details</a>

                         <p
                            class="d-inline-block margin-lr-20px text-grey-2 text-up-small text-warning font-weight-bold">
                             <i class="fas fa-user-injured"></i>${a.progressionEnum}</p>

                         <a
                                 href="/examinations/examination/${a.id}"
                                 class="d-inline-block text-black text-up-small"><i
                                 class="fas fa-trash-alt"></i>Delete Examination</a>



                 </div>
             </div>
         </div>

                      `
              })
              .join('');

        }






const searchReview = document.getElementById('searchReview')
const reviewsList = document.getElementById('reviewsList')

const allReviews = [];

fetch("http://localhost:8080/admin/allReviews").
then(response => response.json()).
then(data => {
  for (let review of data) {

    allReviews.push(review);
  }
})
//
//console.log(reviewsList);
//console.log(allReviews)
searchReview.addEventListener('keyup', (e) => {
  const searchingCharacters = searchReview.value.toLowerCase();
  let filteredReview = allReviews.filter(review => {

    return (review.doctorName.toLowerCase().includes(searchingCharacters)
    );
  });

  displayReviews(filteredReview);
})


const displayReviews = (reviews) => {
reviewsList.innerHTML =""
  reviewsList.innerHTML = reviews
      .map((r) => {
         return `
  <div class="margin-bottom-30px box-shadow ">
        <div class="padding-30px background-white">

            <div class="row">
                <div class="col">
                    <ul class="commentlist padding-0px margin-0px list-unstyled text-grey-3">
                        <li class="border-bottom-1 border-grey-1 margin-bottom-20px">

                            <div class="margin-left-35px">
                        <span   class="d-inline-block text-dark text-medium margin-right-20px">
                                   ${r.patientName}
                        </span>
                                <span
                                      class="d-inline-block text-dark text-medium margin-right-20px">
Department: ${r.department}, Doctor: ${r.doctorName}
                        </span>

                                <div class="rating">
                                    <ul>



                                        <span class="text-extra-small"
                                              >Date :${r.localDateTime}  </span>

                                    </ul>
                                </div>
                                <div>

                                    <span
                                          class="d-inline-block text-dark text-medium margin-right-20px">
                                          ${r.comment}
                                          </span>

                                </div>

                            </div>

                        </li>

                    </ul>
                </div>

                    <div class="col">
                        <div class="btn-group">
                            <a type="button" href="/admin/delete/review/${r.id}"

                               class="btn btn-md padding-lr-25px text-white background-main-color ">Delete</a>
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