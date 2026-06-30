#include <stdio.h>
#include <iostream>

void basicControls(){
    std::cout << "I am in CS rn" << std::endl;

    auto a = "Arnav";

    std::cout << "My name is " << a << std::endl;

    int n = 1;

    if(n < 2){
        std::cout << "n is less than 2" << std::endl;
    }

    while (n < 5)
    {
        std::cout << "n is " << n << std::endl;
        n++;
    }
}

//Fizzbuzz

void fizzbuzz() {
    for(int i = 0; i < 50; i++){
        if(i % 3 == 0 && i % 5 == 0){
            std::cout << "fizzbuzz" << std::endl;
        }
        else if(i % 3 == 0){
            std::cout << "fizz" << std::endl;
        }
        else if(i % 5 == 0){
            std::cout << "buzz" << std::endl;
        }
        else{
            std::cout << i << std::endl;
        }
    }
}

//Classes

class Student{
    public:
        std::string name;
        int age;

        Student(std::string name, int age) : name(name), age(age) {}

        virtual void introduce(){ //allow overriding
          
          std::cout << "My name is " << name << " and I am " << age << " years old." << std::endl;
        }

        ~Student(){} //destructor
};

class HonorsStudent : public Student {
    public:
        float gpa;

        HonorsStudent(std::string name, int age, float gpa) : Student(name, age), gpa(gpa) {}

        void introduce(){
            Student::introduce();
            std::cout << "I have a GPA of " << gpa << std::endl;
        }
}; 

void classes() {
    Student John("John", 20);
    John.introduce();
    John.~Student();

    HonorsStudent Alice("Alice", 22, 3.9);
    Alice.introduce();
    Alice.~HonorsStudent();

    Student* Bob = new HonorsStudent("Bob", 20, 4.2);
    std::cout << sizeof(*Bob) << std::endl;
    Bob -> introduce(); 
    delete Bob;
}

struct Triangle{
    float base;
    float height;
    Triangle(float b, float h) : base(b), height(h) {}
    float area() {
        return 0.5 * base * height;
    }
};

int main() {
    Triangle t(4.0, 5.0);
    std::cout << "Area of the triangle: " << t.area() << std::endl;

    return 0;
}