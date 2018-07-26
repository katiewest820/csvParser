# CsvParser

## About

CsvParser allows you to pass in aws CUR and DBR billing files in order to easily see the total cost included in the provided file.
You can pass in .zip and .gz files as well as unzipped files 

## To Run

- Clone or fork repo.
- Run ./gradlew build
- Run java -jar [path to build file] [folder or file you are parsing]
- Result should return you something that looks like:


 ```
 Account ID | Un-blended Cost
 -------------------------------------
   555522223333 | 0.50
   444455556666 | 0.50
   111122223333 | 1.50
-------------------------------------

     Date    | Un-blended Cost
---------------------------------
  2018-06-01 | 0.50
  2018-06-02 | 0.00
  2018-06-03 | 0.00
  2018-06-04 | 0.50
  2018-06-05 | 0.00
  2018-06-06 | 0.00
  2018-06-07 | 0.00
  2018-06-08 | 0.00
  2018-06-09 | 0.00
  2018-06-10 | 0.00
  2018-06-11 | 1.00
  2018-06-12 | 0.00
  2018-06-13 | 0.00
  2018-06-14 | 0.00
  2018-06-15 | 0.00
  2018-06-16 | 0.00
  2018-06-17 | 0.50
  2018-06-18 | 0.00
  2018-06-19 | 0.00
  2018-06-20 | 0.00
  2018-06-21 | 0.00
  2018-06-22 | 0.00
  2018-06-23 | 0.00
  2018-06-24 | 0.00
  2018-06-25 | 0.00
  2018-06-26 | 0.00
  2018-06-27 | 0.00
  2018-06-28 | 0.00
  2018-06-29 | 0.00
  2018-06-30 | 0.00
---------------------------------

Total Cost: 2.50
```

