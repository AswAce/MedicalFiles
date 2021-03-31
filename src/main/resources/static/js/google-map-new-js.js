function initMap() {
  // The location of Uluru
  const drujba = { lat: 42.665, lng: 23.40 };
  // The map, centered at Uluru
  const map = new google.maps.Map(document.getElementById("map"), {
    zoom: 15,
    center: drujba,
  });
  // The marker, positioned at Uluru
  const marker = new google.maps.Marker({
    position: drujba,
    map: map,
  });
}