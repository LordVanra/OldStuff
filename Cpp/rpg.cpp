#include <stdio.h>
#include <iostream>
#include <vector>

// Forward declarations
class Agent;
class Item;

struct Modifier
{
    int healthModifier = 0;
    int powerModifier = 0;

    Modifier(int healthModifier = 0, int powerModifier = 0) : healthModifier(healthModifier), powerModifier(powerModifier) {}

    void applyModifier(Agent &agent);
};

class Agent
{
public:
    int health;
    int power;

    Agent(int health, int power) : health(health), power(power) {};
    virtual ~Agent() {};

    virtual void attack(Agent &target);
    virtual void useItem(Item item);
};

// Omplement applyModifier after Agent is fully defined
void Modifier::applyModifier(Agent &agent)
{
    agent.health += healthModifier;
    agent.power += powerModifier;
    std::cout << "Applied modifier: Health +" << healthModifier << ", Power +" << powerModifier << std::endl;
}

class Item{
public:
    std::string name;
    std::string description;
    Modifier modifiers;

    Item(std::string name, std::string description, int healthMod = 0, int powerMod = 0) 
        : name(name), description(description), modifiers(healthMod, powerMod) {};

    void use(Agent &agent)
    {
        modifiers.applyModifier(agent);
    }
};

// Implement Agent methods after Item is fully defined
void Agent::attack(Agent &target) {}

void Agent::useItem(Item item) {}

class Player : public Agent
{
public:
    std::string name;
    int level = 1;
    Player(std::string name, int health = 5, int power = 1) : Agent(health, power), name(name) {};
    
    void attack(Agent &target) override
    {
        std::cout << name << " attacks " << target.health << " HP target with power " << power << "!" << std::endl;
        target.health -= power;
        if (target.health < 0){
            target.health = 0;
        }
        std::cout << "Target now has " << target.health << " HP." << std::endl;
    }

    void useItem(Item item) override
    {
        std::cout << name << " uses " << item.name << "!" << std::endl;
        item.use(*this);
    }

    void levelUp()
    {
        if (level >= 30)
        {
            std::cout << name << " has reached max level!" << std::endl;
            return;
        }
        level++;
        health += 5;
        power += 1;
        std::cout << name << " leveled up! Health: " << health << ", Power: " << power << ", Level: " << level << std::endl;
    }

    void displayStats()
    {
        std::cout << name << ": Player with level: " << level << ", health: " << health << ", power: " << power << std::endl;
    }

    void reset()
    {
        std::cout << name << " has been respawned!" << std::endl;
        health = 5 * level;
        power = level;
        levelUp();
        displayStats();
    }
};

class Enemy : public Agent
{
public:
    std::string type;
    Enemy(std::string type, int health, int power) : Agent(health, power), type(type) {};
    
    void attack(Agent &target) override
    {
        std::cout << type << " attacks with power " << power << "!" << std::endl;
        target.health -= power;
        if (target.health < 0){
            target.health = 0;
        }
        std::cout << "Target now has " << target.health << " HP." << std::endl;
    }

    void useItem(Item item) override
    {
        std::cout << type << " uses " << item.name << "!" << std::endl;
        item.use(*this);
    }

    void displayStats()
    {
        std::cout << "Enemy of type " << type << " - Health: " << health << ", Power: " << power << std::endl;
    }
};

int main()
{
    Player player("Hero");
    Enemy enemies[] = {
        Enemy("Ant", 10, 2),
        Enemy("Slug", 15, 3),
        Enemy("Skeleton", 30, 5),
        Enemy("Goblin", 40, 7),
        Enemy("Troll", 80, 15),
        Enemy("Dragon", 150, 25)
    };

    int tries = 0;

    for(Enemy &enemy : enemies)
    {
        player.displayStats();
        enemy.displayStats();
        while (player.health > 0 && enemy.health > 0)
        {
            player.attack(enemy);
            if (enemy.health <= 0)
            {
                std::cout << "Enemy defeated!" << std::endl;
                tries = 0;
            }
            enemy.attack(player);
            if (player.health <= 0)
            {
                player.reset();
                tries++;
            }
            if(tries >= 3)
            {
                std::cout << "Game Over! You have been defeated too many times." << std::endl;
                return 0;
            }
        }
    }

    return 0;
}