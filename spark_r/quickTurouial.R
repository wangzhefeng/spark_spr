


# ---------------------------------------------------------------
# 载入sparklyr, dplry
# ---------------------------------------------------------------
# if(!require(pacman)) install.packages("pacman")
# p_load(sparklyr, dplyr)


if(!require(sparklyr)) install.packages("sparklyr")
# devtools::install_github("rstudio/sparklyr")
if(!require(dplry)) install.packages("dplyr")
if(!require(nycflights13)) install.packages("nycflights13")
if(!require(Lahman)) install.packages("Lahman")

# ---------------------------------------------------------------
# 安装Spark
# ---------------------------------------------------------------
# spark_install(version = "2.1.0)

# ---------------------------------------------------------------
# 配置，连接Spark
# ---------------------------------------------------------------
conf = spark_config()
conf$`sparklyr.cores.local` = 4
conf$`sparklyr.shell.driver-memory` = "16G"
conf$`spark.memory.fraction` = 0.9

sc = spark_connect(master = "local",
                   version = "2.1.0",
                   config = conf)


# ---------------------------------------------------------------
# 利用dplyr处理集群上的数据
# ---------------------------------------------------------------
iris_tbl = sc %>% 
    dplyr::copy_to(iris, "iris")
flights_tbl = sc %>% 
    dplyr::copy_to(nycflights13::flights, "flights")
batting_tbl = sc %>% 
    dplyr::copy_to(Lahman::Batting, "batting")
cars = sc %>% 
    sparklyr::sdf_copy_to(cars, "cars")

dplyr::src_tbls(sc)

flights_tbl %>% filter(dep_delay == 2)

delay = flights_tbl %>%
    group_by(tailnum) %>%
    summarise(count = n(), dist = mean(distance), delay = mean(arr_delay)) %>%
    filter(count > 20, dist < 2000, !is.na(delay)) %>%
    collect

if(!require(ggplot2)) install.packages("ggplot2")
ggplot(data = delay, mapping = aes(x = dist, y = delay)) +
    geom_point(mapping = aes(size = count), alpha = 0.5) +
    geom_smooth() +
    scale_size_area(max_size = 2)

batting_tbl %>%
    select(playerID, yearID, teamID, G, AB:H) %>%
    arrange(playerID, yearID, teamID) %>%
    group_by(playerID) %>%
    filter(min_rank(desc(H)) <= 2 & H > 0)

# ---------------------------------------------------------------
# SQL
# ---------------------------------------------------------------
if(!require(DBI)) install.packages("DBI")
iris_preview = sc %>% dbGetQuery("SELECT * FROM iris LIMIT 10")
iris_preview

# ---------------------------------------------------------------
# Machine Learning
# ---------------------------------------------------------------
mtcars_tbl = sc %>% 
    dplyr::copy_to(mtcars, "mtcars")

dplyr::src_tbls(sc)

partitions = mtcars_tbl %>%
    filter(hp > 100) %>%
    mutate(cyl8 = cyl == 8) %>%
    sdf_partition(training = 0.7, testing = 0.3, seed = 1099)

fit = partitions$training %>%
    ml_linear_regression(response = "mpg", features = c("wt", "cyl"))
fit
summarise(fit)

# ---------------------------------------------------------------
# Reading and Writing Data
# ---------------------------------------------------------------
temp_csv = tempfile(fileext = ".csv")
temp_parquet = tempfile(fileext = ".parquet")
temp_json = tempfile(fileext = ".json")



