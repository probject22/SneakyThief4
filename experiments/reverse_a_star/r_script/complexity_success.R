setwd("~/Development/DKE/Project22/experiments/reverse_a_star/r_script")
reverse_a_star_data <- read.csv("../data/dummy.csv")

prey_algorithm <- subset(reverse_a_star_data, algorithm == "prey_a_star")
reverse_algorithm <- subset(reverse_a_star_data, algorithm == "reverse_a_star")

prey_blank <- subset(prey_algorithm, map_type == "blank")
reverse_blank <- subset(reverse_algorithm, map_type == "blank")

prey_rooms <- subset(prey_algorithm, map_type == "rooms")
reverse_rooms <- subset(reverse_algorithm, map_type == "rooms")

prey_Us <- subset(prey_algorithm, map_type == "Us")
reverse_Us <- subset(reverse_algorithm, map_type == "Us")

prey_maze <- subset(prey_algorithm, map_type == "maze")
reverse_maze <- subset(reverse_algorithm, map_type == "maze")

table(prey_blank$success)
table(reverse_blank$success)

t.test(prey_blank$success,reverse_blank$success)
t.test(prey_rooms$success,reverse_rooms$success)
t.test(prey_Us$success,reverse_Us$success)
t.test(prey_maze$success,reverse_maze$success)