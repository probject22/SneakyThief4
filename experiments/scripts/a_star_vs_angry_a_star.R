
# Set the working directory
setwd("~/Development/DKE/Project22/experiments/a_star/data")

# Import the data
data <- read.csv("astar_total.csv")

# Split the data based on the complexity of the map
a_path <- split(data$a_path_length, data$map_complexity)
l_path <- split(data$robStar_path_length, data$map_complexity)

# Draw boxplot
boxplot(a_path, ylim = c(100,200))
boxplot(l_path
        , ylim = c(100,200)
        )

# Calculate the means of the path length per map complexity
a_means <- aggregate(data$a_path_length, list(data$map_complexity), mean)
l_means <- aggregate(data$robStar_path_length, list(data$map_complexity), mean)

# Plot the means of path length of RobStar vs AStar
plot(a_means, col="red", ylim=c(100,250),  xlab="Map Complexity", ylab="Found path length", main="RobStar versus AStar 1")
lines(a_means, col="red")
points(l_means, col="blue", pch=6)
lines(l_means, col="blue")

# Calculate the means of the time per map complexity
a_time <- aggregate(data$a_running_time, list(data$map_complexity), mean)
l_time <- aggregate(data$robStar_running_time, list(data$map_complexity), mean)

# Plot the means of running time of RobStar vs AStar
plot(a_time, col="red", ylim=c(0,1e+9), xlab="Map Complexity", ylab="Running time (ns)", main="RobStar versus AStar 2")
lines(a_time, col="red")
points(l_time, col="blue",pch=6)
lines(l_time, col="blue")
