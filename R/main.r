options(repos = c(CRAN = "https://cloud.r-project.org"))
install.packages("ggplot2")  # once
library(ggplot2)

a <- 2
b <- 3
c <- a + b
print(c)

x <- c(2,3)
y <- c(4,5)
z <- c(x + y, 1, 5, 2)
print(z)

print(paste(
  length(z),
  sum(z),
  mean(z),
  median(z),
  min(z),
  max(z)
))

print(z > 3)
print(z[z > 3])

mat <- matrix(c(1,2,3,4,5,6), nrow = 2, ncol = 3)
print(mat)

df <- data.frame(
  Name = c("Alice", "Bob", "Charlie"),
  Age = c(25, 30, 22),
  Score = c(90, 85, 95)
)

print(df)

if (a > 10) {
  print("a > 10")
} else if (a > 3) {
  print("a > 3 but <= 10")
} else {
  print("a <= 3")
}

for (n in z) {
  print(n^2) 
}

#Preferred implementation
print(z^2)

i <- 1
while (i <= 10) {
  print(i)
  i <- i + 2
}

#Preferred implementation
i <- seq(1, 10, by = 2)

add_numbers <- function(a, b = 3) {
  result <- a + b
  return(result)
}

print(add_numbers(3, 5))

df <- data.frame(
  Name = c("Alice", "Bob", "Charlie"),
  Age = c(25, 30, 22),
  Score = c(90, 85, 95)
)

print(df)

df %>% filter(Score > 90)

df %>% select(Name, Score)

df %>% mutate(Score2 = Score * 2)

df %>% group_by(Name) %>% summarise(avg_score = mean(Score))

df %>% select(Name, Age)
