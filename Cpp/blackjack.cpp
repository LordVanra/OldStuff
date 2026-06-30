#include <iostream>
#include <stdio.h>
#include <vector>
#include <algorithm>

class Deck
{
public:
    static const int cards[52];
    static std::vector<int> deck;

    static void init()
    {
        deck.assign(cards, cards + 52);
    }

    static int drawCard()
    {
        if (deck.empty())
        {
            std::cerr << "The deck is empty!" << std::endl;
            return -1; // Indicate that the deck is empty
        }
        int index = rand() % deck.size();
        int card = deck[index];   
        deck.erase(deck.begin() + index); 
        return card;
    }

};

const int Deck::cards[52] = {2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 6, 6, 6, 6, 7, 7, 7, 7, 8, 8, 8, 8, 9, 9, 9, 9, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 11, 11, 11, 11};
std::vector<int> Deck::deck;

class Player
{
public:
    std::vector<int> hand;
    bool hasDoubledDown = false;
    bool hasSplit = false;

    int drawCard()
    {
        // int card = Deck::drawCard();
        hand.push_back(10);

        return 10;
    }

    int getHandValue()
    {
        int sum = 0;
        for (int card : hand)
        {
            sum += card;
        }
        return sum;
    }

    void clearHand()
    {
        hand.clear();
    }

    bool canSplit() { return hand.size() >= 2 && hand[0] == hand[1]; }

    bool canDoubleDown() { return hand.size() == 2 && getHandValue() >= 9 && getHandValue() <= 11 && !hasDoubledDown; }

    virtual std::string showHand() {}
};

class Dealer : public Player
{ 
public:
    int showing;
    void init()
    {
        hand.push_back(Deck::drawCard());

        showing = hand[0];
        std::cout << "Dealer is showing a " << showing << std::endl;
    }

    std::string showHand() override
    {
        std::string handData = "";

        handData += "Dealer's hand:";
        for (int card : hand)
        {
            handData += " " + std::to_string(card);;
        }
        handData += ". Total: " + std::to_string(getHandValue());
        return handData;
    }

    int setupHand(int playerValue)
    {
        while (getHandValue() < 17)
        {
            drawCard();

            if (getHandValue() > 21)
            {
                if (std::find(hand.begin(), hand.end(), 11) != hand.end())
                {
                    auto it = std::find(hand.begin(), hand.end(), 11);
                    *it = 1; // Change an Ace from 11 to 1
                }
            }
        }

        return getHandValue();
    }
};

class Human : public Player
{
public:
    std::string showHand() override
    {
        std::string handData;

        handData += "Player's hand:";
        for (int card : hand)
        {
            handData += " ";
            handData += std::to_string(card);
        }
        handData += ". Total: " + std::to_string(getHandValue());

        return handData;
    }
};

void resetGame(Dealer &dealer, Human &player, int &money, int &bet)
{
    std::cout << "You have $" << money << "." << std::endl;
    bet = 0;

    Deck::init();
    dealer.clearHand();
    player.clearHand();

    while (bet <= 0 || bet > money)
    {
        std::cout << "What would you like to bet? ";
        std::cin >> bet;
        if (bet < 0 || bet > money)
        {
            std::cout << "Invalid bet amount. You have $" << money << "." << std::endl;
        }
    }

    dealer.init();

    player.drawCard();
    dealer.drawCard();
    player.drawCard();
}

void partialResetGame(Human &player, int &money)
{
    std::cout << "You have $" << money << "." << std::endl;

    Deck::init();
    player.clearHand();
}

char getPlayerChoice(Human &player)
{
    std::cout << "Would you like to hit ('h'), ";
    if (player.canDoubleDown())
    {
        std::cout << "double down ('d'), ";
    }
    if (player.canSplit())
    {
        std::cout << "split ('p'), ";
    }
    std::cout << "or stand ('s')." << std::endl;
    char choice;
    std::cin >> choice;
    return choice;
}

void terminateGame(Dealer &dealer, Human &player, int &money, int &bet, std::string outputLog);

int playGame(Dealer &dealer, Human &player, int &money, int &bet)
{
    std::string outputLog = "";
    std::cout << player.showHand() << std::endl;

    char choice = getPlayerChoice(player);

    if (choice == 'h')
    {
        player.drawCard();
    }
    else if (choice == 's')
    {
        player.hasDoubledDown = false;
        terminateGame(dealer, player, money, bet, outputLog);
    }
    else if (choice == 'd' && player.canDoubleDown())
    {
        bet *= 2;
        player.hasDoubledDown = true;
    }
    else if (choice == 'p' && player.canSplit())
    {
        player.hand.pop_back();
    }
    else if (choice == 'q')
    {
        std::cout << "Thanks for playing! You leave with $" << money << "." << std::endl;
        return -1;
    }
    else
    {
        outputLog += "Invalid input. Please enter 'h' to hit, ";
        if (player.canDoubleDown())
        {
            outputLog += "'d' to double down, ";
        }
        if (player.canSplit())
        {
            outputLog += "'p' to split, ";
        }
        outputLog += "or 's' to stay.";

        std::cout << outputLog << std::endl;
    }

    if (player.getHandValue() > 21)
    {
        if (std::find(player.hand.begin(), player.hand.end(), 11) != player.hand.end())
        {
            auto it = std::find(player.hand.begin(), player.hand.end(), 11);
            *it = 1; // Change an Ace from 11 to 1
        }

        if (player.hasSplit)
        {

            player.showHand();
            std::cout << "You busted! Dealer wins this hand." << std::endl;
            money -= bet;
            player.hasDoubledDown = false;

            while (player.hand.size() > 1)
            {
                player.hand.pop_back();
            }
        }
        else
        {
            player.showHand();
            dealer.showHand();
            std::cout << "You busted! Dealer wins." << std::endl;
            money -= bet;

            player.hasDoubledDown = false;
            resetGame(dealer, player, money, bet);
        }
    }

    return 0;
}


void terminateGame(Dealer &dealer, Human &player, int &money, int &bet, std::string outputLog)
{

    int playerValue = player.getHandValue();
    int dealerValue = dealer.setupHand(playerValue);

    outputLog += "After drawing, the dealer's hand is:";
    // Cast dealer.showHand() to int
    int dealerHandValue = std::stoi(dealer.showHand());

    if (dealerValue > 21 || player.getHandValue() > dealerValue)
    {
        outputLog += "You win!";
        money += bet;
    }
    else if (player.getHandValue() < dealerValue)
    {
        outputLog += "Dealer wins!";
        money -= bet;
    }
    else
    {
        outputLog += "It's a tie!";
    }

    if (!player.hasSplit)
    {
        std::cout << outputLog << std::endl;
        resetGame(dealer, player, money, bet);
    }
    else
    {
        int card = player.hand[0];
        partialResetGame(player, money);
        player.hand.push_back(card);
        playGame(dealer, player, money, bet);
        player.hasSplit = false;
    }
}

int main()
{
    srand(time(0));
    Deck::init();
    Dealer dealer;
    Human player;

    int bet = 0;
    int money = 5000;

    resetGame(dealer, player, money, bet);

    while (true)
    {
        playGame(dealer, player, money, bet);
    }
    return 0;
}
