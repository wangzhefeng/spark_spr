
// ===============================================================
// SQL
// ===============================================================
/*
* Spark SQL => execute SQL queries
* Spark SQL => read data from an existing Hive installation
* Spark SQL => run SQL from within another programming language(scala, java, python)
*   - Dataset/DataFrame
* Spark SQL => interact with SQL interface
*   - Spark SQL CLI: spark-sql
*   - JDBC/ODBC
* */

// ===============================================================
// Datasets and DataFrame
// ===============================================================
/*
* --------------------------------
* Dataset
* --------------------------------
* distributed collection of data
* optimized RDD
* scala
* --------------------------------
* DataFrame
* --------------------------------
* Dataset organized into named columns => table/dataframe
* sources:
*   - structured data files
*   - Hive table
*   - external databases
*   - existing RDD
* scala, python, R
*   - scala: Dataset of RowS: Dataset[Row]
*   - Python: DataFrame
*   - R: data.frame
* */

// ===============================================================
// Spark SQL
// ===============================================================
import org.apache.spark.sql.SparkSession
import spark.implicits._


object sparkSQL {
	def main(args: Array[String]) = {

        // ==========================================================================================
            // DataFrame和Dataset编程的程序入口: SparkSession
        // ==========================================================================================
        val spark = SparkSession
            .builder()
            .master("local")
            .appName("Spark SQL")
            .config("spark.some.config.option", "some-value")
            .getOrCreate()

        // ==========================================================================================
		// stop the SparkSession
        // ==========================================================================================
		spark.stop()
	}
}

