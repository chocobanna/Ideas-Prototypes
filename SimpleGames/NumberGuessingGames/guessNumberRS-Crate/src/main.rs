use adv_random::random::random_numbers;
use adv_random::settings::Settings;
use adv_random::rules::NumberRange;

fn main() {
    let settings = Settings::new(
        &[ Box::new(NumberRange::all(1, 100)) ],
        1
    );
    let result = random_numbers(&settings);

    let secret = result
        .numbers()
        .unwrap_or_else(|logs| panic!("Generation error: {:?}", logs))[0];

    let mut attempts = 0;
    let mut guess = 0;

    println!("numberGuesser");
    println!("Guess a random number between 1 and 100!");

    while guess != secret {
        println!("Please enter your guess:");
        let mut input = String::new();
        std::io::stdin()
            .read_line(&mut input)
            .expect("Failed to read line");
        guess = input.trim().parse().expect("Enter a valid number");
        attempts += 1;

        if guess < secret {
            println!("Too low! Try again.");
        } else if guess > secret {
            println!("Too high! Try again.");
        } else {
            println!(
                "Congratulations! You've guessed the number {} in {} attempts.",
                secret, attempts
            );
        }
    }
}
