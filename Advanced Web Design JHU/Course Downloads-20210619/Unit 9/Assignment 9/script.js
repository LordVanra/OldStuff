function g() {
	var number=Math.random()*5; //Get a random number from 1-5
	number=Math.ceil(number); //Make it a whole number
	alert("Guess the number I'm thinking of between 1 and 5. You've got 3 tries."); //Display statement
	var guessleft=3; //amount of guesses left
	var currguess=0; //guessed number
	while (guessleft != 0){
		currguess=prompt(guessleft+ " tries remaining. Enter your guess?"); //get a guess
		currguess=parseInt(currguess); // make guess an integer
		if (currguess==number){
			guessleft=0; //end loop
			alert("You're a genius...you guessed it!!");
		}
		else{
			guessleft--; //increase guesses by 1
			alert("Sorry...that's not it...try again?");
		}
	}				
}