import random;

def randomNumberGame():
    randomNumber = random.randint(1, 100)
    guess = 0
    attempts = 0
    print("Random Number Guessing Game")
    print("Guess a number between 1 and 100")
    while guess != randomNumber:
        guess = int(input("Enter your guess: "))
        attempts += 1
        if guess < randomNumber:
            print("Too low! Try again.")
        elif guess > randomNumber:
            print("Too high! Try again.")
        elif guess != randomNumber:
            print("Invalid input. Please enter a number between 1 and 100.")
        else:
            print(f"Congratulations! You've guessed the number {randomNumber} in {attempts} attempts.")


while True:
    randomNumberGame()
    playAgain = input("Do you want to play again? (yes/no): ").strip().lower()
    if playAgain != 'yes':
        print("Thanks for playing!")
        break