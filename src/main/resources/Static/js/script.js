document.addEventListener("DOMContentLoaded", function () {
    const formContainer = document.getElementById("formContainer");
    for (let i = 1; i <= 14; i++) {
        const playerContainer = document.createElement("div");
        playerContainer.classList.add("player-container");
        const nameLabel = document.createElement("label");
        nameLabel.textContent = i + ".";
        nameLabel.classList.add("player-label");
        const nameInput = document.createElement("input");
        nameInput.size = 20;
        nameInput.classList.add("player-input");
        nameInput.type = "text";
        nameInput.name = `player${i}Name`;
        nameInput.placeholder = "Full Name";
        nameInput.required = true;
        const positionLabel = document.createElement("label");
        positionLabel.id = `player${i}Position`;
        let positionText;
        if (i <= 2) {
            positionText = "GK🧤";
        } 
        else if (i >= 3 && i <= 6) {
            positionText = "DEF🪨";
        } 
        else if (i >= 7 && i <= 10) {
            positionText = "MID🦵🏻";
        } 
         else if (i >= 11 && i <= 14) {
            positionText = "ATK⚽";
        }
        positionLabel.textContent = positionText;
        positionLabel.classList.add("player-position");
        const ratingInput = document.createElement("input");
        ratingInput.size = 3;
        ratingInput.step = 0.1;
        ratingInput.max = 10;
        ratingInput.min = 0;
        ratingInput.classList.add("player-rating");
        ratingInput.type = "number";
        ratingInput.name = `player${i}Rating`;
        ratingInput.placeholder = "R";
        ratingInput.required = true;
        playerContainer.appendChild(nameLabel);
        playerContainer.appendChild(positionLabel);
        playerContainer.appendChild(nameInput);
        playerContainer.appendChild(ratingInput);
        formContainer.appendChild(playerContainer);
    }
});
var formations = [];
var index = 0;
var showRatings = false;
var goalkeepers = [];
var otherPlayers =[]; 
async function balanceTeams() {
    formations = [];
    const players = [];
    for (let i = 1; i <= 14; i++) {
        const name = document.getElementsByName(`player${i}Name`)[0].value;
        const rating = parseFloat(document.getElementsByName(`player${i}Rating`)[0].value);
        const position = document.getElementById(`player${i}Position`).textContent;
        if (name === '' || rating === 0){
            showCustomAlert();
            return;
        }
        players.push({ name, rating, position });
    }
    try{
        const response = await fetch('http://localhost:9095/players', {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
              },
              body: JSON.stringify(players),
            });
            if (!response.ok)
              throw new Error(`HTTP error! Status: ${response.status}`);
            const data = await response.json();
            console.log('Response status:', response.status);
            console.log(data.length);
            formations = data;
    }     
    catch(error){
        console.error(error.message);
    }
    index =0;
    otherFormations();
}
function displayTeams(team1, team2) {
    var teams = document.getElementById('teams');
    teams.style.display = 'inline-block';
    var navDiv = document.getElementById("navDiv");
    navDiv.style.display = 'flex';
    var buttons = document.getElementsByClassName('navButtons');
    for (var i = 0; i < buttons.length; i++) {
        buttons[i].style.display = 'flex';
    }
    // var list = document.getElementById('list');
    // list.style.display = 'none';
    const team1Rating = Math.round(team1.totalRating);
    const team2Rating = Math.round(team2.totalRating);
    const team1AttackersRating = team1.atkrating;
    const team2AttackersRating = team2.atkrating;
    const team1CenterRating = team1.midrating;
    const team2CenterRating = team2.midrating;
    const team1DefendersRating = team1.defrating;
    const team2DefendersRating = team2.defrating;
    const team1GKRating = team1.gkrating;
    const team2GKRating = team2.gkrating;
    team1 = team1.players;
    team2 = team2.players;
    var accuracy = Math.round(Math.min(team1Rating,team2Rating)/Math.max(team1Rating,team2Rating)*100);
    var days = ['SUN', 'MON', 'TUE', 'WED', 'THU', 'FRI', 'SAT'];
    var currentDate = new Date();
    const outputDiv = document.getElementById("output");
    // <div class="inline-container">
    // <h2>Position Legend 🗝️</h2>
    // <div id="positionsKeys">
    //     <ul><li>🧤= GK</li><li>🪨= DEF</li><li>🦵🏻= MID</li><li>⚽=ATK</li></ul>
    // </div>
    // </div>
    outputDiv.innerHTML = `
        <h2>⏳ ${days[currentDate.getDay()]} - ${currentDate.getDate()}/${currentDate.getMonth()+1}/${currentDate.getFullYear()}, 17:00 - 20:00</h2>
        <div class="inline-container">
            <h2>RED 🔴</h2>
            <div class ="team" id="firstTeam">
                <ol id="team1">${team1.map(player => `<li>${player.name} - ${player.position}</li>`).join('')}</ol>
            </div>
        </div>
        <div class="inline-container">
            <h2>BLUE 🔵</h2>
            <div class ="team" id="secondTeam">
                <ol id="team2">${team2.map(player => `<li>${player.name} - ${player.position}</li>`).join('')}</ol>
            </div>
        </div>
        <h2>🔴${team1Rating} - ${team2Rating}🔵</h2>
        <br>
        <h2>🆚 Competition = ${accuracy}%</h2>
        <br>
        <h2>📌 Al Salam Grass Playground</h2>
        <br>
        <h2>🧤:${team1GKRating} - ${team2GKRating} 🪨:${team1DefendersRating} - ${team2DefendersRating} 🦵🏻:${team1CenterRating} - ${team2CenterRating} ⚽:${team1AttackersRating} - ${team2AttackersRating}  </h2>
    `;
    if (showRatings){
        document.getElementById("team1").innerHTML = `${team1.map(player => `<li>${player.name} - ${player.position} ${player.rating}</li>`).join('')}`;
        document.getElementById("team2").innerHTML = `${team2.map(player => `<li>${player.name} - ${player.position} ${player.rating}</li>`).join('')}`;
    }
}
function navigate(direction){
    if (direction === 'left' && index >0)
        index--;
    if (direction === 'right' && index<formations.length-1)
        index++;
    otherFormations();
}
function otherFormations(){
    displayTeams(formations[index].team1,formations[index].team2);
}
function showCustomAlert() {
    var modal = document.getElementById('customAlert');
    modal.style.display = 'block';
}
function hideCustomAlert() {
    var modal = document.getElementById('customAlert');
    modal.style.display = 'none';
}
function ratingsShow(){
    showRatings = !showRatings;
    otherFormations();
}
function swapColors(){
    var temp = formations[index].team1;
    formations[index].team1 = formations[index].team2;
    formations[index].team2 = temp;
    otherFormations();
}